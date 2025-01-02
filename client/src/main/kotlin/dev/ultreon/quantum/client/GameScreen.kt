package dev.ultreon.quantum.client

import com.artemis.Entity
import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.math.Vector3
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.entity.PlayerComponent
import dev.ultreon.quantum.entity.PositionComponent
import dev.ultreon.quantum.entity.RunningComponent
import dev.ultreon.quantum.client.input.KeyBinds
import dev.ultreon.quantum.client.world.ClientDimension
import dev.ultreon.quantum.client.world.Skybox
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.quantum.vec3d
import dev.ultreon.quantum.world.BlockFlags
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import ktx.math.vec3

/**
 * Represents the main game screen responsible for rendering and managing the player's interaction
 * with the game world. This class implements the `KtxScreen` interface and handles functionality
 * such as input handling, rendering, player movement, camera operations, and resource management.
 *
 * The `GameScreen` for example manages:
 * - Rendering 3D models and 2D overlays.
 * - Camera and viewport configurations.
 * - Input processing for movement, actions, and interaction.
 * - Player entity initialization and behavior updates.
 * - Environmental light and textures.
 *
 * @constructor Creates a `GameScreen` instance that initializes the game world, rendering environment,
 *              camera, player entity, and necessary assets.
 * @param world Represents the Artemis `World` instance holding the ECS (Entity Component System) structure
 *              for managing entities and components.
 */
class GameScreen(world: World) : KtxScreen {
  private var lastRefreshPosition: Vector3D = vec3d()
  private var lastRefreshTime: Long = 0
  private var lastPollTime: Long = 0
  private val speed: Float = 1f
  private val modelBatch = ModelBatch(
    QuantumVoxel.resourceManager[NamespaceID.of(path = "shaders/default.vsh")].text,
    QuantumVoxel.resourceManager[NamespaceID.of(path = "shaders/default.fsh")].text
  )
  private val font = BitmapFont()
  private val spriteBatch = SpriteBatch()
  private val texture = texture(NamespaceID.of(path = "textures/block/soil.png"))
  val material = material {
    diffuse(texture.texture)
    cullFace(GL20.GL_BACK)
  }
  private val dimension: ClientDimension = ClientDimension(material)

  val camera = perspectiveCamera {
    position.set(0f, 0f, 0f)
    near = 0.01f
    far = 500f
    update()
  }

  val position = vec3d(0.0, 65.0, 0.0)

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
      .add(PositionComponent(vec3d(0, 65, 0)))

    lastRefreshPosition.set(it.getComponent(PositionComponent::class.java).position)
  }

  val environment = Environment().apply {
    set(ColorAttribute.createAmbientLight(1F, 1F, 1F, 1F))
  }

  val skybox = Skybox()

  init {
//    world.inject(player)

    dimension.refreshChunks(player.getComponent(PositionComponent::class.java).position)
  }

  /**
   * Renders the frame for the game world. This method is called every frame and handles all rendering
   * and game state updates based on the time delta between frames.
   *
   * @param delta A `Float` value representing the time elapsed since the last frame, used to scale time-dependent calculations.
   */
  override fun render(delta: Float) {
    val position: PositionComponent = player.getComponent(PositionComponent::class.java)

    clearScreen(red = 0F, green = 0F, blue = 0F)
    Gdx.gl.glDepthMask(false)
    skybox.render(camera, position.xRot)

    dimension.updateLocations(camera, position.position)

    Gdx.gl.glDepthMask(true)
    modelBatch.begin(camera)
    modelBatch.renderContext.setBlending(false, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    modelBatch.render(dimension)
    modelBatch.end()

    vel.set(0f, 0f, 0f)

    moveX = 0F
    moveY = 0F

    input()

    spriteBatch.use {
      drawInfo(position)
    }

    move()
    controllerMove()

    if (Gdx.input.isCursorCatched || gamePlatform.isMobile) {
      look(position)
    }

    move(position, delta)

    if (lastPollTime + 100 < System.currentTimeMillis()) {
      dimension.pollChunkLoad()
      lastPollTime = System.currentTimeMillis()
    }

    if (lastRefreshTime + 1000 < System.currentTimeMillis()) {
      if (position.position != lastRefreshPosition) {
        dimension.refreshChunks(player.getComponent(PositionComponent::class.java).position)
        lastRefreshTime = System.currentTimeMillis()
        lastRefreshPosition = position.position.copy()
      } else {
        lastRefreshTime = System.currentTimeMillis()
      }
    }

    Gdx.app.graphics.setTitle("Quantum Voxel - FPS: ${Gdx.graphics.framesPerSecond}")
  }

  /**
   * Updates the position and velocity of the player based on the current movement inputs and delta time.
   *
   * @param position A `PositionComponent` representing the current position and rotation state of the entity.
   * @param delta A `Float` representing the time elapsed since the last frame, used for scaling movement.
   */
  private fun move(position: PositionComponent, delta: Float) {
//    when {
//      moveX > 0 -> position.xRot = max(position.xRot - 45 / (position.xHeadRot - position.xRot + 50), position.xRot - 90)
//      moveX < -0 -> position.xRot = min(position.xRot + 45 / (position.xRot - position.xHeadRot + 50), position.xRot + 90)
//      moveY != 0F && position.xRot > position.xHeadRot -> position.xRot = max(position.xRot - (45 / (position.xRot - position.xHeadRot)), position.xHeadRot)
//      moveY != 0F && position.xRot < position.xHeadRot -> position.xRot = min(position.xRot + (45 / (position.xHeadRot - position.xRot)), position.xHeadRot)
//    }

    tmpVec.set(moveX, 0f, -moveY).nor().scl(speed)/*.rotate(Vector3.X, position.yRot + 90)*/
      .rotate(Vector3.Y, position.xHeadRot + 90)

    var flight = 0F

    if (up) {
      flight -= 1f
    }
    if (down) {
      flight += 1f
    }
    vel.set(tmpVec).add(0F, -flight, 0F).scl(delta * 10)

    if (!vel.isZero) {
      position.position.add(vel)
      this.position.set(position.position.x, position.position.y, position.position.z)
    }
  }

  /**
   * Updates the player's rotation and camera direction based on mouse movement.
   *
   * @param position A `PositionComponent` that represents the current position and rotation state of the player.
   */
  private fun look(position: PositionComponent) {
    val deltaX = Gdx.input.deltaX
    val deltaY = Gdx.input.deltaY

    position.xRot -= deltaX * 0.5f
    position.xHeadRot = position.xRot
    position.yRot += deltaY * 0.5f

    position.xRot = (position.xRot + 180) % 360 - 180
    position.yRot = position.yRot.coerceIn(-89.99F, 89.99F)

    position.lookVec(camera.direction)
    camera.update()
  }

  private fun input() {
    forward = KeyBinds.walkForwardsKey.isPressed() && (Gdx.input.isCursorCatched || gamePlatform.isMobile)
    backward = KeyBinds.walkBackwardsKey.isPressed() && (Gdx.input.isCursorCatched || gamePlatform.isMobile)
    strafeLeft = KeyBinds.walkLeftKey.isPressed() && (Gdx.input.isCursorCatched || gamePlatform.isMobile)
    strafeRight = KeyBinds.walkRightKey.isPressed() && (Gdx.input.isCursorCatched || gamePlatform.isMobile)
    up = KeyBinds.jumpKey.isPressed() && (Gdx.input.isCursorCatched || gamePlatform.isMobile)
    down = KeyBinds.crouchKey.isPressed() && (Gdx.input.isCursorCatched || gamePlatform.isMobile)

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !gamePlatform.isMobile) {
      Gdx.input.isCursorCatched = false
    }
    if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !gamePlatform.isMobile) {
      Gdx.input.isCursorCatched = true
    }
    player.getComponent(RunningComponent::class.java).running =
      KeyBinds.runningKey.isPressed() && (Gdx.input.isCursorCatched || gamePlatform.isMobile)

    if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
      dimension.rebuildAll()
    }
  }

  /**
   * Renders various debugging and informational data as an overlay on the screen.
   *
   * This function displays the player's current position, movement states, rotation values,
   * camera details, and frames per second (FPS). It uses a `SpriteBatch` instance to draw each line of text.
   *
   * @param position The player's current position and rotation, represented by a `PositionComponent` instance.
   */
  private fun drawInfo(position: PositionComponent) {
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
    font.draw(
      spriteBatch, "Direction: " + when {
        position.yRot < -45 -> "Up"
        position.yRot > 45 -> "Down"
        position.xRot < -45 && position.xRot > -135 -> "Left"
        position.xRot > 45 && position.xRot < 135 -> "Right"
        position.xRot > 135 || position.xRot < -135 -> "Backward"
        else -> "Forward"
      }, 10f, 190f
    )

    font.draw(spriteBatch, "Velocity: $vel", 10f, 200f)
    font.draw(spriteBatch, "Chunk Position: ${position.chunkPosition}", 10f, 210f)

    font.draw(spriteBatch, "Is Mobile: ${gamePlatform.isMobile}", 10f, 220f)
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
    skybox.disposeSafely()
    spriteBatch.disposeSafely()
    super.dispose()
  }

  /**
   * Adjusts the camera viewport dimensions to match the new width and height of the window.
   *
   * This method ensures that the camera's viewport is updated whenever the game window is resized.
   * It invokes the parent class's `resize` method, recalculates the camera's viewport width and height
   * based on the provided dimensions, and updates the camera configuration.
   *
   * @param width The new width of the game window in pixels.
   * @param height The new height of the game window in pixels.
   */
  override fun resize(width: Int, height: Int) {
    super.resize(width, height)

    camera.viewportWidth = width.toFloat()
    camera.viewportHeight = height.toFloat()
    camera.update()

    spriteBatch.projectionMatrix = spriteBatch.projectionMatrix.setToOrtho2D(0f, 0f, width.toFloat(), height.toFloat())
  }
}
