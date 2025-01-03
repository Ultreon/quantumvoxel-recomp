package dev.ultreon.quantum.util

import com.badlogic.gdx.math.GridPoint3
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.vec3d
import java.util.*

open class BlockHit : Hit {
  var direction: Direction? = null

  // input
  var ray: RayD? = null
  var distanceMax = 5.0f
    protected set
  var position = vec3d()
  var normal = vec3d()
  var point = GridPoint3()
  var blockMeta = Blocks.air
  var block = Blocks.air
  var isCollide = false
  var distance = 0f

  constructor()

  constructor(ray: RayD) {
    this.ray = ray
    this.direction = ray.getDirection()
  }

  constructor(ray: RayD, distanceMax: Float) {
    this.ray = ray
    this.direction = ray.getDirection()
    this.distanceMax = distanceMax
    this.distance = distanceMax
  }

//  constructor(buffer: PacketIO) {
//    this.ray = RayD(buffer)
//    this.direction = ray.getDirection()
//    this.distanceMax = buffer.readFloat()
//    position.set(buffer.readVec3d())
//    normal.set(buffer.readVec3d())
//    vec.set(buffer.readVec3i())
//    this.blockMeta = buffer.readBlockState()
//    this.block = Registries.BLOCK.byId(buffer.readVarInt())
//    this.isCollide = buffer.readBoolean()
//    this.distance = buffer.readFloat()
//  }

  constructor(ray: RayD, blockVec: GridPoint3, block: Block) {
    this.ray = ray
    this.blockMeta = block
    this.block = block
    position.set(blockVec.x.toDouble(), blockVec.y.toDouble(), blockVec.z.toDouble())
    this.direction = ray.getDirection()
    this.distanceMax = 5.0f
    getVec().set(blockVec.x.toDouble(), blockVec.y.toDouble(), blockVec.z.toDouble())
    normal.set(0.0, 0.0, 0.0)
    this.isCollide = true
    this.distance = 0.0f
  }

//  fun write(buffer: PacketIO) {
//    ray.write(buffer)
//    buffer.writeFloat(this.distanceMax)
//    buffer.writeVec3d(this.position)
//    buffer.writeVec3d(this.normal)
//    buffer.writeVec3i(this.getVec())
//    buffer.writeBlockState(this.getBlockMeta())
//    buffer.writeVarInt(Registries.BLOCK.getRawId(this.getBlock()))
//    buffer.writeBoolean(this.isCollide)
//    buffer.writeFloat(this.distance)
//  }

  fun setInput(ray: RayD): BlockHit {
    this.ray = ray
    this.direction = ray.getDirection()
    return this
  }

  fun getVec(): Vector3D {
    return this.position
  }

  val blockVec: GridPoint3
    get() = this.point

  val next: GridPoint3
    get() = point.cpy()
      .add(normal.x.toInt(), normal.y.toInt(), normal.z.toInt())

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    val hitResult = other as BlockHit
    return distanceMax.compareTo(hitResult.distanceMax) == 0 && isCollide == hitResult.isCollide && distance.toDouble()
      .compareTo(hitResult.distance.toDouble()) == 0 && direction === hitResult.direction && ray == hitResult.ray && position == hitResult.position && normal == hitResult.normal && getVec() == hitResult.getVec() && blockMeta == hitResult.blockMeta && block == hitResult.block
  }

  override fun hashCode(): Int {
    return Objects.hash(
      direction, ray, distanceMax, position, normal, getVec(), blockMeta, block,
      isCollide,
      distance
    )
  }

  companion object {
    val MISS: BlockHit = BlockHit()
  }
}
