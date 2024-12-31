package dev.ultreon.quantum.math

import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3

enum class Axis {
  X,
  Y,
  Z
}

fun Axis.opposite() = when (this) {
  Axis.X -> Axis.Z
  Axis.Y -> Axis.Y
  Axis.Z -> Axis.X
}

fun Axis.toVector() = when (this) {
  Axis.X -> Vector3(1f, 0f, 0f)
  Axis.Y -> Vector3(0f, 1f, 0f)
  Axis.Z -> Vector3(0f, 0f, 1f)
}

fun Axis.toVector3(x: Float, y: Float, z: Float) = when (this) {
  Axis.X -> Vector3(x, 0f, 0f)
  Axis.Y -> Vector3(0f, y, 0f)
  Axis.Z -> Vector3(0f, 0f, z)
}

fun Axis.toQuaternion(angle: Float = 0f) = when (this) {
  Axis.X -> Quaternion(Vector3(1f, 0f, 0f), angle)
  Axis.Y -> Quaternion(Vector3(0f, 1f, 0f), angle)
  Axis.Z -> Quaternion(Vector3(0f, 0f, 1f), angle)
}
