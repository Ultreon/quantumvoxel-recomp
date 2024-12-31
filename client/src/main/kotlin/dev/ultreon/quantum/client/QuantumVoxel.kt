@file:Suppress("t")

package dev.ultreon.quantum.client

import com.artemis.Entity
import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture.TextureFilter.Nearest
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.SharedLibraryLoader
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.client.entity.PlayerComponent
import dev.ultreon.quantum.client.entity.PositionComponent
import dev.ultreon.quantum.client.entity.RunningComponent
import dev.ultreon.quantum.client.input.KeyBinds
import dev.ultreon.quantum.client.model.JsonModelLoader
import dev.ultreon.quantum.client.resource.TexturesCategory
import dev.ultreon.quantum.client.texture.TextureManager
import dev.ultreon.quantum.client.world.ClientWorld
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.resource.ResourceManager
import dev.ultreon.quantum.util.NamespaceID
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import ktx.math.vec3
import java.util.*
import kotlin.io.path.toPath

val timer = Timer()


object QuantumVoxel : KtxGame<KtxScreen>() {
  val resourceManager: ResourceManager = ResourceManager("client")
  val jsonModelLoader: JsonModelLoader = JsonModelLoader(resourceManager)
  val textureManager: TextureManager = TextureManager(resourceManager)
  private val world = World()

  init {
    resourceManager.register("textures", TexturesCategory(textureManager))
  }

  override fun create() {
    super.create()

    textureManager.init()
    textureManager.registerAtlas("block")

    resourceManager.load(if (SharedLibraryLoader.isAndroid) {
      Gdx.files.internal("quantum.zip")
    } else {
      // Locate resource "._assetroot" and use its parent directory as the root
      val resource = QuantumVoxel::class.java.classLoader.getResource("_assetroot")
      logger.info("Asset root: $resource")
      val path = resource.toURI().toPath().parent
      logger.info("Asset root: $path")
      Gdx.files.absolute(path.toString())
    })
    textureManager.pack()

    addScreen(FirstScreen(world))
    setScreen<FirstScreen>()
  }

  override fun dispose() {
    super.dispose()

    textureManager.disposeSafely()
    world.dispose()
  }
}

class FirstScreen(world: World) : KtxScreen {
  private val speed: Float = 1f
  private val modelBatch = ModelBatch()
  private val font = BitmapFont()
  private val spriteBatch = SpriteBatch()
  private val texture = texture(NamespaceID.of(path = "block/dirt.png"))
  private val world: ClientWorld = ClientWorld()
  private val material = material {
    diffuse(texture.texture)
    cullFace(GL20.GL_BACK)
  }
  private var cube = model {
    part("cube", primitiveType = GL20.GL_TRIANGLES, material = material) {
      cube(
        x = 0f,
        y = 0f,
        z = 0f,
        width = 1f,
        height = 1f,
        depth = 1f,
        textureRegion = texture
      )
    }
  }

  val camera = perspectiveCamera {
    position.set(0f, 0f, 5f)
    near = 1f
    far = 10f
    update()
  }

  var cubeInstance = instance(cube)
  val position = vec3d(0.0, 0.0, 0.0)

  val vel = vec3(0f, 0f, 0f)

  var moveX = 0F
  var moveY = 0F

  var forward = false
  var backward = false
  var strafeLeft = false
  var strafeRight = false
  var up = false
  var down = false

  val player: Entity = world.createEntity().also {
    it.edit()
      .add(PlayerComponent("Player"))
      .add(RunningComponent(1.6F))
      .add(PositionComponent())
  }

  val environment = Environment().apply {
    set(
      ColorAttribute.createAmbientLight(1F, 1F, 1F, 1F),
    )
  }

  override fun render(delta: Float) {
    clearScreen(red = 0.0f, green = 0.0f, blue = 0.0f)

    if (Gdx.input.isKeyJustPressed(Keys.ALT_LEFT)) {
      cube.disposeSafely()

      cube = model {
        part("cube", primitiveType = GL20.GL_TRIANGLES, material = material) {
          cube(
            x = 0f,
            y = 0f,
            z = 0f,
            width = 1f,
            height = 1f,
            depth = 1f,
            textureRegion = texture
          )
        }
      }
      cubeInstance = instance(cube)
    }

    modelBatch.begin(camera)
    modelBatch.render(cubeInstance.relative(camera, position))
    modelBatch.end()

    vel.set(0f, 0f, 0f)

    moveX = 0F
    moveY = 0F

    forward = KeyBinds.walkForwardsKey.isPressed() && Gdx.input.isCursorCatched
    backward = KeyBinds.walkBackwardsKey.isPressed() && Gdx.input.isCursorCatched
    strafeLeft = KeyBinds.walkLeftKey.isPressed() && Gdx.input.isCursorCatched
    strafeRight = KeyBinds.walkRightKey.isPressed() && Gdx.input.isCursorCatched
    up = KeyBinds.jumpKey.isPressed() && Gdx.input.isCursorCatched
    down = KeyBinds.crouchKey.isPressed() && Gdx.input.isCursorCatched

    spriteBatch.use {
      font.draw(spriteBatch, "X: ${player.getComponent(PositionComponent::class.java).position.x}", 10f, 10f)
      font.draw(spriteBatch, "Y: ${player.getComponent(PositionComponent::class.java).position.y}", 10f, 20f)
      font.draw(spriteBatch, "Z: ${player.getComponent(PositionComponent::class.java).position.z}", 10f, 30f)

      font.draw(spriteBatch, "Forward: $forward", 10f, 40f)
      font.draw(spriteBatch, "Backward: $backward", 10f, 50f)
      font.draw(spriteBatch, "Strafe Left: $strafeLeft", 10f, 60f)
      font.draw(spriteBatch, "Strafe Right: $strafeRight", 10f, 70f)

      font.draw(spriteBatch, "Real X: ${this.position.x}", 10f, 80f)
      font.draw(spriteBatch, "Real Y: ${this.position.y}", 10f, 90f)
      font.draw(spriteBatch, "Real Z: ${this.position.z}", 10f, 100f)

      font.draw(spriteBatch, "X Rotation: ${player.getComponent(PositionComponent::class.java).xRot}", 10f, 110f)
      font.draw(spriteBatch, "Y Rotation: ${player.getComponent(PositionComponent::class.java).yRot}", 10f, 120f)
      font.draw(spriteBatch, "X Head Rotation: ${player.getComponent(PositionComponent::class.java).xHeadRot}", 10f, 130f)

      font.draw(spriteBatch, "Running: ${player.getComponent(RunningComponent::class.java).running}", 10f, 140f)

      font.draw(spriteBatch, "FPS: ${Gdx.graphics.framesPerSecond}", 10f, 150f)

      font.draw(spriteBatch, "Camera Position: ${camera.position}", 10f, 160f)
      font.draw(spriteBatch, "Camera Direction: ${camera.direction}", 10f, 170f)
      font.draw(spriteBatch, "Camera Up: ${camera.up}", 10f, 180f)

      val position: PositionComponent = player.getComponent(PositionComponent::class.java)
      position.xRot = (position.xRot + 180) % 360 - 180
      position.yRot = position.yRot.coerceIn(-89.99F, 89.99F)

      font.draw(spriteBatch, "Direction: " + when {
        position.yRot < -45 -> "Up"
        position.yRot > 45 -> "Down"
        position.xRot < -45 && position.xRot > -135 -> "Left"
        position.xRot > 45 && position.xRot < 135 -> "Right"
        position.xRot > 135 || position.xRot < -135 -> "Backward"
        else -> "Forward"
      }, 10f, 190f)
    }

    if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
      Gdx.input.isCursorCatched = false
    }
    if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
      Gdx.input.isCursorCatched = true
    }
    player.getComponent(RunningComponent::class.java).running =
      KeyBinds.runningKey.isPressed() && Gdx.input.isCursorCatched

    move()
    controllerMove()

    val position: PositionComponent = player.getComponent(PositionComponent::class.java)

    if (Gdx.input.isCursorCatched) {
      val deltaX = Gdx.input.deltaX
      val deltaY = Gdx.input.deltaY

      position.xRot -= deltaX * 0.5f
      position.xHeadRot = position.xRot
      position.yRot -= deltaY * 0.5f

      position.lookVec(camera.direction)
      camera.update()
    }


//    when {
//      moveX > 0 -> position.xRot = max(position.xRot - 45 / (position.xHeadRot - position.xRot + 50), position.xRot - 90)
//      moveX < -0 -> position.xRot = min(position.xRot + 45 / (position.xRot - position.xHeadRot + 50), position.xRot + 90)
//      moveY != 0F && position.xRot > position.xHeadRot -> position.xRot = max(position.xRot - (45 / (position.xRot - position.xHeadRot)), position.xHeadRot)
//      moveY != 0F && position.xRot < position.xHeadRot -> position.xRot = min(position.xRot + (45 / (position.xHeadRot - position.xRot)), position.xHeadRot)
//    }

    tmpVec.set(-moveX, 0f, moveY).nor().scl(speed)/*.rotate(Vector3.X, position.yRot + 90)*/.rotate(Vector3.Y, position.xHeadRot + 90)
    vel.set(tmpVec).scl(delta)

    if (!vel.isZero) {
      position.position.add(vel)
      this.position.set(position.position.x, position.position.y, position.position.z)
    }

    Gdx.app.graphics.setTitle("Quantum Voxel - FPS: ${Gdx.graphics.framesPerSecond}")
  }

  fun move() {
    if (!this.forward && !this.backward && !this.strafeLeft && !this.strafeRight) return
    if (this.forward) this.moveY += 1f
    if (this.backward) this.moveY -= 1f
    if (this.strafeLeft) this.moveX -= 1f
    if (this.strafeRight) this.moveX += 1f
  }

  fun controllerMove() {
    // TODO
  }

  override fun dispose() {
    modelBatch.disposeSafely()
    cube.disposeSafely()
    super.dispose()
  }

  override fun resize(width: Int, height: Int) {
    super.resize(width, height)

    camera.viewportWidth = width.toFloat()
    camera.viewportHeight = height.toFloat()
    camera.update()
  }
}
