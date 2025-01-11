package dev.ultreon.quantum.blocks

import com.badlogic.gdx.math.collision.BoundingBox
import dev.ultreon.quantum.id
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.vec3d
import ktx.collections.GdxArray
import ktx.collections.gdxArrayOf
import ktx.collections.toGdxArray
import ktx.math.vec3

class Block {
  val isAir: Boolean
    get() = this == Blocks.air
  var isFluid: Boolean = false
  var renderType: String = "default"
  var hasCollider: Boolean = true
  val bounds: GdxArray<BoundingBox> = gdxArrayOf(
    BoundingBox(
      vec3(0f, 0f, 0f),
      vec3(1f, 1f, 1f)
    )
  )

  val isSolid: Boolean = true

  override fun toString(): String {
    return "Block($id)"
  }

  fun boundsAt(vec3d: Vector3D): GdxArray<BoundingBoxD> {
    return bounds.map {
      BoundingBoxD(
        Vector3D(it.min.x + vec3d.x, it.min.y + vec3d.y, it.min.z + vec3d.z),
        Vector3D(it.max.x + vec3d.x, it.max.y + vec3d.y, it.max.z + vec3d.z)
      )
    }.toGdxArray()
  }

  fun boundsAt(x: Double, y: Double, z: Double): GdxArray<BoundingBoxD> {
    return boundsAt(vec3d(x, y, z))
  }

  fun boundsAt(x: Int, y: Int, z: Int): GdxArray<BoundingBoxD> {
    return boundsAt(vec3d(x.toDouble(), y.toDouble(), z.toDouble()))
  }
}

fun block(func: Block.() -> Unit): Block {
  val block = Block()
  block.func()
  return block
}
