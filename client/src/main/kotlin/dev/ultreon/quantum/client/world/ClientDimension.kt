package dev.ultreon.quantum.client.world

import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.math.GridPoint3
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.client.gamePlatform
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.util.BlockHit
import dev.ultreon.quantum.util.RayD
import dev.ultreon.quantum.world.BlockFlags
import dev.ultreon.quantum.world.Dimension
import dev.ultreon.quantum.world.SIZE
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ktx.assets.disposeSafely
import ktx.async.MainDispatcher
import ktx.async.newAsyncContext
import ktx.collections.GdxArray
import ktx.collections.GdxSet
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

val renderDistance: Int
  get() = if (gamePlatform.isMobile) 4 else 8

open class ClientDimension(private val material: Material) : Dimension() {
  private lateinit var player: PlayerEntity
  val chunks: MutableMap<Long, ClientChunk> = ConcurrentHashMap()
  val chunksToLoad = GdxArray<Pair<GridPoint3, Long>>()
  val generator = Generator()

  private var toRemove = listOf<ClientChunk>()
  private var toRebuild = listOf<ClientChunk>()

  val context = newAsyncContext((Runtime.getRuntime().availableProcessors()) * 4, "ChunkBuilder")

  override fun get(x: Int, y: Int, z: Int): Block {
    synchronized(chunks) {
      return chunks[location(x.floorDiv(SIZE), y.floorDiv(SIZE), z.floorDiv(SIZE))]
        ?.get(x.mod(SIZE), y.mod(SIZE), z.mod(SIZE)) ?: Blocks.air
    }
  }

  override fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags) {
    val get = chunks[location(x.floorDiv(SIZE), y.floorDiv(SIZE), z.floorDiv(SIZE))]
    get?.let {
      logger.info("Setting block at $x, $y, $z to $block")
      it.set(x % SIZE, y % SIZE, z % SIZE, block, flags)
      it.rebuild()
      forChunksAround(it) { rebuild() }
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

  fun put(chunk: ClientChunk) {
    val location = location(chunk.chunkPos.x, chunk.chunkPos.y, chunk.chunkPos.z)
    if (location in chunks) {
      remove(chunks[location] ?: return)
      logger.warn("Overridden chunk at ${chunk.chunkPos}")
    }
    chunks.put(location, chunk)
  }

  fun remove(chunk: ClientChunk) {
    if (chunk.disposeChunk()) {
      return
    }
    if (chunks.remove(location(chunk.chunkPos.x, chunk.chunkPos.y, chunk.chunkPos.z)) == null) {
      logger.warn("Tried to remove nonexistent chunk at ${chunk.chunkPos}")
    }

    forChunksAround(chunk) { rebuild() }
  }

  fun loadChunk(cx: Int, cy: Int, cz: Int, build: Boolean = true) {
    val chunk = ClientChunk(cx, cy, cz, material, this)
    put(chunk.apply {
      generate(this)
    })
    chunk.apply {
      if (build) {
        rebuild()
        forChunksAround(this) { rebuild() }
      }
    }
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

  fun pollChunkLoad() {
    if (chunksToLoad.isEmpty) return
    val toLoad = chunksToLoad.removeIndex(0)
    val chunkPos = chunks[toLoad.second]?.chunkPos
    when (chunkPos) {
      toLoad.first -> return
      null -> loadChunk(toLoad.first.x, toLoad.first.y, toLoad.first.z)
      else -> logger.warn("Attempted override for ${toLoad.first} at already existing location $chunkPos")
    }
  }

  fun pollAllChunks() {
    logger.debug("About to load ${chunksToLoad.size} chunks!")

    measureTimeMillis {
      var lastLogTime = System.currentTimeMillis()
      while (!chunksToLoad.isEmpty) {
        val toLoad = chunksToLoad.removeIndex(0)
        loadChunk(toLoad.first.x, toLoad.first.y, toLoad.first.z, build = false)
        if (System.currentTimeMillis() - lastLogTime > 1000) {
          logger.debug("${chunksToLoad.size} chunks remaining!")
          lastLogTime = System.currentTimeMillis()
        }
      }
    }.also {
      logger.debug("Loaded ${chunks.size} chunks in $it ms!")
    }

    rebuildAll()

    logger.debug("Rebuilt all chunks!")
  }

  fun rebuildAll() {
    for (chunk in chunks.values) {
      chunk.rebuild()
    }
  }

  suspend fun refreshChunks(position: Vector3D) = coroutineScope {
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
        }
      }
    }

    requiredChunks.sortBy {
      it.first.dst(cx, cy, cz)
    }

    val toRemove = GdxArray<ClientChunk>()
    val toRebuild = GdxSet<ClientChunk>()
    val await = coroutineScope { async(MainDispatcher) { chunks.map { it.value } } }.await()
    for (chunk in await) {
      if (chunk.chunkPos.dst(cx, cy, cz) > renderDistance) {
        toRemove.add(chunk)
        toRebuild.remove(chunk)
        forChunksAround(chunk) { toRebuild.add(this) }
      }
    }

    async(MainDispatcher) {
      this@ClientDimension.toRemove = toRemove.toList()
    }.await()

    async(MainDispatcher) {
      this@ClientDimension.toRebuild = toRebuild.toList()
    }.await()

    async(MainDispatcher) {
      chunksToLoad.clear()
      for (chunk in requiredChunks) {
        this@ClientDimension.chunksToLoad.add(chunk)
      }
    }.await()
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
//    val wx = chunk.chunkPos.x * SIZE
//    val wy = chunk.chunkPos.y * SIZE
//    val wz = chunk.chunkPos.z * SIZE
//
//    for (x in 0 until SIZE) {
//      for (y in 0 until SIZE) {
//        for (z in 0 until SIZE) {
//          val block = generateBlock(wx + x, wy + y, wz + z)
//          chunk.set(x, y, z, block, BlockFlags.NONE)
//        }
//      }
//    }

    generator.generate(chunk)
  }

  private fun generateBlock(wx: Int, wy: Int, wz: Int): Block {
    return when {
      wy > 64 -> Blocks.air
      wy == 64 -> Blocks.grass
      wy > 60 -> Blocks.soil
      else -> Blocks.stone
    }
  }

  fun render(modelBatch: ModelBatch) {
    for (chunk: ClientChunk in chunks.values) {
      modelBatch.render(chunk)
    }
  }

  override fun dispose() {
    context.disposeSafely()

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

  fun spawnPlayer(vec3d: Vector3D): PlayerEntity {
    this.player = PlayerEntity(this, vec3d)
    return player
  }
}

private operator fun GridPoint3.component1(): Int = x
private operator fun GridPoint3.component2(): Int = y
private operator fun GridPoint3.component3(): Int = z
