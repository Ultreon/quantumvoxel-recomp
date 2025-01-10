package dev.ultreon.quantum.server

import dev.ultreon.quantum.world.SIZE

@JvmInline
value class Index3(val index: Int) {
  constructor(x: Int, y: Int, z: Int) : this((x * SIZE + y) * SIZE + z)

  val x get() = index / (SIZE * SIZE) % SIZE
  val y get() = index % (SIZE * SIZE) / SIZE
  val z get() = index % SIZE

  override fun toString(): String {
    return "[$x, $y, $z]"
  }
}

@JvmInline
value class Index2(val index: Int) {
  constructor(x: Int, y: Int) : this(x * SIZE + y)

  val x get() = index / SIZE % SIZE
  val y get() = index % SIZE

  override fun toString(): String {
    return "[$x, $y]"
  }
}
