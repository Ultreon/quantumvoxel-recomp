package dev.ultreon.quantum.client.world

import com.badlogic.gdx.graphics.Camera
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
import dev.ultreon.quantum.client.part
import dev.ultreon.quantum.client.relative
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.world.BlockFlags
import dev.ultreon.quantum.world.Dimension
import ktx.assets.disposeSafely
import ktx.collections.GdxArray
import ktx.collections.map

const val SIZE = 16

class ClientChunk(x: Int, y: Int, z: Int, private val material: Material, val dimension: ClientDimension) : Dimension(),
  RenderableProvider {
  val chunkPos: GridPoint3 = GridPoint3(x, y, z)

  val blocks = Array(SIZE) { Array(SIZE) { Array(SIZE) { Blocks.air } } }
  private var worldModel: Model? = null
  var worldModelInstance: ModelInstance? = null
    private set

  override fun get(x: Int, y: Int, z: Int): Block {
    return blocks[x][y][z]
  }

  override fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags) {
    blocks[x][y][z] = block
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
    worldModel.disposeSafely()
    worldModel = null
    worldModelInstance = null

    worldModel = buildModel()
    worldModelInstance = ModelInstance(worldModel)
  }

  private fun buildModel(): Model {
    val builder = ModelBuilder()
    builder.begin()
    builder.part(
      "world", vertexAttributes = VertexAttributes(
        VertexAttribute.Position(),
        VertexAttribute.Normal(),
        VertexAttribute.ColorPacked(),
        VertexAttribute.TexCoords(0)
      ), primitiveType = GL20.GL_TRIANGLES, material = this.material
    ) {
      for (x in 0..<SIZE) {
        for (y in 0..<SIZE) {
          for (z in 0..<SIZE) {
            loadBlockInto(x, y, z)
          }
        }
      }
    }

    return builder.end()
  }

  private fun MeshPartBuilder.loadBlockInto(x: Int, y: Int, z: Int) {
    val block = get(x, y, z)
    if (block != Blocks.air) {
      val model = ModelRegistry[block]
      model.loadInto(
        this, x, y, z, FaceCull(
          back = getSafe(x, y, z + 1) != Blocks.air,
          front = getSafe(x, y, z - 1) != Blocks.air,
          left = getSafe(x - 1, y, z) != Blocks.air,
          right = getSafe(x + 1, y, z) != Blocks.air,
          top = getSafe(x, y + 1, z) != Blocks.air,
          bottom = getSafe(x, y - 1, z) != Blocks.air
        )
      )
    }
  }

  fun getSafe(localX: Int, localY: Int, localZ: Int): Block {
    if (localX < 0 || localX >= SIZE || localY < 0 || localY >= SIZE || localZ < 0 || localZ >= SIZE) {
      val wx = chunkPos.x * SIZE + localX
      val wy = chunkPos.y * SIZE + localY
      val wz = chunkPos.z * SIZE + localZ
      logger.debug("$localX, $localY, $localZ :: $chunkPos :: $wx, $wy, $wz")
      return dimension[wx, wy, wz]
    }
    return this[localX, localY, localZ]
  }

  override fun getRenderables(array: GdxArray<Renderable>, pool: Pool<Renderable>) {
    worldModelInstance?.getRenderables(array, pool) ?: logger.warn("Failed to get renderable")
  }

  override fun dispose() {
    worldModel.disposeSafely()
  }

  fun reposition(camera: Camera, position: Vector3D) {
    worldModelInstance?.relative(camera,
      position.cpy()
        .sub(this.chunkPos.x * SIZE.toFloat(), this.chunkPos.y * SIZE.toFloat(), this.chunkPos.z * SIZE.toFloat())
    )
  }
}
