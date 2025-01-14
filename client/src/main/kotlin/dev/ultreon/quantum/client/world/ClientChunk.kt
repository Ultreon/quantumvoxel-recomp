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
import kotlinx.coroutines.*
import ktx.assets.disposeSafely
import ktx.async.MainDispatcher
import ktx.collections.GdxArray

var allLoading = 0
  private set

private val lastLoad: Long get() = System.currentTimeMillis()

class ClientChunk(x: Int, y: Int, z: Int, private val material: Material, val dimension: ClientDimension) : Chunk(),
  RenderableProvider {
  private var loading: Boolean = false
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

  fun rebuild() {
    if (loading || allLoading > (Runtime.getRuntime().availableProcessors() * 2).coerceAtLeast(1)) {
      dirty = true
      return
    }

    dirty = false

    if (worldModelInstance != null || worldModel != null) {
      worldModel.disposeSafely()
      worldModel = null
      worldModelInstance = null
    }

    buildModel()
  }

  @OptIn(DelicateCoroutinesApi::class)
  private fun buildModel() {
    if (loading || !hasBlocks) return

    val builder = ModelBuilder()
    builder.begin()
    loading = true
    allLoading++
    GlobalScope.launch(dimension.context) {
      val part1 = async(MainDispatcher) {
        builder.part(
          "world#default", GL20.GL_TRIANGLES, VertexAttributes(
            VertexAttribute.Position(),
            VertexAttribute.Normal(),
            VertexAttribute.ColorPacked(),
            VertexAttribute.TexCoords(0)
          ), this@ClientChunk.material
        )
      }.await()
      val part2 = async(MainDispatcher) {
        builder.part(
          "world#water", GL20.GL_TRIANGLES, VertexAttributes(
            VertexAttribute.Position(),
            VertexAttribute.Normal(),
            VertexAttribute.ColorPacked(),
            VertexAttribute.TexCoords(0)
          ), this@ClientChunk.material
        )
      }.await()
      val part3 = async(MainDispatcher) {
        builder.part(
          "world#water", GL20.GL_TRIANGLES, VertexAttributes(
            VertexAttribute.Position(),
            VertexAttribute.Normal(),
            VertexAttribute.ColorPacked(),
            VertexAttribute.TexCoords(0)
          ), this@ClientChunk.material
        )
      }.await()
      for (x in 0..<SIZE) {
        for (y in 0..<SIZE) {
          for (z in 0..<SIZE) {
            loadBlockInto(part1, x, y, z)
          }
        }
      }
      for (x in 0..<SIZE) {
        for (y in 0..<SIZE) {
          for (z in 0..<SIZE) {
            loadBlockInto(part2, x, y, z, renderType = "water")
          }
        }
      }
      for (x in 0..<SIZE) {
        for (y in 0..<SIZE) {
          for (z in 0..<SIZE) {
            loadBlockInto(part3, x, y, z, renderType = "foliage")
          }
        }
      }
      async(MainDispatcher) {
        val model = builder.end()
        worldModel = model
        worldModelInstance = ModelInstance(worldModel)
        loading = false
        allLoading--
      }.await()
    }
  }

  private suspend fun loadBlockInto(meshPartBuilder: MeshPartBuilder, x: Int, y: Int, z: Int, renderType: String = "default") = coroutineScope {
    val block = get(x, y, z)
    if (block != Blocks.air) {
      val model = ModelRegistry[block]
      if (renderType != block.renderType) {
        return@coroutineScope
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

      this@ClientChunk.hasBlocks = true
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

  suspend fun getAsync(localX: Int, localY: Int, localZ: Int): Block = coroutineScope {
    async(MainDispatcher) {
      return@async getSafe(localX, localY, localZ)
    }
  }.await()

  override fun getRenderables(array: GdxArray<Renderable>, pool: Pool<Renderable>) {
    if (loading || !hasBlocks) return
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
  }

  fun markDirty() {
    this.dirty = true
  }

  val isDirty: Boolean
    get() = this.dirty
}
