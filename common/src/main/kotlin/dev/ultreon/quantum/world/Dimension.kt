package dev.ultreon.quantum.world

import com.badlogic.gdx.utils.Disposable
import dev.ultreon.quantum.blocks.Block

const val SIZE = 16

@JvmInline
value class BlockFlags(val value: Int) {
  fun has(flag: Int): Boolean = value and flag == flag

  operator fun plus(flag: Int): BlockFlags = BlockFlags(value or flag)
  operator fun plus(flag: BlockFlags): BlockFlags = BlockFlags(value or flag.value)

  companion object {
    val NONE = BlockFlags(0)
    val REPLACE = BlockFlags(1)
    val UPDATE = BlockFlags(2)
    val SYNC = BlockFlags(4)
  }
}

abstract class Dimension : Disposable {
  abstract operator fun get(x: Int, y: Int, z: Int): Block
  operator fun set(x: Int, y: Int, z: Int, block: Block) =
    set(x, y, z, block, BlockFlags.SYNC + BlockFlags.UPDATE)
  abstract fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags)

  override fun dispose() = Unit
}
