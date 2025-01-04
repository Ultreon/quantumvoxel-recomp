package dev.ultreon.quantum.util

import com.badlogic.gdx.math.GridPoint3
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.BoundingBoxD
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.vec3d
import dev.ultreon.quantum.world.Chunk
import dev.ultreon.quantum.world.Dimension
import dev.ultreon.quantum.world.SIZE
import kotlin.math.abs
import kotlin.math.roundToInt

object WorldRayCaster {
  private val abs: GridPoint3 = GridPoint3()
  private val origin: GridPoint3 = GridPoint3()
  private val loc: GridPoint3 = GridPoint3()
  private val dir: Vector3D = vec3d()
  private val ext: Vector3D = vec3d()
  private val intersection: Vector3D = vec3d()
  private val local: Vector3D = vec3d()
  private val box: BoundingBoxD = BoundingBoxD()

  fun rayCast(map: Dimension): BlockHit {
    return rayCast(BlockHit(), map)
  }

  // sources : https://www.researchgate.net/publication/2611491_A_Fast_Voxel_Traversal_Algorithm_for_Ray_Tracing
  // and https://www.gamedev.net/blogs/entry/2265248-voxel-traversal-algorithm-ray-casting/
  fun rayCast(result: BlockHit, world: Dimension): BlockHit {
    return rayCast(result, world) { !it.isFluid }
  }

  // sources : https://www.researchgate.net/publication/2611491_A_Fast_Voxel_Traversal_Algorithm_for_Ray_Tracing
  // and https://www.gamedev.net/blogs/entry/2265248-voxel-traversal-algorithm-ray-casting/
  fun rayCast(result: BlockHit, world: Dimension, predicate: (Block) -> Boolean): BlockHit {
    result.isCollide = false

    val rayD: RayD = result.ray ?: throw IllegalArgumentException("ray is null")

    dir.set(
      if (rayD.direction.x > 0) 1 else -1,
      if (rayD.direction.y > 0) 1 else -1,
      if (rayD.direction.z > 0) 1 else -1
    )
    ext.set(
      if (rayD.direction.x > 0) 1 else 0,
      if (rayD.direction.y > 0) 1 else 0,
      if (rayD.direction.z > 0) 1 else 0
    )

    var chunk: Chunk? = null

    origin.set(
      kotlin.math.floor(rayD.origin.x).toInt(),
      kotlin.math.floor(rayD.origin.y).toInt(),
      kotlin.math.floor(rayD.origin.z).toInt()
    )
    abs.set(origin)

    val nextX: Double = abs.x + ext.x
    val nextY: Double = abs.y + ext.y
    val nextZ: Double = abs.z + ext.z

    var tMaxX = if (rayD.direction.x == 0.0) Double.MAX_VALUE else (nextX - rayD.origin.x) / rayD.direction.x
    var tMaxY = if (rayD.direction.y == 0.0) Double.MAX_VALUE else (nextY - rayD.origin.y) / rayD.direction.y
    var tMaxZ = if (rayD.direction.z == 0.0) Double.MAX_VALUE else (nextZ - rayD.origin.z) / rayD.direction.z


    val tDeltaX = if (rayD.direction.x == 0.0) Double.MAX_VALUE else dir.x / rayD.direction.x
    val tDeltaY = if (rayD.direction.y == 0.0) Double.MAX_VALUE else dir.y / rayD.direction.y
    val tDeltaZ = if (rayD.direction.z == 0.0) Double.MAX_VALUE else dir.z / rayD.direction.z

    while (true) {
      if (abs.dst(origin) > result.distanceMax) return result

      if (chunk == null || chunk.isDisposed()) {
        chunk = world.chunkAtBlock(abs.x, abs.y, abs.z)
        if (chunk == null || chunk.isDisposed()) return result
      }

      loc.set(abs).sub(chunk.offset.x.toInt(), chunk.offset.y.toInt(), chunk.offset.z.toInt())

      if (loc.x < 0 || loc.y < 0 || loc.z < 0 || loc.x >= SIZE || loc.y >= SIZE || loc.z >= SIZE) {
        chunk = null
        continue
      }

      val blockState: Block = chunk[loc.x, loc.y, loc.z]
      if (!blockState.isAir && predicate(blockState)) {
        val block: Block = blockState
        box.set(block.boundsAt(abs.x, abs.y, abs.z)[0])
        box.update()

        doIntersect(result, rayD, blockState, abs)

        return result
      }

      // increment
      if (tMaxX < tMaxY) {
        if (tMaxX < tMaxZ) {
          tMaxX += tDeltaX
          abs.x += dir.x.toInt()
        } else {
          tMaxZ += tDeltaZ
          abs.z += dir.z.toInt()
        }
      } else if (tMaxY < tMaxZ) {
        tMaxY += tDeltaY
        abs.y += dir.y.toInt()
      } else {
        tMaxZ += tDeltaZ
        abs.z += dir.z.toInt()
      }
    }
  }

  private fun doIntersect(result: BlockHit, rayD: RayD, block: Block, pos: GridPoint3 = GridPoint3()) {
    if (Intersector.intersectRayBounds(rayD, box, intersection)) {
      val dst: Double = intersection.dst(rayD.origin)
      result.isCollide = true
      result.distance = dst.toFloat()
      result.position.set(intersection)
      result.getVec().set(abs)
      result.blockMeta = block
      result.block = block

      computeFace(result)

      result.point.set(pos)
    } else {
      result.isCollide = false
      result.distance = Float.MAX_VALUE
      result.position.set(rayD.origin)
      result.getVec().set(abs)
      result.blockMeta = block
      result.block = block
    }
  }


  private fun computeFace(result: BlockHit) {
    // compute face
    local.set(result.position)
      .sub(result.getVec().x, result.getVec().y, result.getVec().z)
      .sub(.5f)

    val absX: Double = abs(local.x)
    val absY: Double = abs(local.y)
    val absZ: Double = abs(local.z)

    if (absY > absX) {
      if (absZ > absY) {
        // face Z+
        result.normal.set(0, 0, if (local.z < 0) -1 else 1)
      } else {
        result.normal.set(0, if (local.y < 0) -1 else 1, 0)
      }
    } else {
      if (absZ > absX) {
        result.normal.set(0, 0, if (local.z < 0) -1 else 1)
      } else {
        result.normal.set(if (local.x < 0) -1 else 1, 0, 0)
      }
    }
  }
}

private fun GridPoint3.set(intersection: Vector3D) {
  this.x = intersection.x.toInt()
  this.y = intersection.y.toInt()
  this.z = intersection.z.toInt()
}
