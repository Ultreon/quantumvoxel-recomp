package dev.ultreon.quantum.entity

import com.artemis.Component
import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.math.Vector3
import dev.ultreon.quantum.world.SIZE
import dev.ultreon.quantum.math.Vector3D
import kotlin.math.cos
import kotlin.math.sin

class PositionComponent(val position: Vector3D = Vector3D(), var xRot: Float = 0F, var xHeadRot: Float = 0F, var yRot: Float = 0F) : Component() {
  fun lookVec(direction: Vector3) {
      direction.x = (-cos(Math.toRadians(xRot.toDouble())) * cos(Math.toRadians(yRot.toDouble())).toFloat()).toFloat()
      direction.y = -sin(Math.toRadians(yRot.toDouble())).toFloat()
      direction.z = (sin(Math.toRadians(xRot.toDouble())) * cos(Math.toRadians(yRot.toDouble())).toFloat()).toFloat()
  }

  val chunkPosition: GridPoint3
    get() = GridPoint3(position.x.toInt().floorDiv(SIZE), position.y.toInt().floorDiv(SIZE), position.z.toInt().floorDiv(SIZE))
}
