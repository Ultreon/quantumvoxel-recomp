package dev.ultreon.quantum.blocks

import com.badlogic.gdx.math.collision.BoundingBox
import dev.ultreon.quantum.id
import dev.ultreon.quantum.math.Vector3D
import ktx.collections.GdxArray
import ktx.collections.gdxArrayOf
import ktx.collections.toGdxArray
import ktx.math.vec3

class Block {
  var renderType: String = "default"
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
}

fun block(func: Block.() -> Unit): Block {
  val block = Block()
  block.func()
  return block
}
