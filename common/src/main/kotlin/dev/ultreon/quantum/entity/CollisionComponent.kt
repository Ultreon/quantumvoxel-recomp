package dev.ultreon.quantum.entity

import com.artemis.Component
import dev.ultreon.quantum.blocks.BoundingBoxD
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.util.BoundingBoxUtils
import dev.ultreon.quantum.util.Tickable
import dev.ultreon.quantum.world.Dimension
import kotlin.math.abs
import kotlin.math.log

class CollisionComponent : Component(), Tickable {
  lateinit var positionComponent: PositionComponent
  lateinit var dimension: Dimension
  private var oDx: Double = 0.0
  private var oDy: Double = 0.0
  private var oDz: Double = 0.0
  private var fallDistance: Double = 0.0
  var velocityX: Double = 0.0
  var velocityY: Double = 0.0
  var velocityZ: Double = 0.0
  var onGround: Boolean = false
  var wasOnGround: Boolean = false
  var isColliding: Boolean = false
    private set
  var isCollidingX: Boolean = false
    private set
  var isCollidingY: Boolean = false
    private set
  var isCollidingZ: Boolean = false
    private set
  val boundingBox: BoundingBoxD
    get() = BoundingBoxD(
      positionComponent.position.cpy().sub(0.3, 0.0, 0.3),
      positionComponent.position.cpy().add(0.3, 1.8, 0.3)
    )
  var noClip: Boolean = false
  var x: Double
    get() = positionComponent.position.x
    set(v) {
      positionComponent.position.x = v
    }
  var y: Double
    get() = positionComponent.position.y
    set(v) {
      positionComponent.position.y = v
    }
  var z: Double
    get() = positionComponent.position.z
    set(v) {
      positionComponent.position.z = v
    }

  var onMoved = {}

  /**
   * Moves the entity by the specified deltas.
   *
   * @param deltaX the change in x-coordinate
   * @param deltaY the change in y-coordinate
   * @param deltaZ the change in z-coordinate
   * @return true if the entity is colliding after the move, false otherwise
   */
  fun move(deltaX: Double, deltaY: Double, deltaZ: Double): Boolean {
    // Store the original deltas
    var deltaX = deltaX
    var deltaY = deltaY
    var deltaZ = deltaZ
    val originalDeltaX = deltaX
    val originalDeltaY = deltaY
    val originalDeltaZ = deltaZ

    // Calculate the absolute values of the deltas
    var absDeltaX = abs(deltaX)
    var absDeltaY = abs(deltaY)
    var absDeltaZ = abs(deltaZ)

    // Check if the deltas are too small to cause a significant move
    if (absDeltaX < 0.001 && absDeltaY < 0.001 && absDeltaZ < 0.001) {
      absDeltaX = 0.0
      absDeltaY = 0.0
      absDeltaZ = 0.0
    }

    // Trigger an event to allow modification of the move
    // TODO: Event
//    val event: EntityMoveEvent = EntityMoveEvent(this, Vec(deltaX, deltaY, deltaZ))
//    ModApi.getGlobalEventHandler().call(event)
//    val modifiedValue: Vec = event.getDelta()
//
//    if (event.isCanceled()) {
//      return this.isColliding
//    }
//
//    // If the event is canceled and a modified value is provided, update the deltas
//    deltaX = modifiedValue.x
//    deltaY = modifiedValue.y
//    deltaZ = modifiedValue.z

    // Store the original deltas after potential modification
    val originalDeltaXModified = deltaX
    val originalDeltaYModified = deltaY
    val originalDeltaZModified = deltaZ

    // Update the bounding box based on the modified deltas
    val updatedBoundingBoxD: BoundingBoxD = this.boundingBox.updateByDelta(deltaX, deltaY, deltaZ)

    // Move the entity based on the updated bounding box and deltas
    if (this.noClip) {
      this.x += deltaX
      this.y += deltaY
      this.z += deltaZ
      this.onMoved()
      // TODO: Networking
//      this.pipeline.putDouble("x", this.x)
//      this.pipeline.putDouble("y", this.y)
//      this.pipeline.putDouble("z", this.z)
    } else {
      this.moveWithCollision(
        updatedBoundingBoxD,
        deltaX,
        deltaY,
        deltaZ,
        originalDeltaXModified,
        originalDeltaYModified,
        originalDeltaZModified
      )
      this.onMoved()
      // TODO: Networking
//      this.pipeline.putDouble("x", this.x)
//      this.pipeline.putDouble("y", this.y)
//      this.pipeline.putDouble("z", this.z)
    }

    return this.isColliding
  }

  /**
   * Moves the entity with collision detection and response.
   *
   * @param ext Bounding box of the entity
   * @param dx Change in x-coordinate
   * @param dy Change in y-coordinate
   * @param dz Change in z-coordinate
   * @param oldDx Original change in x-coordinate
   * @param oldDy Original change in y-coordinate
   * @param oldDz Original change in z-coordinate
   */
  private fun moveWithCollision(
    ext: BoundingBoxD,
    dx: Double,
    dy: Double,
    dz: Double,
    oldDx: Double,
    oldDy: Double,
    oldDz: Double,
  ) {
    // Get list of bounding boxes the entity collides with
    var dx = dx
    var dy = dy
    var dz = dz
    val boxes: List<BoundingBoxD> = this.dimension.collide(ext, false)

    val pBox: BoundingBoxD = this.boundingBox // Get the entity's bounding box

    this.isColliding = false
    this.isCollidingY = false

    // Check collision and update y-coordinate
    for (box in boxes) {
      val dy2: Double = BoundingBoxUtils.clipYCollide(box, pBox, dy)
      this.isColliding = this.isColliding or (dy != dy2)
      this.isCollidingY = this.isCollidingY or (dy != dy2)
      dy = dy2
    }

    // Update the y-coordinate of the bounding box
    pBox.min.add(0.0, dy, 0.0)
    pBox.max.add(0.0, dy, 0.0)
    pBox.update()

    this.isCollidingX = false

    // Check collision and update x-coordinate
    for (box in boxes) {
      val dx2: Double = BoundingBoxUtils.clipXCollide(box, pBox, dx)
      this.isColliding = this.isColliding or (dx != dx2)
      this.isCollidingX = this.isCollidingX or (dx != dx2)
      dx = dx2
    }

    // Update the x-coordinate of the bounding box
    pBox.min.add(dx, 0.0, 0.0)
    pBox.max.add(dx, 0.0, 0.0)
    pBox.update()

    this.isCollidingZ = false

    // Check collision and update z-coordinate
    for (box in boxes) {
      val dz2: Double = BoundingBoxUtils.clipZCollide(box, pBox, dz)
      this.isColliding = this.isColliding or (dz != dz2)
      this.isCollidingZ = this.isCollidingZ or (dz != dz2)
      dz = dz2
    }

    // Update the z-coordinate of the bounding box
    pBox.min.add(0.0, 0.0, dz)
    pBox.max.add(0.0, 0.0, dz)
    pBox.update()

    // Check if entity is on the ground
    this.wasOnGround = this.onGround
    this.onGround = oldDy != dy && oldDy < 0.0f

    // Reset velocity if there was a collision in x-coordinate
    if (oldDx != dx) {
      this.velocityX = 0.0
    }

    // Reset fall distance if entity is moving upwards
    if (dy >= 0) {
      this.fallDistance = 0.0
    }

    // Handle collision responses and update fall distance
    if (this.onGround && !this.wasOnGround) {
      this.hitGround()
      this.fallDistance = 0.0
      this.velocityY = 0.0
      dy = 0.0
    } else if (dy < 0) {
      this.fallDistance -= dy
    }

    // Reset velocity if there was a collision in z-coordinate
    if (oldDz != dz) {
      this.velocityZ = 0.0
    }

    // Update entity's position
    this.x = (pBox.min.x + pBox.max.x) / 2.0f
    this.y = pBox.min.y
    this.z = (pBox.min.z + pBox.max.z) / 2.0f

    this.oDx = dx
    this.oDy = dy
    this.oDz = dz
  }

  private fun hitGround() {
    this.fallDistance = 0.0
  }

  fun move() {
    if (!this.onGround) velocityY -= 0.04
    velocityX *= 0.6
    velocityZ *= 0.6
    move(velocityX, velocityY, velocityZ)
  }

  override fun tick() {
    move()
  }
}

private fun BoundingBoxD.updateByDelta(deltaX: Double, deltaY: Double, deltaZ: Double): BoundingBoxD {
  this.min.add(deltaX, deltaY, deltaZ)
  this.max.add(deltaX, deltaY, deltaZ)
  return this
}
