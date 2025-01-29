package dev.ultreon.quantum.client.world

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import ktx.assets.disposeSafely
import ktx.async.KtxAsync
import ktx.async.MainDispatcher
import ktx.collections.GdxArray
import ktx.math.vec3

var allLoading = 0
  private set

private val lastLoad: Long get() = System.currentTimeMillis()

class ClientChunk(x: Int, y: Int, z: Int, private val material: Material, val dimension: ClientDimension) : Chunk(),
  RenderableProvider {
  private val _boundingBox: BoundingBox = BoundingBox()
  val boundingBox: BoundingBox
    get() {
      _boundingBox.min.set(renderPosition)
      _boundingBox.max.set(renderPosition).add(SIZE.toFloat(), SIZE.toFloat(), SIZE.toFloat())
      return _boundingBox
    }

  val renderPosition: Vector3 = vec3()
  internal var loading: Boolean = true
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
    val nx = (x % SIZE + SIZE) % SIZE
    val ny = (y % SIZE + SIZE) % SIZE
    val nz = (z % SIZE + SIZE) % SIZE
    val block1 = blocks[nx][ny][nz]
    if (block1 == Blocks.air) {
      airBlocks--
    } else {
      airBlocks++
    }
    blocks[nx][ny][nz] = block
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

  fun rebuild(blocking: Boolean = false) {
    dirty = false
    allLoading++
    loading = true

    if (blocking) {
      runBlocking {
        buildModel()
      }
    } else {
      KtxAsync.launch(MainDispatcher) {
        buildModel()
      }
    }
  }

  suspend fun rebuildAsync() {
    dirty = false
    allLoading++
    loading = true

    buildModel()
  }

  suspend fun buildModel() {
    val builder = ModelBuilder()
    builder.begin()
    allLoading++
    val part1 = builder.part(
      "world#default", GL20.GL_TRIANGLES, VertexAttributes(
        VertexAttribute.Position(),
        VertexAttribute.Normal(),
        VertexAttribute.ColorPacked(),
        VertexAttribute.TexCoords(0)
      ), this@ClientChunk.material
    )
    val part2 = builder.part(
      "world#water", GL20.GL_TRIANGLES, VertexAttributes(
        VertexAttribute.Position(),
        VertexAttribute.Normal(),
        VertexAttribute.ColorPacked(),
        VertexAttribute.TexCoords(0)
      ), this@ClientChunk.material
    )
    val part3 = builder.part(
      "world#water", GL20.GL_TRIANGLES, VertexAttributes(
        VertexAttribute.Position(),
        VertexAttribute.Normal(),
        VertexAttribute.ColorPacked(),
        VertexAttribute.TexCoords(0)
      ), this@ClientChunk.material
    )
    for (x in 0..<SIZE) {
      for (y in 0..<SIZE) {
        for (z in 0..<SIZE) {
          loadBlockInto(part1, x, y, z)
        }
      }
    }

    yield()
    for (x in 0..<SIZE) {
      for (y in 0..<SIZE) {
        for (z in 0..<SIZE) {
          loadBlockInto(part2, x, y, z, renderType = "water")
        }
      }
    }

    yield()
    for (x in 0..<SIZE) {
      for (y in 0..<SIZE) {
        for (z in 0..<SIZE) {
          loadBlockInto(part3, x, y, z, renderType = "foliage")
        }
      }
    }

    yield()

    // Hotswap model and model instance
    if (worldModelInstance != null || worldModel != null) {
      worldModel.disposeSafely()
      worldModel = null
      worldModelInstance = null
    }

    val model = builder.end()
    worldModel = model
    worldModelInstance = ModelInstance(worldModel)
    loading = false
    allLoading--

    yield()
  }

  private fun loadBlockInto(
    meshPartBuilder: MeshPartBuilder,
    x: Int,
    y: Int,
    z: Int,
    renderType: String = "default",
  ) {
    val block = getSafe(x, y, z)
    if (block != Blocks.air) {
      val model = ModelRegistry[block]
      if (renderType != block.renderType) {
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

  suspend fun getAsync(localX: Int, localY: Int, localZ: Int): Block {
    val block = getSafe(localX, localY, localZ)
    yield()
    return block
  }

  override fun getRenderables(array: GdxArray<Renderable>, pool: Pool<Renderable>) {
    if (!hasBlocks) return
    worldModelInstance?.getRenderables(array, pool)
  }

  fun disposeChunk(): Boolean {
    if (loading) {
      return false
    }
    worldModel.disposeSafely()
    return true
  }

  override fun dispose() {
    throw UnsupportedOperationException()
  }

  fun reposition(position: Vector3D) {
    worldModelInstance?.relative(
      position.cpy()
        .sub(this.chunkPos.x * SIZE.toFloat(), this.chunkPos.y * SIZE.toFloat(), this.chunkPos.z * SIZE.toFloat())
    )

    worldModelInstance?.transform?.getTranslation(renderPosition)
  }

  fun markDirty() {
    this.dirty = true
  }

  val isDirty: Boolean
    get() = this.dirty
}
