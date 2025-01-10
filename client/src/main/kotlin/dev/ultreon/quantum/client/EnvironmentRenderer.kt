@file:Suppress("t")

package dev.ultreon.quantum.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.github.tommyettinger.textra.Font
import com.github.tommyettinger.textra.Layout
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.client.QuantumVoxel.dimension
import dev.ultreon.quantum.client.QuantumVoxel.gameInput
import dev.ultreon.quantum.client.QuantumVoxel.player
import dev.ultreon.quantum.client.input.KeyBinds
import dev.ultreon.quantum.client.world.Skybox
import dev.ultreon.quantum.entity.CollisionComponent
import dev.ultreon.quantum.entity.PositionComponent
import dev.ultreon.quantum.entity.RunningComponent
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.util.BlockHit
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.quantum.vec3d
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import ktx.math.vec3
import javax.accessibility.AccessibleEditableText

private val tmp1 = vec3()

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
 */
class EnvironmentRenderer {
  private var lastHit: BlockHit? = null
  private val backupMatrix: Matrix4 = Matrix4()
  internal var lastRefreshPosition: Vector3D = vec3d()
  private var lastRefreshTime: Long = 0
  private var lastPollTime: Long = 0
  private val speed: Float = 6f
  private val modelBatch = ModelBatch(
    QuantumVoxel.resourceManager[NamespaceID.of(path = "shaders/default.vsh")].text,
    QuantumVoxel.resourceManager[NamespaceID.of(path = "shaders/default.fsh")].text
  )
  private val font = QuantumVoxel.font
  private val spriteBatch = SpriteBatch()
  val camera = perspectiveCamera {
    position.set(0f, 1.6f, 0f)
    near = 0.01f
    far = 500f
    update()
  }

  val vel = vec3(0f, 0f, 0f)

  var moveX = 0F
  var moveY = 0F

  var forward = false
  var backward = false
  var strafeLeft = false
  var strafeRight = false
  var up = false
  var down = false

  val environment = Environment().apply {
    set(ColorAttribute.createAmbientLight(1F, 1F, 1F, 1F))
  }

  val skybox = Skybox()

  init {
//    world.inject(player)

    dimension!!.refreshChunks(player!!.getComponent(PositionComponent::class.java).position)
  }

  /**
   * Renders the frame for the game world. This method is called every frame and handles all rendering
   * and game state updates based on the time delta between frames.
   *
   * @param delta A `Float` value representing the time elapsed since the last frame, used to scale time-dependent calculations.
   */
  fun render(delta: Float) {
    val player = player
    val dimension = dimension
    if (player == null || dimension == null) {
      logger.warn("Player or dimension is null")
      return
    }
    val position: PositionComponent = player.getComponent(PositionComponent::class.java)

    clearScreen(red = 0F, green = 0F, blue = 0F)
    Gdx.gl.glDepthMask(false)
    skybox.render(camera, position.xRot)

    dimension.updateLocations(position.position)

    Gdx.gl.glDepthMask(true)
    modelBatch.begin(camera)
    dimension.render(modelBatch)
    modelBatch.end()

    vel.set(0f, 0f, 0f)

    moveX = 0F
    moveY = 0F

    input()

    move()
    controllerMove()

    if (Gdx.input.isCursorCatched || gamePlatform.isMobile) {
      look(position)
    }

    move(position, delta)

    if (lastPollTime + 10 < System.currentTimeMillis()) {
      dimension.pollChunkLoad()
      lastPollTime = System.currentTimeMillis()
    }

    if (lastRefreshTime + 100 < System.currentTimeMillis()) {
      if (position.position != lastRefreshPosition) {
        dimension.refreshChunks(player.getComponent(PositionComponent::class.java).position)
        lastRefreshTime = System.currentTimeMillis()
        lastRefreshPosition = position.position.copy()
      } else {
        lastRefreshTime = System.currentTimeMillis()
      }
    }
  }

  private fun rayCast(): BlockHit {
    val position = player!!.getComponent(PositionComponent::class.java)
    val hit = dimension!!.rayCast(position.position.cpy().add(0.0, 1.6, 0.0), vec3d().also {
      val vec = tmp1
      position.lookVec(vec)
      it.set(vec.x, vec.y, vec.z)
    })
    this.lastHit = hit
    return hit
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

    vel.set(tmpVec).add(0F, -flight, 0F)

    val collision = player!!.getComponent(CollisionComponent::class.java) ?: return
    if (vel.x != 0F) collision.velocityX = vel.x.toDouble() / TPS
    if (up && collision.onGround) collision.velocityY = 0.4
    if (vel.z != 0F) collision.velocityZ = vel.z.toDouble() / TPS
  }

  /**
   * Updates the player's rotation and camera direction based on mouse movement.
   *
   * @param position A `PositionComponent` that represents the current position and rotation state of the player.
   */
  private fun look(position: PositionComponent) {
    val deltaX = if (gameInput.isMouseSupported) gameInput.mouseDeltaX else Gdx.input.deltaX.toFloat()
    val deltaY = if (gameInput.isMouseSupported) gameInput.mouseDeltaY else Gdx.input.deltaY.toFloat()

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
      if (Gdx.input.isCursorCatched) {
        rayCast().let {
          val collide = it.isCollide
          if (collide) {
            dimension!![it.point.x, it.point.y, it.point.z] = Blocks.air
          }
        }
      }
      Gdx.input.isCursorCatched = true
    }
    player!!.getComponent(RunningComponent::class.java).running =
      KeyBinds.runningKey.isPressed() && (Gdx.input.isCursorCatched || gamePlatform.isMobile)

    if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
      dimension!!.rebuildAll()
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
    backupMatrix.set(spriteBatch.transformMatrix)
    spriteBatch.transformMatrix =
      spriteBatch.transformMatrix.scale(QuantumVoxel.guiScale, QuantumVoxel.guiScale, QuantumVoxel.guiScale)

    val pos = player!!.getComponent(PositionComponent::class.java).position

    try {
      font.draw(
        spriteBatch,
        "[gold]X: [white]${pos.x}",
        10f,
        10f
      )
      font.draw(
        spriteBatch,
        "Y: ${pos.y}",
        10f,
        20f
      )
      font.draw(
        spriteBatch,
        "Z: ${pos.z}",
        10f,
        30f
      )

      font.draw(spriteBatch, "[gold]Forward: [white]$forward", 10f, 40f)
      font.draw(spriteBatch, "[gold]Backward: [white]$backward", 10f, 50f)
      font.draw(spriteBatch, "[gold]Strafe Left: [white]$strafeLeft", 10f, 60f)
      font.draw(spriteBatch, "[gold]Strafe Right: [white]$strafeRight", 10f, 70f)

      font.draw(
        spriteBatch,
        "[gold]X Rotation: [white]${player!!.getComponent(PositionComponent::class.java).xRot}",
        10f,
        110f
      )
      font.draw(
        spriteBatch,
        "[gold]Y Rotation: [white]${player!!.getComponent(PositionComponent::class.java).yRot}",
        10f,
        120f
      )
      font.draw(
        spriteBatch,
        "[gold]X Head Rotation: [white]${player!!.getComponent(PositionComponent::class.java).xHeadRot}",
        10f,
        130f
      )

      font.draw(
        spriteBatch,
        "[gold]Running: [white]${player!!.getComponent(RunningComponent::class.java).running}",
        10f,
        140f
      )

      font.draw(spriteBatch, "[gold]FPS:[white]${Gdx.graphics.framesPerSecond}", 10f, 150f)

      font.draw(spriteBatch, "[gold]Camera Position: [white]${camera.position}", 10f, 160f)
      font.draw(spriteBatch, "[gold]Camera Direction: [white]${camera.direction}", 10f, 170f)
      font.draw(spriteBatch, "[gold]Camera Up: [white]${camera.up}", 10f, 180f)
      font.draw(
        spriteBatch, "[gold]Direction: [white]" + when {
          position.yRot < -45 -> "Up"
          position.yRot > 45 -> "Down"
          position.xRot < -45 && position.xRot > -135 -> "Left"
          position.xRot > 45 && position.xRot < 135 -> "Right"
          position.xRot > 135 || position.xRot < -135 -> "Backward"
          else -> "Forward"
        }, 10f, 190f
      )

      font.draw(spriteBatch, "[gold]Velocity: [white]$vel", 10f, 200f)
      font.draw(spriteBatch, "[gold]Chunk Position: [white]${position.chunkPosition}", 10f, 210f)

      font.draw(spriteBatch, "[gold]Is Mobile: [white]${gamePlatform.isMobile}", 10f, 220f)

      font.draw(spriteBatch, "[gold]Gui Scale: [white]${QuantumVoxel.guiScale}", 10f, 230f)
      font.draw(spriteBatch, "[gold]Intersected at: [white]${lastHit?.point}", 10f, 240f)
    } finally {
      spriteBatch.transformMatrix.set(backupMatrix)
    }
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

  fun dispose() {
    modelBatch.disposeSafely()
    skybox.disposeSafely()
    spriteBatch.disposeSafely()
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
  fun resize(width: Int, height: Int) {
    camera.viewportWidth = width.toFloat()
    camera.viewportHeight = height.toFloat()
    camera.update()

    spriteBatch.projectionMatrix =
      spriteBatch.projectionMatrix.setToOrtho2D(0f, 0f, width.toFloat(), height.toFloat())
  }
}

private val layout = Layout()
