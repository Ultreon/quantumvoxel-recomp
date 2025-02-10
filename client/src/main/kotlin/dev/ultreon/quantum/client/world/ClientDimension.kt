package dev.ultreon.quantum.client.world

import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.utils.async.AsyncExecutor
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.gamePlatform
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.util.BlockHit
import dev.ultreon.quantum.util.RayD
import dev.ultreon.quantum.world.BlockFlags
import dev.ultreon.quantum.world.Dimension
import dev.ultreon.quantum.world.SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import ktx.async.AsyncExecutorDispatcher
import ktx.async.KtxAsync
import ktx.async.MainDispatcher
import ktx.collections.GdxArray
import ktx.collections.GdxSet
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

val renderDistance: Int
  get() = if (gamePlatform.isMobile) 4 else 8

open class ClientDimension(private val material: Material) : Dimension() {
  private lateinit var player: LocalPlayer
  val chunks: MutableMap<Long, ClientChunk> = ConcurrentHashMap()
  val chunksToLoad = GdxArray<Pair<GridPoint3, Long>>()
  val generator = Generator()
  val asyncChunkGen = AsyncExecutorDispatcher(AsyncExecutor(8, "ChunkGeneratorPool"), 8)

  private var toRemove = listOf<ClientChunk>()
  private var toRebuild = listOf<ClientChunk>()

  override fun get(x: Int, y: Int, z: Int): Block {
    return chunks[location(x.floorDiv(SIZE), y.floorDiv(SIZE), z.floorDiv(SIZE))]
      ?.get(x.mod(SIZE), y.mod(SIZE), z.mod(SIZE)) ?: Blocks.air
  }

  override fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags) {
    val get = chunks[location(x.floorDiv(SIZE), y.floorDiv(SIZE), z.floorDiv(SIZE))]
    get?.let {
      logger.info("Setting blocks at $x, $y, $z to $block")
      it.set(x % SIZE, y % SIZE, z % SIZE, block, flags)
      it.rebuild()
      forChunksAround(it) { rebuild(true) }
    }
  }

  fun location(x: Int, y: Int, z: Int): Long {
    return (x.toLong() and 0xFFFFFF) or
      ((y.toLong() and 0xFFFFFF) shl 24) or
      ((z.toLong() and 0xFFFFFF) shl 48)
  }

  override fun chunkAt(x: Int, y: Int, z: Int): ClientChunk? {
    return chunks[location(x, y, z)]
  }

  override fun chunkAtBlock(x: Int, y: Int, z: Int): ClientChunk? {
    return chunkAt(x.floorDiv(SIZE), y.floorDiv(SIZE), z.floorDiv(SIZE))
  }

  fun put(chunk: ClientChunk): Boolean {
    val location = location(chunk.chunkPos.x, chunk.chunkPos.y, chunk.chunkPos.z)
    val oldChunk = chunks[location]
    if (oldChunk != null) {
      if (remove(oldChunk)) {
        if (chunk.loading) {
          return true
        }
        chunk.disposeChunk()
        return true
      }
      logger.warn("Overridden chunk at ${chunk.chunkPos}")
    }
    chunks[location] = chunk
    return false
  }

  suspend fun putAsync(chunk: ClientChunk): Boolean {
    return MainDispatcher.invoke {
      put(chunk)
    }
  }

  fun remove(chunk: ClientChunk): Boolean {
    if (!chunk.disposeChunk()) {
      return true
    }
    if (chunks.remove(location(chunk.chunkPos.x, chunk.chunkPos.y, chunk.chunkPos.z)) == null) {
      logger.warn("Tried to remove nonexistent chunk at ${chunk.chunkPos}")
    }

    forChunksAround(chunk) { rebuild() }
    return false
  }

  fun loadChunk(cx: Int, cy: Int, cz: Int, build: Boolean = true) {
    val chunk = ClientChunk(cx, cy, cz, material, this)
    if (put(chunk.apply {
        generate(this)
      })) return
    chunk.apply {
      if (build) {
        rebuild()
      }
    }
  }

  suspend fun loadChunkAsync(cx: Int, cy: Int, cz: Int, build: Boolean = true) {
    val chunk = ClientChunk(cx, cy, cz, material, this)
    if (putAsync(chunk.also { return@also asyncChunkGen.invoke { generateAsync(it) } })) return
    chunk.buildModel()
    forChunksAround(chunk) { rebuild() }
  }

  inline fun forChunksAround(chunk: ClientChunk, crossinline action: ClientChunk.() -> Unit) {
    val position = chunk.chunkPos
    var didAction = false
    for (x in (position.x - 1)..(position.x + 1)) {
      for (y in (position.y - 1)..(position.y + 1)) {
        for (z in (position.z - 1)..(position.z + 1)) {
          if (x == position.x && y == position.y && z == position.z) {
            continue
          }

          chunks[location(x, y, z)]?.let(action)?.let {
            didAction = true
          }
        }
      }
    }

    if (!didAction) {
      logger.warn("Didn't find any loaded chunks around ${chunk.chunkPos}")
    }
  }

  val ClientChunk.hasChunksAround: Boolean
    get() {
      for (x in -1..1) for (y in -1..1) for (z in -1..1) {
        if (x == 0 && y == 0 && z == 0) {
          continue
        } else {
          chunks[location(chunkPos.x + x, chunkPos.y + y, chunkPos.z + z)] ?: return false
        }
      }
      return true
    }

  suspend fun pollChunkLoad() {
    if (chunksToLoad.isEmpty) return
    val toLoad = chunksToLoad.removeIndex(0)
    when (val chunkPos = chunks[toLoad.second]?.chunkPos) {
      toLoad.first -> return
      null -> loadChunkAsync(toLoad.first.x, toLoad.first.y, toLoad.first.z)
      else -> logger.warn("Attempted override for ${toLoad.first} at already existing location $chunkPos")
    }
  }

  suspend fun pollAllChunks() {
    logger.debug("About to load ${chunksToLoad.size} chunks!")

    measureTimeMillis {
      var lastLogTime = System.currentTimeMillis()
      while (!chunksToLoad.isEmpty) {
        val toLoad = chunksToLoad.removeIndex(0)
        loadChunkAsync(toLoad.first.x, toLoad.first.y, toLoad.first.z, build = false)
        if (System.currentTimeMillis() - lastLogTime > 1000) {
          logger.debug("${chunksToLoad.size} chunks remaining!")
          lastLogTime = System.currentTimeMillis()
        }

        yield()
      }
    }.also {
      logger.debug("Loaded ${chunks.size} chunks in $it ms!")
    }

    rebuildAll()

    logger.debug("Rebuilt all chunks!")
  }

  suspend fun rebuildAll() {
    for (chunk in chunks.values) {
      chunk.rebuild(blocking = true)
      yield()
    }
  }

  suspend fun refreshChunks(position: Vector3D) {
    val requiredChunks: MutableList<Pair<GridPoint3, Long>> = arrayListOf()
    val cx = position.x.toInt().floorDiv(SIZE)
    val cy = position.y.toInt().floorDiv(SIZE)
    val cz = position.z.toInt().floorDiv(SIZE)
    for (x in -renderDistance..renderDistance) {
      for (y in -renderDistance..renderDistance) {
        for (z in -renderDistance..renderDistance) {
          val dcy = y + cy
          val dcx = x + cx
          val dcz = z + cz

          val chunk = chunks[location(dcx, dcy, dcz)]
          if (chunk == null) {
            requiredChunks.add(GridPoint3(dcx, dcy, dcz) to location(dcx, dcy, dcz))
          }

          yield()
        }
      }
    }

    requiredChunks.sortBy {
      it.first.dst(cx, cy, cz)
    }

    val toRemove = GdxArray<ClientChunk>()
    val toRebuild = GdxSet<ClientChunk>()
    val await = chunks.map { it.value }
    for (chunk in await) {
      if (chunk.chunkPos.dst(cx, cy, cz) > renderDistance) {
        toRemove.add(chunk)
        toRebuild.remove(chunk)
        forChunksAround(chunk) { toRebuild.add(this) }
      }

      yield()
    }

    this@ClientDimension.toRemove = toRemove.toList()
    this@ClientDimension.toRebuild = toRebuild.toList()

    for (chunk in requiredChunks) {
      loadChunkAsync(chunk.first.x, chunk.first.y, chunk.first.z, build = true)
      yield()
    }

    for (chunk in toRemove.toList()) {
      remove(chunk)
      yield()
    }
  }

  fun pollChunks() {
    for (removing in toRemove) {
      remove(removing)
    }

    for (rebuilding in toRebuild) {
      rebuilding.rebuild()
    }
  }

  private fun generate(chunk: ClientChunk) {
    generator.generate(chunk)
  }

  private fun generateAsync(chunk: ClientChunk) {
    generator.generateAsync(chunk)
  }

  private fun generateBlock(wx: Int, wy: Int, wz: Int): Block {
    return when {
      wy > 64 -> Blocks.air
      wy == 64 -> Blocks.grass
      wy > 60 -> Blocks.soil
      else -> Blocks.stone
    }
  }

  fun render(modelBatch: ModelBatch, camera: PerspectiveCamera) {
    for (chunk: ClientChunk in chunks.values) {
      chunk.reposition(player.positionComponent.position)
      modelBatch.render(chunk)
    }
  }

  override fun dispose() {
    for (chunk in chunks.values) {
      val disposeChunk = chunk.disposeChunk()
      if (!disposeChunk) {
        logger.warn("Failed to dispose chunk at ${chunk.chunkPos}")
      }
    }
    chunks.clear()
  }

  fun updateLocations(position: Vector3D) {
    for (chunk in chunks.values) {
      chunk.reposition(position)
    }
  }

  fun rayCast(position: Vector3D, lookVec: Vector3D): BlockHit {
    return rayTrace(RayD(position, lookVec))
  }

  fun spawnPlayer(vec3d: Vector3D): LocalPlayer {
    this.player = LocalPlayer("local", this, vec3d)
    return player
  }
}

private operator fun GridPoint3.component1(): Int = x
private operator fun GridPoint3.component2(): Int = y
private operator fun GridPoint3.component3(): Int = z
