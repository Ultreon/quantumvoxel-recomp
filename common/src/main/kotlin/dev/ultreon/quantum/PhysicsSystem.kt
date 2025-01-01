package dev.ultreon.quantum

import com.artemis.Entity
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.math.Vector3
import dev.ultreon.quantum.blocks.BoundingBoxD
import dev.ultreon.quantum.world.Dimension
import ktx.artemis.allOf
import ktx.collections.GdxArray

/** System to handle collisions. */
class PhysicsSystem(
  private val dimension: Dimension,
) : IteratingSystem(allOf(CollisionComponent::class)) {

  private val blockBoundingBoxes = GdxArray<BoundingBoxD>()

  override fun process(entityId: Int) {
    val entity = world.getEntity(entityId)
    if (entity != null) {
      processEntity(entity, 0f)
    }
  }

  fun processEntity(entity: Entity, deltaTime: Float) {
    val collision = entity.getComponent(CollisionComponent::class.java)
    val bounds = collision.bounds
    val velocity = collision.velocity

    // Calculate the target position based on velocity.
    val targetPosition = bounds.getCenter(vec3d()).add(velocity.cpy().scl(deltaTime))
    val targetBounds = BoundingBoxD(
      targetPosition.cpy().sub(bounds.getDimensions(vec3d()).scl(0.5f)),
      targetPosition.cpy().add(bounds.getDimensions(vec3d()).scl(0.5f))
    )

    // Clear the list of potential block collisions.
    blockBoundingBoxes.clear()

    // Find all blocks that intersect with the entity's target position.
    val min = targetBounds.min
    val max = targetBounds.max
    for (x in min.x.toInt()..max.x.toInt()) {
      for (y in min.y.toInt()..max.y.toInt()) {
        for (z in min.z.toInt()..max.z.toInt()) {
          if (dimension[x, y, z].isSolid) {
            blockBoundingBoxes.addAll(dimension[x, y, z].boundsAt(vec3d(x, y, z)))
          }
        }
      }
    }

    // Resolve collisions for each axis.
    resolveCollisions(bounds, velocity, blockBoundingBoxes, deltaTime)

    // Update the collision component state.
    collision.grounded = velocity.y == 0f
  }

  private fun resolveCollisions(
    bounds: BoundingBoxD,
    velocity: Vector3,
    blockBoundingBoxes: GdxArray<BoundingBoxD>,
    deltaTime: Float,
  ) {
    // Separate checks for each axis to prevent tunneling issues.
    for (axis in 0..2) {
      resolveCollisionAxis(velocity, axis, bounds, deltaTime, blockBoundingBoxes)
    }
  }

  private fun resolveCollisionAxis(
    velocity: Vector3,
    axis: Int,
    bounds: BoundingBoxD,
    deltaTime: Float,
    blockBoundingBoxes: GdxArray<BoundingBoxD>,
  ) {
    val axisVelocity = velocity[axis].toDouble()
    if (axisVelocity == 0.0) return

    val newPosition = bounds.getCenter(vec3d()).cpy()
    newPosition[axis] += axisVelocity * deltaTime
    val axisBounds = BoundingBoxD(
      newPosition.cpy().sub(bounds.getDimensions(vec3d()).scl(0.5f)),
      newPosition.cpy().add(bounds.getDimensions(vec3d()).scl(0.5f))
    )

    for (block in blockBoundingBoxes.asIterable()) {
      if (axisBounds.intersects(block)) {
        // Resolve collision along this axis.
        val blockEdge = if (axisVelocity > 0) block.min[axis] else block.max[axis]
        bounds.min[axis] = blockEdge - (if (axisVelocity > 0) bounds.getDimensions(vec3d())[axis] else 0.0)
        bounds.max[axis] = bounds.min[axis] + bounds.getDimensions(vec3d())[axis]
        velocity[axis] = 0f
        break
      }
    }
  }
}

private operator fun Vector3.set(axis: Int, value: Float) {
  when (axis) {
    0 -> x = value
    1 -> y = value
    2 -> z = value
    else -> throw IllegalArgumentException("Invalid axis: $axis")
  }
}

private operator fun Vector3.get(axis: Int) = when (axis) {
  0 -> x
  1 -> y
  2 -> z
  else -> throw IllegalArgumentException("Invalid axis: $axis")
}
