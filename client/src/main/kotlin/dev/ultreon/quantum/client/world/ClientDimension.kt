package dev.ultreon.quantum.client.world

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.utils.Pool
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.model.FaceCull
import dev.ultreon.quantum.client.part
import dev.ultreon.quantum.client.relative
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.world.BlockFlags
import dev.ultreon.quantum.world.Dimension
import ktx.assets.disposeSafely
import ktx.collections.GdxArray
import ktx.collections.map

private const val SIZE = 32

class ClientDimension(private val material: Material) : Dimension(), RenderableProvider {
  val blocks = Array(SIZE) { Array(SIZE) { Array(SIZE) { Blocks.air } } }
  private var worldModel: Model? = null
  var worldModelInstance: ModelInstance? = null
    private set

  // TODO : Temporary model
  val model = QuantumVoxel.jsonModelLoader.load(Blocks.soil)

  override fun get(x: Int, y: Int, z: Int): Block {
    if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || z < 0 || z >= SIZE) return Blocks.air
    return blocks[x][y][z]
  }

  override fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags) {
    if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || z < 0 || z >= SIZE) return
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
    builder.part("world", vertexAttributes = VertexAttributes(
      VertexAttribute.Position(),
      VertexAttribute.Normal(),
      VertexAttribute.ColorPacked(),
      VertexAttribute.TexCoords(0)
    ), primitiveType = GL20.GL_TRIANGLES, material = this.material) {
      for (x in 0..15) {
        for (y in 0..15) {
          for (z in 0..15) {
            loadBlockInto(x, y, z)
          }
        }
      }
    }

    return builder.end().apply {
      meshes.map { it.numVertices }.sum().let { vertexCount ->
        logger.info("Built world model with $vertexCount vertices")
      }
      meshes.map { it.numIndices }.sum().let { indexCount ->
        logger.info("Built world model with $indexCount indices")
      }

      val verticesBuffer = meshes.get(0).getVerticesBuffer(false)
      verticesBuffer.position(0)
      for (i in 0 until verticesBuffer.limit() / 12 step 12) {
        val x = verticesBuffer[i * 3 + 0]
        val y = verticesBuffer[i * 3 + 1]
        val z = verticesBuffer[i * 3 + 2]
        var norX = verticesBuffer[i * 3 + 3]
        var norY = verticesBuffer[i * 3 + 4]
        var norZ = verticesBuffer[i * 3 + 5]
        val u = verticesBuffer[i * 3 + 6]
        val v = verticesBuffer[i * 3 + 7]
        val colR = verticesBuffer[i * 3 + 8]
        val colG = verticesBuffer[i * 3 + 9]
        val colB = verticesBuffer[i * 3 + 10]
        val colA = verticesBuffer[i * 3 + 11]
        logger.debug("Vertex ${i / 12}: ($x, $y, $z, $norX, $norY, $norZ, $u, $v, $colR, $colG, $colB, $colA)")
      }
    }
  }

  private fun MeshPartBuilder.loadBlockInto(x: Int, y: Int, z: Int) {
    val block = get(x, y, z)
    if (block != Blocks.air) {
      model?.loadInto(
        this, x, y, z, FaceCull(
          back = get(x, y, z + 1) != Blocks.air,
          front = get(x, y, z - 1) != Blocks.air,
          left = get(x - 1, y, z) != Blocks.air,
          right = get(x + 1, y, z) != Blocks.air,
          top = get(x, y + 1, z) != Blocks.air,
          bottom = get(x, y - 1, z) != Blocks.air
        )
      ) ?: throw IllegalStateException("Failed to load model")
    }
  }

  override fun getRenderables(array: GdxArray<Renderable>, pool: Pool<Renderable>) {
    worldModelInstance?.getRenderables(array, pool) ?: logger.warn("Failed to get renderable")
  }

  override fun dispose() {
    worldModel.disposeSafely()
  }
}
