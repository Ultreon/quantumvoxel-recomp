package dev.ultreon.quantum.world

import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.math.Vector3D

abstract class Chunk {
  abstract val offset: Vector3D

  abstract operator fun get(x: Int, y: Int, z: Int): Block
  abstract operator fun set(x: Int, y: Int, z: Int, block: Block)

  abstract fun isDisposed(): Boolean
  abstract fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags)
  abstract fun dispose()
}
