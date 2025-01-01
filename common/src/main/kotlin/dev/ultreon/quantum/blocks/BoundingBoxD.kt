package dev.ultreon.quantum.blocks

import com.badlogic.gdx.math.collision.BoundingBox
import com.badlogic.gdx.utils.Array
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.vec3d

class BoundingBoxD(val min: Vector3D, val max: Vector3D) {
  constructor() : this(vec3d(), vec3d())

  fun intersects(other: BoundingBoxD): Boolean {
    return min.x <= other.max.x && min.y <= other.max.y && min.z <= other.max.z && max.x >= other.min.x && max.y >= other.min.y && max.z >= other.min.z
  }

  fun intersects(other: BoundingBox): Boolean {
    return min.x <= other.max.x && min.y <= other.max.y && min.z <= other.max.z && max.x >= other.min.x && max.y >= other.min.y && max.z >= other.min.z
  }

  fun getCenter(out: Vector3D): Vector3D {
    out.x = (min.x + max.x) / 2
    out.y = (min.y + max.y) / 2
    out.z = (min.z + max.z) / 2
    return out
  }

  fun getDimensions(out: Vector3D): Vector3D {
    out.x = max.x - min.x
    out.y = max.y - min.y
    out.z = max.z - min.z
    return out
  }

  fun getVertices(out: Array<Vector3D>): Array<Vector3D> {
    out.add(Vector3D(min.x, min.y, min.z))
    out.add(Vector3D(max.x, min.y, min.z))
    out.add(Vector3D(max.x, max.y, min.z))
    out.add(Vector3D(min.x, max.y, min.z))
    out.add(Vector3D(min.x, min.y, max.z))
    out.add(Vector3D(max.x, min.y, max.z))
    out.add(Vector3D(max.x, max.y, max.z))
    out.add(Vector3D(min.x, max.y, max.z))
    return out
  }

  override fun toString(): String {
    return "BoundingBoxD(min=$min, max=$max)"
  }

  companion object {
    fun bbTranslated(bb: BoundingBox, vector3D: Vector3D): BoundingBoxD {
      return BoundingBoxD(
        Vector3D(bb.min.x + vector3D.x, bb.min.y + vector3D.y, bb.min.z + vector3D.z),
        Vector3D(bb.max.x + vector3D.x, bb.max.y + vector3D.y, bb.max.z + vector3D.z)
      )
    }
  }
}
