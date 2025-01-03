/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.ultreon.quantum.util

import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.vec3d
import java.io.Serial
import java.io.Serializable

/** Encapsulates a ray having a starting position and a unit length direction.
 *
 * @author badlogicgames@gmail.com
 */
class RayD : Serializable {
  val origin: Vector3D = vec3d()
  val direction: Vector3D = vec3d()

  constructor()

  /** Constructor, sets the starting position of the ray and the direction.
   *
   * @param origin The starting position
   * @param direction The direction
   */
  constructor(origin: Vector3D, direction: Vector3D) {
    this.origin.set(origin)
    this.direction.set(direction).nor()
  }

  // TODO: Networking
//  constructor(buffer: PacketIO) {
//    origin.set(buffer.readVec3d())
//    direction.set(buffer.readVec3d()).nor()
//  }

//  fun write(buffer: PacketIO) {
//    buffer.writeVec3d(this.origin)
//    buffer.writeVec3d(this.direction)
//  }

  /** @return a copy of this ray.
   */
  fun cpy(): RayD {
    return RayD(this.origin, this.direction)
  }

  /** Returns the endpoint given the distance. This is calculated as startpoint + distance * direction.
   * @param out The vector to set to the result
   * @param distance The distance from the end point to the start point.
   * @return The out param
   */
  fun getEndPoint(out: Vector3D, distance: Float): Vector3D {
    return out.set(this.direction).scl(distance).add(this.origin)
  }

  /** {@inheritDoc}  */
  override fun toString(): String {
    return "ray [" + this.origin + ":" + this.direction + "]"
  }

  /** Sets the starting position and the direction of this ray.
   *
   * @param origin The starting position
   * @param direction The direction
   * @return this ray for chaining
   */
  fun set(origin: Vector3D, direction: Vector3D): RayD {
    this.origin.set(origin)
    this.direction.set(direction).nor()
    return this
  }

  /** Sets this ray from the given starting position and direction.
   *
   * @param x The x-component of the starting position
   * @param y The y-component of the starting position
   * @param z The z-component of the starting position
   * @param dx The x-component of the direction
   * @param dy The y-component of the direction
   * @param dz The z-component of the direction
   * @return this ray for chaining
   */
  fun set(x: Float, y: Float, z: Float, dx: Float, dy: Float, dz: Float): RayD {
    origin.set(x.toDouble(), y.toDouble(), z.toDouble())
    direction.set(dx.toDouble(), dy.toDouble(), dz.toDouble()).nor()
    return this
  }

  /** Sets the starting position and direction from the given ray
   *
   * @param rayD The ray
   * @return This ray for chaining
   */
  fun set(rayD: RayD): RayD {
    origin.set(rayD.origin)
    direction.set(rayD.direction).nor()
    return this
  }

  override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other == null || other.javaClass != this.javaClass) return false
    val r = other as RayD
    return direction == r.direction && origin == r.origin
  }

  override fun hashCode(): Int {
    val prime = 73
    var result = 1
    result = prime * result + direction.hashCode()
    result = prime * result + origin.hashCode()
    return result
  }

  fun getDirection(): Direction {
    return Direction.fromVec3d(this.direction)
  }

  companion object {
    private val serialVersionUID = -620692054835390878L
    var tmp: Vector3D = vec3d()
  }
}
