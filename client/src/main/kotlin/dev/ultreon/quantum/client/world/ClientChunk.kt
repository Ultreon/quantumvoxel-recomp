package dev.ultreon.quantum.client.world

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.utils.Pool
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.client.model.FaceCull
import dev.ultreon.quantum.client.model.ModelRegistry
import dev.ultreon.quantum.client.relative
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.vec3d
import dev.ultreon.quantum.world.BlockFlags
import dev.ultreon.quantum.world.Chunk
import dev.ultreon.quantum.world.SIZE
import ktx.assets.disposeSafely
import ktx.collections.GdxArray

class ClientChunk(x: Int, y: Int, z: Int, private val material: Material, val dimension: ClientDimension) : Chunk(),
  RenderableProvider {
  val start: GridPoint3
    get() = chunkPos.cpy().also {
      it.x *= SIZE
      it.y *= SIZE
      it.z *= SIZE
    }
  private var hasBlocks: Boolean = false
  private var airBlocks: Int = SIZE * SIZE * SIZE
  private var dirty: Boolean = true
  val chunkPos: GridPoint3 = GridPoint3(x, y, z)

  val blocks = Array(SIZE) { Array(SIZE) { Array(SIZE) { Blocks.air } } }
  private var worldModel: Model? = null
  var worldModelInstance: ModelInstance? = null
    private set
  override val offset: Vector3D
    get() = vec3d(chunkPos.x * SIZE, chunkPos.y * SIZE, chunkPos.z * SIZE)

  override fun get(x: Int, y: Int, z: Int): Block {
    return blocks[x][y][z]
  }

  override fun set(x: Int, y: Int, z: Int, block: Block) {
    set(x, y, z, block, BlockFlags.SYNC)
  }

  override fun isDisposed(): Boolean {
    return worldModelInstance == null
  }

  override fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags) {
    val x = (x % SIZE + SIZE) % SIZE
    val y = (y % SIZE + SIZE) % SIZE
    val z = (z % SIZE + SIZE) % SIZE
    val block1 = blocks[x][y][z]
    if (block1 == Blocks.air) {
      airBlocks--
    } else {
      airBlocks++
    }
    blocks[x][y][z] = block
    if (block == Blocks.air) {
      airBlocks++
    } else {
      airBlocks--
    }

    hasBlocks = airBlocks < SIZE * SIZE * SIZE
  }

  fun fillUpTo(y: Int, block: Block, flags: BlockFlags) {
    for (x in 0 until SIZE) {
      for (z in 0 until SIZE) {
        for (i in y downTo 0) {
          if (get(x, i, z) == Blocks.air) {
            set(x, i, z, block, flags)
          }
        }
      }
    }
  }

  fun rebuild() {
    dirty = false

    if (worldModelInstance != null || worldModel != null) {
      worldModel.disposeSafely()
      worldModel = null
      worldModelInstance = null
    }

    buildModel()
  }

  private fun buildModel(): Model? {
    if (!hasBlocks) return null

    val builder = ModelBuilder()
    builder.begin()
    val part1 = builder.part(
      "world#default", GL20.GL_TRIANGLES, VertexAttributes(
        VertexAttribute.Position(),
        VertexAttribute.Normal(),
        VertexAttribute.ColorPacked(),
        VertexAttribute.TexCoords(0)
      ), this.material
    )
    for (x in 0..<SIZE) {
      for (y in 0..<SIZE) {
        for (z in 0..<SIZE) {
          loadBlockInto(part1, x, y, z)
        }
      }
    }
    val part2 = builder.part(
      "world#water", GL20.GL_TRIANGLES, VertexAttributes(
        VertexAttribute.Position(),
        VertexAttribute.Normal(),
        VertexAttribute.ColorPacked(),
        VertexAttribute.TexCoords(0)
      ), this.material
    )
    for (x in 0..<SIZE) {
      for (y in 0..<SIZE) {
        for (z in 0..<SIZE) {
          loadBlockInto(part2, x, y, z, renderType = "water")
        }
      }
    }
    val part3 = builder.part(
      "world#water", GL20.GL_TRIANGLES, VertexAttributes(
        VertexAttribute.Position(),
        VertexAttribute.Normal(),
        VertexAttribute.ColorPacked(),
        VertexAttribute.TexCoords(0)
      ), this.material
    )
    for (x in 0..<SIZE) {
      for (y in 0..<SIZE) {
        for (z in 0..<SIZE) {
          loadBlockInto(part3, x, y, z, renderType = "foliage")
        }
      }
    }


    val model = builder.end()
    worldModel = model
    worldModelInstance = ModelInstance(worldModel)
    return model
  }

  private fun loadBlockInto(meshPartBuilder: MeshPartBuilder, x: Int, y: Int, z: Int, renderType: String = "default") {
    val block = get(x, y, z)
    if (block != Blocks.air) {
      val model = ModelRegistry[block]
      if (renderType != block?.renderType) {
        return
      }
      model.loadInto(
        meshPartBuilder, x, y, z, FaceCull(
          back = getSafe(x, y, z + 1).let { it != Blocks.air && it.renderType == block.renderType },
          front = getSafe(x, y, z - 1).let { it != Blocks.air && it.renderType == block.renderType },
          left = getSafe(x - 1, y, z).let { it != Blocks.air && it.renderType == block.renderType },
          right = getSafe(x + 1, y, z).let { it != Blocks.air && it.renderType == block.renderType },
          top = getSafe(x, y + 1, z).let { it != Blocks.air && it.renderType == block.renderType },
          bottom = getSafe(x, y - 1, z).let { it != Blocks.air && it.renderType == block.renderType }
        )
      )

      this.hasBlocks = true
    }
  }

  fun getSafe(localX: Int, localY: Int, localZ: Int): Block {
    if (localX < 0 || localX >= SIZE || localY < 0 || localY >= SIZE || localZ < 0 || localZ >= SIZE) {
      val wx = chunkPos.x * SIZE + localX
      val wy = chunkPos.y * SIZE + localY
      val wz = chunkPos.z * SIZE + localZ
      return dimension[wx, wy, wz]
    }
    return this[localX, localY, localZ]
  }

  override fun getRenderables(array: GdxArray<Renderable>, pool: Pool<Renderable>) {
    worldModelInstance?.getRenderables(array, pool)
  }

  override fun dispose() {
    worldModel.disposeSafely()
  }

  fun reposition(position: Vector3D) {
    worldModelInstance?.relative(
      position.cpy()
        .sub(this.chunkPos.x * SIZE.toFloat(), this.chunkPos.y * SIZE.toFloat(), this.chunkPos.z * SIZE.toFloat())
    )
  }

  fun markDirty() {
    this.dirty = true
  }

  val isDirty: Boolean
    get() = this.dirty
}
