package dev.ultreon.quantum.item

import com.badlogic.gdx.math.GridPoint3
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.util.Direction
import dev.ultreon.quantum.world.Dimension

class BlockItem(val block: () -> Block) : Item() {
  override fun useOn(dimension: Dimension, pos: GridPoint3, face: Direction) {
    dimension[
      (face.normal.x + pos.x).toInt(),
      (face.normal.y + pos.y).toInt(),
      (face.normal.z + pos.z).toInt()
    ] = block()
  }
}
