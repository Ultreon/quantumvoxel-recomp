package dev.ultreon.quantum.math

import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import kotlin.math.sqrt

data class Vector3D(
  var x: Double = 0.0,
  var y: Double = 0.0,
  var z: Double = 0.0
) {
  fun set(x: Double, y: Double, z: Double): Vector3D {
    this.x = x
    this.y = y
    this.z = z
    return this
  }

  fun cpy() = Vector3D(x, y, z)

  fun add(x: Double, y: Double, z: Double): Vector3D {
    this.x += x
    this.y += y
    this.z += z
    return this
  }

  fun add(x: Float, y: Float, z: Float): Vector3D {
    this.x += x
    this.y += y
    this.z += z
    return this
  }

  fun add(v: Vector3D): Vector3D {
    this.x += v.x
    this.y += v.y
    this.z += v.z
    return this
  }

  fun add(v: Vector3): Vector3D {
    this.x += v.x
    this.y += v.y
    this.z += v.z
    return this
  }

  fun sub(x: Double, y: Double, z: Double): Vector3D {
    this.x -= x
    this.y -= y
    this.z -= z
    return this
  }

  fun sub(x: Float, y: Float, z: Float): Vector3D {
    this.x -= x
    this.y -= y
    this.z -= z
    return this
  }

  fun sub(v: Vector3D): Vector3D {
    this.x -= v.x
    this.y -= v.y
    this.z -= v.z
    return this
  }

  fun sub(v: Vector3): Vector3D {
    this.x -= v.x
    this.y -= v.y
    this.z -= v.z
    return this
  }

  fun scl(x: Double, y: Double, z: Double): Vector3D {
    this.x *= x
    this.y *= y
    this.z *= z
    return this
  }

  fun scl(x: Float, y: Float, z: Float): Vector3D {
    this.x *= x
    this.y *= y
    this.z *= z
    return this
  }

  fun scl(s: Double): Vector3D {
    this.x *= s
    this.y *= s
    this.z *= s
    return this
  }

  fun scl(s: Float): Vector3D {
    this.x *= s
    this.y *= s
    this.z *= s
    return this
  }

  fun scl(v: Vector3D): Vector3D {
    this.x *= v.x
    this.y *= v.y
    this.z *= v.z
    return this
  }

  fun scl(v: Vector3): Vector3D {
    this.x *= v.x
    this.y *= v.y
    this.z *= v.z
    return this
  }

  fun div(x: Double, y: Double, z: Double): Vector3D {
    this.x /= x
    this.y /= y
    this.z /= z
    return this
  }

  fun div(x: Float, y: Float, z: Float): Vector3D {
    this.x /= x
    this.y /= y
    this.z /= z
    return this
  }

  fun div(s: Double): Vector3D {
    this.x /= s
    this.y /= s
    this.z /= s
    return this
  }

  fun div(s: Float): Vector3D {
    this.x /= s
    this.y /= s
    this.z /= s
    return this
  }

  fun div(v: Vector3D): Vector3D {
    this.x /= v.x
    this.y /= v.y
    this.z /= v.z
    return this
  }

  fun div(v: Vector3): Vector3D {
    this.x /= v.x
    this.y /= v.y
    this.z /= v.z
    return this
  }

  fun len2() = x * x + y * y + z * z

  fun len() = sqrt(x * x + y * y + z * z)

  fun nor(): Vector3D {
    val len = len()
    this.x /= len
    this.y /= len
    this.z /= len
    return this
  }

  fun dot(v: Vector3D) = x * v.x + y * v.y + z * v.z

  fun cross(v: Vector3D, out: Vector3D): Vector3D {
    out.x = y * v.z - z * v.y
    out.y = z * v.x - x * v.z
    out.z = x * v.y - y * v.x
    return out
  }

  fun dst(v: Vector3D) = sqrt((v.x - x) * (v.x - x) + (v.y - y) * (v.y - y) + (v.z - z) * (v.z - z))

  fun dst2(v: Vector3D) = (v.x - x) * (v.x - x) + (v.y - y) * (v.y - y) + (v.z - z) * (v.z - z)

  fun clone() = Vector3D(x, y, z)

  override fun toString() = "($x, $y, $z)"
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Vector3D) return false

    if (x != other.x) return false
    if (y != other.y) return false
    if (z != other.z) return false

    return true
  }

  override fun hashCode(): Int {
    var result = x.hashCode()
    result = 31 * result + y.hashCode()
    result = 31 * result + z.hashCode()
    return result
  }

  companion object {
    fun zero() = Vector3D(0.0, 0.0, 0.0)
    fun one() = Vector3D(1.0, 1.0, 1.0)
    fun unitX() = Vector3D(1.0, 0.0, 0.0)
    fun unitY() = Vector3D(0.0, 1.0, 0.0)
    fun unitZ() = Vector3D(0.0, 0.0, 1.0)
  }
}

fun Vector3.asEulerAngles(out: Quaternion = Quaternion()): Quaternion = out.setEulerAngles(x, y, z)
