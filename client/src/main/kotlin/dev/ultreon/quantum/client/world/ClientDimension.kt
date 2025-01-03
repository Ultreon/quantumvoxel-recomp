package dev.ultreon.quantum.client.world

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.utils.LongMap
import com.badlogic.gdx.utils.Pool
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.client.GameScreen
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.entity.PositionComponent
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.vec3d
import dev.ultreon.quantum.world.BlockFlags
import dev.ultreon.quantum.world.Dimension
import dev.ultreon.quantum.world.SIZE
import ktx.assets.disposeSafely
import ktx.collections.GdxArray
import ktx.collections.GdxSet
import ktx.collections.sortBy
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

const val renderDistance = 8

class ClientDimension(private val material: Material) : Dimension() {
  val chunks: LongMap<ClientChunk> = LongMap()
  val chunksToLoad = GdxArray<Pair<GridPoint3, Long>>()
  val generator = Generator()

  override fun get(x: Int, y: Int, z: Int): Block {
    return chunks.get(location(x.floorDiv(SIZE), y.floorDiv(SIZE), z.floorDiv(SIZE)))
      ?.get(x.mod(SIZE), y.mod(SIZE), z.mod(SIZE)) ?: Blocks.air
  }

  override fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags) {
    chunks.get(location(x.floorDiv(SIZE), y.floorDiv(SIZE), z.floorDiv(SIZE)))
      ?.set(x % SIZE, y % SIZE, z % SIZE, block, flags)
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
    if (chunks.remove(location(chunk.chunkPos.x, chunk.chunkPos.y, chunk.chunkPos.z)) == null) {
      logger.warn("Tried to remove nonexistent chunk at ${chunk.chunkPos}")
    }
    chunk.disposeSafely()

    forChunksAround(chunk) { rebuild() }

    logger.debug("Unloaded chunk: ${chunk.chunkPos}")
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

  val ClientChunk.hasChunksAround: Boolean
    get() {
      for (x in -1..1) for (y in -1..1) for (z in -1..1) {
        if (x == 0 && y == 0 && z == 0) {
          continue
        } else {
          chunks.get(location(chunkPos.x + x, chunkPos.y + y, chunkPos.z + z)) ?: return false
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
    for (chunk in chunks.values()) {
      chunk.rebuild()
    }
  }

  fun refreshChunks(position: Vector3D) {
    val requiredChunks = GdxArray<Pair<GridPoint3, Long>>()
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

    val toRemove = GdxArray<ClientChunk>()
    val toRebuild = GdxSet<ClientChunk>()
    for (chunk in chunks.values()) {
      if (chunk.chunkPos.dst(cx, cy, cz) > renderDistance) {
        toRemove.add(chunk)
        toRebuild.remove(chunk)
        forChunksAround(chunk) { toRebuild.add(this) }
      }
    }

    for (chunk in toRemove) {
      remove(chunk)
    }

    for (chunk in toRebuild) {
      chunk.rebuild()
    }

    requiredChunks.sortBy {
      it.first.dst2(cx.toInt(), cy.toInt(), cz.toInt())
    }
    chunksToLoad.clear()
    for (chunk in requiredChunks) {
      this.chunksToLoad.add(chunk)
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
    val (x, y, z) = (QuantumVoxel.shownScreen as GameScreen).player.getComponent(PositionComponent::class.java).position
    for (chunk in chunks.values().sortedBy {
      val chunkPos = it.chunkPos
      vec3d(chunkPos.x * SIZE.toDouble(), chunkPos.y * SIZE.toDouble(), chunkPos.z * SIZE.toDouble()).dst2(vec3d(x, y, z))
    }) {
      modelBatch.render(chunk)
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
