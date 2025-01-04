@file:Suppress("t", "GDXKotlinStaticResource")

package dev.ultreon.quantum.client

import com.artemis.Component
import com.artemis.Entity
import com.artemis.World
import com.artemis.utils.Bag
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.async.AsyncExecutor
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.client.QuantumVoxel.jsonModelLoader
import dev.ultreon.quantum.client.QuantumVoxel.resourceManager
import dev.ultreon.quantum.client.QuantumVoxel.textureManager
import dev.ultreon.quantum.client.QuantumVoxel.world
import dev.ultreon.quantum.client.model.JsonModelLoader
import dev.ultreon.quantum.client.model.ModelRegistry
import dev.ultreon.quantum.client.resource.TexturesCategory
import dev.ultreon.quantum.client.texture.TextureManager
import dev.ultreon.quantum.client.world.ClientDimension
import dev.ultreon.quantum.entity.CollisionComponent
import dev.ultreon.quantum.entity.PlayerComponent
import dev.ultreon.quantum.entity.PositionComponent
import dev.ultreon.quantum.entity.RunningComponent
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.resource.ResourceManager
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.quantum.util.Tickable
import dev.ultreon.quantum.vec3d
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import java.io.FileNotFoundException
import java.util.concurrent.CompletableFuture
import kotlin.math.min

const val MINIMUM_WIDTH = 550
const val MINIMUM_HEIGHT = 300

const val TPS = 20
const val SPT = 1F / TPS

lateinit var gamePlatform: GamePlatform

/**
 * The `QuantumVoxel` object represents the main entry point for the Quantum Voxel application.
 * It extends the [KtxGame] class, managing the lifecycle of the game, including initialization,
 * rendering, and resource management.
 *
 * Functionality:
 * - Manages game screens, world instance, and resources.
 * - Initializes and loads resources such as textures, shaders, and models.
 * - Handles application crashes by displaying the crash stack trace on the screen.
 * - Provides access to core components like the [resourceManager], [jsonModelLoader], [textureManager],
 *   and the game [world].
 *
 * Lifecycle:
 * - The `create` method initializes the game environment, including graphics, texture management,
 *   resource loading, and sets up the initial screen.
 * - The `render` method manages the drawing lifecycle, including handling and rendering crash details if any exception occurs.
 * - The `dispose` method cleans up resources and disposes of components safely when the game is terminated.
 */
object QuantumVoxel : KtxGame<KtxScreen>(clearScreen = false) {
  private val bag: Bag<Component> = Bag()
  var environmentRenderer: EnvironmentRenderer? = null
  var player: Entity? = null
  var dimension: ClientDimension? = null
  private lateinit var crashFont: BitmapFont
  private lateinit var crashSpriteBatch: SpriteBatch
  private var crash: Exception? = null
  var setGuiScale = 0
    set(value) {
      field = value.coerceAtLeast(0)
    }
  private val texture by lazy { texture(NamespaceID.of(path = "textures/block/soil.png")) }
  val material by lazy {
    material {
      diffuse(texture.texture)
      cullFace(GL20.GL_BACK)
      blendMode(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
      depthTest(true, GL20.GL_LESS, 0.01f, 1000f)
    }
  }

  private var width = 1
  private var height = 1

  var frameTick = 0F

  val executor: AsyncExecutor = AsyncExecutor(Runtime.getRuntime().availableProcessors(), "QV:Async Worker")

  /**
   * Manages and organizes resources such as textures, models, and shaders.
   * Provides a centralized framework for accessing and registering resource categories.
   * Plays a key role in resource loading, initialization, and memory management.
   * Integrally involved in the application lifecycle to ensure all necessary assets are available.
   */
  val resourceManager: ResourceManager = ResourceManager("client")

  val font by lazy {
    try {
      BitmapFont(
        ResourceVfsFileHandle(NamespaceID.of(path = "fonts/luna_pixel.fnt")),
        ResourceVfsFileHandle(NamespaceID.of(path = "textures/font/luna_pixel.png")),
        false
      )
    } catch (e: FileNotFoundException) {
      logger.error("Failed to load font:\n${e.stackTraceToString()}")
      BitmapFont()
    }
  }

  /**
   * A loader specifically designed to manage the parsing and loading of models from JSON files.
   * Utilizes the provided `ResourceManager` to access and organize relevant resources needed for model generation.
   * Facilitates the integration of JSON-based assets into the application's resource pipeline.
   */
  val jsonModelLoader: JsonModelLoader = JsonModelLoader(resourceManager)

  /**
   * An instance of `TextureManager` responsible for handling texture-related tasks within the application.
   * Coordinates the loading, initialization, registration, and organization of texture assets.
   * Works in conjunction with the `ResourceManager` to manage texture resources efficiently.
   * Plays a crucial role in maintaining texture data during the application's lifecycle.
   */
  val textureManager: TextureManager = TextureManager(resourceManager)

  @JvmField
  val world = World()

  init {
    resourceManager.register("textures", TexturesCategory(textureManager))
    resourceManager.register("shaders", SimpleCategory("shaders", null))
    resourceManager.register("fonts", SimpleCategory("fonts", null))
    resourceManager.register("models", ModelsCategory().apply {
      register("blocks", SimpleCategory("blocks", this))
      register("items", SimpleCategory("items", this))
      register("entities", SimpleCategory("entities", this))
    })
  }

  /**
   * Initializes the application and sets up the necessary resources, screens, and configurations.
   */
  override fun create() {
    super.create()

    try {
      Gdx.graphics.setVSync(false)

      width = Gdx.graphics.width.coerceAtLeast(MINIMUM_WIDTH)
      height = Gdx.graphics.height.coerceAtLeast(MINIMUM_HEIGHT)

      Blocks.init()

      textureManager.init()
      textureManager.registerAtlas("block")
      textureManager.registerAtlas("font")

      if (!Gdx.files.isLocalStorageAvailable || Gdx.files.internal("quantum.zip")
          .exists() || System.getProperty("quantum.nativeimage")?.toBoolean() == true
      ) {
        resourceManager.load(Gdx.files.internal("quantum.zip"))
      } else {
        gamePlatform.loadResources(resourceManager)
      }

      textureManager.pack()
      ModelRegistry.loadModels()

      startWorld()

//      addScreen(GameRenderer())
//      setScreen<GameRenderer>()

      logger.debug("Quantum Voxel started!", this)
    } catch (e: Exception) {
      logger.error(e.stackTraceToString())
      this.crash = e
      this.crashSpriteBatch = SpriteBatch()
      this.crashFont = BitmapFont()
    }
  }

  private fun startWorld() {
    dimension = ClientDimension(material)
    player = world.createEntity().also { entity ->
      val positionComponent = PositionComponent(vec3d(0, 128, 0))
      entity.edit()
        .add(PlayerComponent("Player"))
        .add(RunningComponent(1.6F))
        .add(positionComponent)
        .add(CollisionComponent().also {
          it.positionComponent = positionComponent
          it.dimension = dimension!!
        })

      environmentRenderer?.lastRefreshPosition?.set(entity.getComponent(PositionComponent::class.java).position)
    }

    environmentRenderer = EnvironmentRenderer()
  }

  /**
   * Releases all resources associated with this instance to prevent memory leaks.
   */
  override fun dispose() {
    super.dispose()

    textureManager.dispose()
    world.dispose()

    ModelRegistry.dispose()

    gamePlatform.dispose()
  }

  /**
   * Renders the application frame, handling both normal operation and crash scenarios.
   *
   * If a crash exception is present, the method clears the screen, switches to a crash rendering mode,
   * and outputs the stack trace of the exception to the screen for debugging purposes.
   *
   * In the normal operation case (no crash), the method delegates rendering to the superclass implementation.
   */
  override fun render() {
    val crash = crash

    if (frameTick > SPT) {
      frameTick -= SPT

      doTick()
    }

    frameTick += Gdx.graphics.deltaTime

    if (crash != null) {
      clearScreen(0f, 0f, 0f, 1f)
      crashSpriteBatch.begin()
      crashFont.color = Color.WHITE
      val message = crash.stackTraceToString().replace("\t", "    ")
      for ((index, line) in message.lines().withIndex()) {
        crashFont.draw(crashSpriteBatch, line, 20f, Gdx.graphics.height.toFloat() - 20f - (index * 12f))
      }
      crashSpriteBatch.end()
      return
    }

    this.environmentRenderer?.render(Gdx.graphics.deltaTime) ?: {
      clearScreen(0f, 1f, 0f, 1f)
    }

//    super.render()

    gamePlatform.nextFrame()
  }

  private fun doTick() {
    dimension?.tick()
    bag.clear()
    player?.let {
      world.componentManager.getComponentsFor(it.id, bag).forEach { component ->
        if (component is Tickable) {
          component.tick()
        }
      }
    }
  }

  /**
   * Calculates the maximum GUI scale that can be applied based on the current window dimensions
   * and predefined minimum width and height constraints.
   *
   * The method determines the scale by comparing the ratios of the window's width and height
   * to the defined minimum dimensions `MINIMUM_WIDTH` and `MINIMUM_HEIGHT`. It ensures that
   * the resulting scale is at least 1 to maintain usability.
   *
   * @return The maximum allowable GUI scale as an integer value.
   */
  fun calcMaxGuiScale(): Int {
    var windowWidth = width
    var windowHeight = height

    if (windowWidth / MINIMUM_WIDTH < windowHeight / MINIMUM_HEIGHT) {
      return (windowWidth / MINIMUM_WIDTH).coerceAtLeast(1)
    }

    if (windowHeight / MINIMUM_HEIGHT < windowWidth / MINIMUM_WIDTH) {
      return (windowHeight / MINIMUM_HEIGHT).coerceAtLeast(1)
    }

    val min = min(windowWidth / MINIMUM_WIDTH, windowHeight / MINIMUM_HEIGHT)
    return min.coerceAtLeast(1)
  }

  var guiScale: Float =
    (if (setGuiScale <= 0) calcMaxGuiScale() else setGuiScale.coerceAtMost(calcMaxGuiScale())).toFloat()
    private set

  override fun resize(width: Int, height: Int) {
    super.resize(width, height)

    this.width = width
    this.height = height

    guiScale = (if (setGuiScale <= 0) calcMaxGuiScale() else setGuiScale.coerceAtMost(calcMaxGuiScale())).toFloat()

    environmentRenderer?.resize(width, height)
  }

  operator fun <T> invoke(block: (QuantumVoxel) -> T): CompletableFuture<T> {
    val completableFuture = CompletableFuture<T>()
    Gdx.app.postRunnable {
      try {
        completableFuture.complete(block(this))
      } catch (e: Exception) {
        completableFuture.completeExceptionally(e)
      }
    }

    return completableFuture
  }
}

