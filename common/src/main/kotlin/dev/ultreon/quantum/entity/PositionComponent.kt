package dev.ultreon.quantum.entity

import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.world.SIZE
import dev.ultreon.quantum.math.Vector3D
import kotlin.math.cos
import kotlin.math.sin

class PositionComponent(val position: Vector3D = Vector3D(), var xRot: Float = 0F, var xHeadRot: Float = 0F, var yRot: Float = 0F) : Component<PositionComponent>() {
  override val componentType = ComponentType.position
  override fun json(): JsonValue {
    return JsonValue(JsonValue.ValueType.`object`).also {
      it.addChild("x", JsonValue(position.x))
      it.addChild("y", JsonValue(position.y))
      it.addChild("z", JsonValue(position.z))
      it.addChild("xRot", JsonValue(xRot.toDouble()))
      it.addChild("xHeadRot", JsonValue(xHeadRot.toDouble()))
      it.addChild("yRot", JsonValue(yRot.toDouble()))
    }
  }

  override fun load(json: JsonValue) {
    position.x = json["x"]?.asDouble() ?: 0.0
    position.y = json["y"]?.asDouble() ?: 0.0
    position.z = json["z"]?.asDouble() ?: 0.0
    xRot = json["xRot"]?.asFloat() ?: 0F
    xHeadRot = json["xHeadRot"]?.asFloat() ?: 0F
    yRot = json["yRot"]?.asFloat() ?: 0F
  }

  fun lookVec(direction: Vector3) {
      direction.x = (-cos(Math.toRadians(xRot.toDouble())) * cos(Math.toRadians(yRot.toDouble())).toFloat()).toFloat()
      direction.y = -sin(Math.toRadians(yRot.toDouble())).toFloat()
      direction.z = (sin(Math.toRadians(xRot.toDouble())) * cos(Math.toRadians(yRot.toDouble())).toFloat()).toFloat()
  }

  val chunkPosition: GridPoint3
    get() = GridPoint3(position.x.toInt().floorDiv(SIZE), position.y.toInt().floorDiv(SIZE), position.z.toInt().floorDiv(SIZE))
}
