package dev.ultreon.quantum

import com.artemis.Component
import com.badlogic.gdx.math.Vector3
import dev.ultreon.quantum.math.BoundingBoxD
import ktx.math.vec3

/** Component for entities with collision bounds. */
class CollisionComponent(
  val bounds: BoundingBoxD = BoundingBoxD(),
  val velocity: Vector3 = vec3(),
  var grounded: Boolean = false
) : Component()
