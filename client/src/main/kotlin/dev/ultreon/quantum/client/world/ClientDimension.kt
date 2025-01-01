package dev.ultreon.quantum.client.world

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.utils.LongMap
import com.badlogic.gdx.utils.Pool
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.world.BlockFlags
import dev.ultreon.quantum.world.Dimension
import ktx.assets.disposeSafely
import ktx.collections.GdxArray
import ktx.collections.sortBy
import kotlin.system.measureTimeMillis

const val renderDistance = 2

class ClientDimension(private val material: Material) : Dimension(), RenderableProvider {
  val chunks: LongMap<ClientChunk> = LongMap()
  val chunksToLoad = GdxArray<Pair<GridPoint3, Long>>()

  override fun get(x: Int, y: Int, z: Int): Block {
    return chunks.get(location(x, y, z))?.get(x.mod(SIZE), y.mod(SIZE), z.mod(SIZE)) ?: Blocks.air
  }

  override fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags) {
    chunks.get(location(x, y, z))?.set(x % SIZE, y % SIZE, z % SIZE, block, flags)
  }

  fun location(x: Int, y: Int, z: Int): Long {
    return (x.toLong() and 0xFFFFFF) or
            ((y.toLong() and 0xFFFFFF) shl 24) or
            ((z.toLong() and 0xFFFFFF) shl 48)
  }

  fun chunkAt(x: Int, y: Int, z: Int): ClientChunk? {
    return chunks.get(location(x, y, z))
  }

  fun chunkAtBlock(x: Int, y: Int, z: Int): ClientChunk? {
    return chunkAt(x.floorDiv(SIZE), y.floorDiv(SIZE), z.floorDiv(SIZE))
  }

  fun put(chunk: ClientChunk) {
    val location = location(chunk.chunkPos.x, chunk.chunkPos.y, chunk.chunkPos.z)
    if (chunks.containsKey(location)) {
      remove(chunks.get(location))
      logger.warn("Overridden chunk at ${chunk.chunkPos}")
    }
    chunks.put(location, chunk)
  }

  fun remove(chunk: ClientChunk) {
    chunks.remove(location(chunk.chunkPos.x, chunk.chunkPos.y, chunk.chunkPos.z))
    chunk.disposeSafely()

    forChunksAround(chunk) { rebuild() }

    logger.debug("Unloaded chunk: ${chunk.chunkPos}")
  }

  fun loadChunk(cx: Int, cy: Int, cz: Int, build: Boolean = true) = put(ClientChunk(cx, cy, cz, material, this).apply {
    generate(this)
    if (build) {
      rebuild()
      forChunksAround(this) { rebuild() }
    }
  })

  inline fun forChunksAround(chunk: ClientChunk, crossinline action: ClientChunk.() -> Unit) {
    val position = chunk.chunkPos
    var didAction = false
    for (x in (position.x - 1)..(position.x + 1)) {
      for (y in (position.y - 1)..(position.y + 1)) {
        for (z in (position.z - 1)..(position.z + 1)) {
          if (x == position.x && y == position.y && z == position.z) {
            continue
          }

          chunks.get(location(x, y, z))?.let(action)?.let {
            didAction = true
          }
        }
      }
    }

    if (!didAction) {
      logger.warn("Didn't find any loaded chunks around ${chunk.chunkPos}")
    }
  }

  fun pollChunkLoad() {
    if (chunksToLoad.isEmpty) return
    val toLoad = chunksToLoad.removeIndex(0)
    loadChunk(toLoad.first.x, toLoad.first.y, toLoad.first.z)
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
    for (chunk in chunks.values()) {
      chunk.rebuild()
    }
  }

  fun refreshChunks(position: Vector3D) {
    val requiredChunks = GdxArray<Pair<GridPoint3, Long>>()
    for (x in -renderDistance..renderDistance) {
      for (y in -renderDistance..renderDistance) {
        for (z in -renderDistance..renderDistance) {
          val cy = y + position.y.toInt() * SIZE
          val cx = x + position.x.toInt() * SIZE
          val cz = z + position.z.toInt() * SIZE

          val chunk = chunks[location(cx, cy, cz)]
          if (chunk == null) {
            requiredChunks.add(GridPoint3(cx, cy, cz) to location(cx, cy, cz))
          }
        }
      }
    }

    for (chunk in chunks.values()) {
      if (requiredChunks.none { it.first.x == chunk.chunkPos.x && it.first.y == chunk.chunkPos.y && it.first.z == chunk.chunkPos.z }) {
        remove(chunk)
        forChunksAround(chunk) { rebuild() }
      }
    }

    requiredChunks.sortBy {
      it.first.dst2(position.x.toInt(), position.y.toInt(), position.z.toInt())
    }
    for (chunk in chunksToLoad) {
      if (chunk.first.dst(position.x.toInt(), position.y.toInt(), position.z.toInt()) > renderDistance * SIZE) {
        chunksToLoad.removeValue(chunk, true)
      }
    }
    for (chunk in requiredChunks) {
      this.chunksToLoad.add(chunk)
    }
  }

  private fun generate(chunk: ClientChunk) {
    val wx = chunk.chunkPos.x * SIZE
    val wy = chunk.chunkPos.y * SIZE
    val wz = chunk.chunkPos.z * SIZE

    for (x in 0 until SIZE) {
      for (y in 0 until SIZE) {
        for (z in 0 until SIZE) {
          val block = generateBlock(wx + x, wy + y, wz + z)
          chunk.set(x, y, z, block, BlockFlags.NONE)
        }
      }
    }
  }

  private fun generateBlock(wx: Int, wy: Int, wz: Int): Block {
    if (wy > 64) {
      return Blocks.air
    } else if (wy == 64) {
      return Blocks.grass
    } else if (wy > 60) {
      return Blocks.soil
    } else {
      return Blocks.stone
    }
  }

  override fun getRenderables(array: GdxArray<Renderable>, pool: Pool<Renderable>) {
    for (chunk in chunks.values()) {
      chunk.getRenderables(array, pool)
    }
  }

  override fun dispose() {
    for (chunk in chunks.values()) {
      chunk.disposeSafely()
    }
    chunks.clear()
  }

  fun updateLocations(camera: Camera, position: Vector3D) {
    for (chunk in chunks.values()) {
      chunk.reposition(camera, position)
    }
  }
}
