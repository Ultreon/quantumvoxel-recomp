package dev.ultreon.quantum.util

import dev.ultreon.quantum.math.BoundingBoxD

object BoundingBoxUtils {
  fun offset(boundingBox: BoundingBoxD, dx: Double, dy: Double, dz: Double): BoundingBoxD {
    val b = BoundingBoxD(boundingBox)
    b.min.add(dx, dy, dz)
    b.max.add(dx, dy, dz)
    b.update()
    return b
  }

  fun clipXCollide(a: BoundingBoxD, c: BoundingBoxD, xa: Double): Double {
    var xa = xa
    var max: Double = 0.0
    if (c.max.y <= a.min.y || c.min.y >= a.max.y) {
      return xa
    }
    if (c.max.z <= a.min.z || c.min.z >= a.max.z) {
      return xa
    }
    if (xa > 0.0f && c.max.x <= a.min.x && ((a.min.x - c.max.x - 0.0f).also { max = it }) < xa) {
      xa = max
    }
    if (xa < 0.0f && c.min.x >= a.max.x && ((a.max.x - c.min.x + 0.0f).also { max = it }) > xa) {
      xa = max
    }
    return xa
  }

  fun clipZCollide(a: BoundingBoxD, c: BoundingBoxD, za: Double): Double {
    var za = za
    var max: Double = 0.0
    if (c.max.x <= a.min.x || c.min.x >= a.max.x) {
      return za
    }
    if (c.max.y <= a.min.y || c.min.y >= a.max.y) {
      return za
    }
    if (za > 0.0f && c.max.z <= a.min.z && ((a.min.z - c.max.z - 0.0f).also { max = it }) < za) {
      za = max
    }
    if (za < 0.0f && c.min.z >= a.max.z && ((a.max.z - c.min.z + 0.0f).also { max = it }) > za) {
      za = max
    }
    return za
  }

  fun clipYCollide(a: BoundingBoxD, c: BoundingBoxD, ya: Double): Double {
    var ya = ya
    var max = 0.0
    if (c.max.x <= a.min.x || c.min.x >= a.max.x) {
      return ya
    }
    if (c.max.z <= a.min.z || c.min.z >= a.max.z) {
      return ya
    }
    if (ya > 0.0f && c.max.y <= a.min.y && ((a.min.y - c.max.y - 0.0f).also { max = it }) < ya) {
      ya = max
    }
    if (ya < 0.0f && c.min.y >= a.max.y && ((a.max.y - c.min.y + 0.0f).also { max = it }) > ya) {
      ya = max
    }
    return ya
  }
}
