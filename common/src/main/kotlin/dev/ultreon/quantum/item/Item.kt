package dev.ultreon.quantum.item

import com.badlogic.gdx.math.GridPoint3
import dev.ultreon.quantum.util.Direction
import dev.ultreon.quantum.world.Dimension

open class Item {
  open fun useOn(dimension: Dimension, pos: GridPoint3, face: Direction) {}
}
