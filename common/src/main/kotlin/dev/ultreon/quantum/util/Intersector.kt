package dev.ultreon.quantum.util

import dev.ultreon.quantum.blocks.BoundingBoxD
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.vec3d

object Intersector {
  private val tmp: Vector3D = vec3d()

  fun intersectRayBounds(rayD: RayD, box: BoundingBoxD, intersection: Vector3D?): Boolean {
    if (box.contains(rayD.origin)) {
      intersection?.set(rayD.origin)
      return true
    }
    var lowest = 0.0
    var t: Double
    var hit = false

    // min x
    if (rayD.origin.x <= box.min.x && rayD.direction.x > 0) {
      t = (box.min.x - rayD.origin.x) / rayD.direction.x
      if (t >= 0) {
        tmp.set(rayD.direction).scl(t).add(rayD.origin)
        if (tmp.y >= box.min.y && tmp.y <= box.max.y && tmp.z >= box.min.z && tmp.z <= box.max.z && (!hit || t < lowest)) {
          hit = true
          lowest = t
        }
      }
    }
    // max x
    if (rayD.origin.x >= box.max.x && rayD.direction.x < 0) {
      t = (box.max.x - rayD.origin.x) / rayD.direction.x
      if (t >= 0) {
        tmp.set(rayD.direction).scl(t).add(rayD.origin)
        if (tmp.y >= box.min.y && tmp.y <= box.max.y && tmp.z >= box.min.z && tmp.z <= box.max.z && (!hit || t < lowest)) {
          hit = true
          lowest = t
        }
      }
    }
    // min y
    if (rayD.origin.y <= box.min.y && rayD.direction.y > 0) {
      t = (box.min.y - rayD.origin.y) / rayD.direction.y
      if (t >= 0) {
        tmp.set(rayD.direction).scl(t).add(rayD.origin)
        if (tmp.x >= box.min.x && tmp.x <= box.max.x && tmp.z >= box.min.z && tmp.z <= box.max.z && (!hit || t < lowest)) {
          hit = true
          lowest = t
        }
      }
    }
    // max y
    if (rayD.origin.y >= box.max.y && rayD.direction.y < 0) {
      t = (box.max.y - rayD.origin.y) / rayD.direction.y
      if (t >= 0) {
        tmp.set(rayD.direction).scl(t).add(rayD.origin)
        if (tmp.x >= box.min.x && tmp.x <= box.max.x && tmp.z >= box.min.z && tmp.z <= box.max.z && (!hit || t < lowest)) {
          hit = true
          lowest = t
        }
      }
    }
    // min z
    if (rayD.origin.z <= box.min.z && rayD.direction.z > 0) {
      t = (box.min.z - rayD.origin.z) / rayD.direction.z
      if (t >= 0) {
        tmp.set(rayD.direction).scl(t).add(rayD.origin)
        if (tmp.x >= box.min.x && tmp.x <= box.max.x && tmp.y >= box.min.y && tmp.y <= box.max.y && (!hit || t < lowest)) {
          hit = true
          lowest = t
        }
      }
    }
    // max z
    if (rayD.origin.z >= box.max.z && rayD.direction.z < 0) {
      t = (box.max.z - rayD.origin.z) / rayD.direction.z
      if (t >= 0) {
        tmp.set(rayD.direction).scl(t).add(rayD.origin)
        if (tmp.x >= box.min.x && tmp.x <= box.max.x && tmp.y >= box.min.y && tmp.y <= box.max.y && (!hit || t < lowest)) {
          hit = true
          lowest = t
        }
      }
    }
    if (hit && intersection != null) {
      intersection.set(rayD.direction).scl(lowest).add(rayD.origin)
      if (intersection.x < box.min.x) {
        intersection.x = box.min.x
      } else if (intersection.x > box.max.x) {
        intersection.x = box.max.x
      }
      if (intersection.y < box.min.y) {
        intersection.y = box.min.y
      } else if (intersection.y > box.max.y) {
        intersection.y = box.max.y
      }
      if (intersection.z < box.min.z) {
        intersection.z = box.min.z
      } else if (intersection.z > box.max.z) {
        intersection.z = box.max.z
      }
    }
    return hit
  }
}
