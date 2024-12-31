package dev.ultreon.quantum.client.world

import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.world.BlockFlags
import dev.ultreon.quantum.world.World

class ClientWorld : World() {
  val blocks = Array(16) { Array(16) { Array(16) { Blocks.air } } }

  override fun get(x: Int, y: Int, z: Int): Block {
    return blocks[x][y][z]
  }

  override fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags) {
    blocks[x][y][z] = block
  }
}
