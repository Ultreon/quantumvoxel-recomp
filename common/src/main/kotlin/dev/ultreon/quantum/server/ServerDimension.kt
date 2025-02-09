package dev.ultreon.quantum.server

import com.badlogic.gdx.math.GridPoint3
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.scripting.PersistentData
import dev.ultreon.quantum.world.BlockFlags
import dev.ultreon.quantum.world.Chunk
import dev.ultreon.quantum.world.Dimension

class ServerDimension : Dimension() {
  val chunks = mutableMapOf<GridPoint3, ServerChunk>()

  override fun get(x: Int, y: Int, z: Int): Block {
    TODO("Not yet implemented")
  }

  override fun set(x: Int, y: Int, z: Int, block: Block, flags: BlockFlags) {
    TODO("Not yet implemented")
  }

  override fun chunkAt(x: Int, y: Int, z: Int): Chunk? {
    TODO("Not yet implemented")
  }

  override val persistentData: PersistentData = PersistentData()

}
