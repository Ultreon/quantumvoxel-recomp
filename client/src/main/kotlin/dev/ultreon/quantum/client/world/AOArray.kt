package dev.ultreon.quantum.client.world

import com.badlogic.gdx.math.GridPoint3
import dev.ultreon.quantum.math.Axis
import dev.ultreon.quantum.util.Direction

@JvmInline
value class AOArray(val value: IntArray) {
  val hasAO: Boolean get() = value.any { it != 0 }

  fun aoForSide(side: Direction): AO {
    return AO(value[side.ordinal])
  }

  operator fun get(index: Int): AO {
    return AO(value[index])
  }

  operator fun set(index: Int, ao: AO) {
    value[index] = ao.value
  }

  operator fun plus(other: AOArray): AOArray {
    return AOArray(value + other.value)
  }

  operator fun plus(ao: AO): AOArray {
    return AOArray(value + ao.value)
  }

  fun copy(): AOArray {
    return AOArray(value.copyOf())
  }

  override fun toString(): String {
    return "[${value.joinToString(", ")}]"
  }

  @JvmInline
  value class AO(val value: Int) {
    val hasAO: Boolean get() = value != 0

    val hasAoCorner00: Boolean get() = (value and 1) != 0
    val hasAoCorner01: Boolean get() = (value and 2) != 0
    val hasAoCorner10: Boolean get() = (value and 4) != 0
    val hasAoCorner11: Boolean get() = (value and 8) != 0

    override fun toString(): String {
      return "AO([00]=$hasAoCorner00, [01]=$hasAoCorner01, [10]=$hasAoCorner10, [11]=$hasAoCorner11)"
    }

    fun flipped(): AO {
      return AO(
        hasAoCorner00 = hasAoCorner11,
        hasAoCorner01 = hasAoCorner10,
        hasAoCorner10 = hasAoCorner01,
        hasAoCorner11 = hasAoCorner00
      )
    }

    companion object {
      val AO_NONE: AO = AO(0)

      operator fun invoke(
        hasAoCorner00: Boolean,
        hasAoCorner01: Boolean,
        hasAoCorner10: Boolean,
        hasAoCorner11: Boolean,
      ): AO {
        return AO(
          (if (hasAoCorner00) 1 else 0) or
            (if (hasAoCorner01) 2 else 0) or
            (if (hasAoCorner10) 4 else 0) or
            (if (hasAoCorner11) 8 else 0)
        )
      }
    }
  }

  companion object {
    fun calculate(chunk: ClientChunk, x: Int, y: Int, z: Int): AOArray {
      if (!chunk.getSafe(x, y, z).ambientOcclusion) {
        return AOArray(intArrayOf(0, 0, 0, 0, 0, 0))
      }
      val array = IntArray(6)
      for (dir in Direction.entries) {
        val point = GridPoint3((x + dir.normal.x).toInt(), (y + dir.normal.y).toInt(), (z + dir.normal.z).toInt())
        val block = chunk.getSafe(point.x, point.y, point.z)
        if (!block.ambientOcclusion) {
          var ao: AO = when (dir.axis) {
            Axis.Y -> {
              val northWest = chunk.getSafe(point.x - 1, point.y, point.z - 1)
              val west = chunk.getSafe(point.x - 1, point.y, point.z)
              val north = chunk.getSafe(point.x, point.y, point.z - 1)

              val southWest = chunk.getSafe(point.x - 1, point.y, point.z + 1)
              val south = chunk.getSafe(point.x, point.y, point.z + 1)

              val southEast = chunk.getSafe(point.x + 1, point.y, point.z + 1)
              val east = chunk.getSafe(point.x + 1, point.y, point.z)

              val northEast = chunk.getSafe(point.x + 1, point.y, point.z - 1)

              if (dir.isNegative) {
                AO(
                  hasAoCorner00 = northWest.ambientOcclusion || west.ambientOcclusion || north.ambientOcclusion,
                  hasAoCorner01 = southWest.ambientOcclusion || west.ambientOcclusion || south.ambientOcclusion,
                  hasAoCorner10 = northEast.ambientOcclusion || east.ambientOcclusion || north.ambientOcclusion,
                  hasAoCorner11 = southEast.ambientOcclusion || east.ambientOcclusion || south.ambientOcclusion
                )
              } else {
                AO(
                  hasAoCorner00 = northEast.ambientOcclusion || east.ambientOcclusion || north.ambientOcclusion,
                  hasAoCorner01 = southEast.ambientOcclusion || east.ambientOcclusion || south.ambientOcclusion,
                  hasAoCorner10 = northWest.ambientOcclusion || west.ambientOcclusion || north.ambientOcclusion,
                  hasAoCorner11 = southWest.ambientOcclusion || west.ambientOcclusion || south.ambientOcclusion
                ).flipped()
              }
            }

            Axis.X -> {
              val northUp = chunk.getSafe(point.x, point.y + 1, point.z + 1)
              val up = chunk.getSafe(point.x, point.y + 1, point.z)
              val north = chunk.getSafe(point.x, point.y, point.z + 1)

              val southUp = chunk.getSafe(point.x, point.y + 1, point.z - 1)
              val south = chunk.getSafe(point.x, point.y, point.z - 1)

              val southDown = chunk.getSafe(point.x, point.y - 1, point.z - 1)
              val down = chunk.getSafe(point.x, point.y - 1, point.z)

              val northDown = chunk.getSafe(point.x, point.y - 1, point.z + 1)

              if (dir.isNegative) {
                AO(
                  hasAoCorner00 = southDown.ambientOcclusion || down.ambientOcclusion || south.ambientOcclusion,
                  hasAoCorner01 = southUp.ambientOcclusion || up.ambientOcclusion || south.ambientOcclusion,
                  hasAoCorner10 = northDown.ambientOcclusion || down.ambientOcclusion || north.ambientOcclusion,
                  hasAoCorner11 = northUp.ambientOcclusion || up.ambientOcclusion || north.ambientOcclusion
                )
              } else {
                AO(
                  hasAoCorner00 = northDown.ambientOcclusion || down.ambientOcclusion || north.ambientOcclusion,
                  hasAoCorner01 = northUp.ambientOcclusion || up.ambientOcclusion || north.ambientOcclusion,
                  hasAoCorner10 = southDown.ambientOcclusion || down.ambientOcclusion || south.ambientOcclusion,
                  hasAoCorner11 = southUp.ambientOcclusion || up.ambientOcclusion || south.ambientOcclusion
                )
              }
            }

            Axis.Z -> {
              val westUp = chunk.getSafe(point.x - 1, point.y + 1, point.z)
              val up = chunk.getSafe(point.x, point.y + 1, point.z)
              val west = chunk.getSafe(point.x - 1, point.y, point.z)

              val eastUp = chunk.getSafe(point.x + 1, point.y + 1, point.z)
              val east = chunk.getSafe(point.x + 1, point.y, point.z)

              val eastDown = chunk.getSafe(point.x + 1, point.y - 1, point.z)
              val down = chunk.getSafe(point.x, point.y - 1, point.z)

              val westDown = chunk.getSafe(point.x - 1, point.y - 1, point.z)

              if (dir.isNegative) {
                AO(
                  hasAoCorner10 = westDown.ambientOcclusion || down.ambientOcclusion || west.ambientOcclusion,
                  hasAoCorner11 = westUp.ambientOcclusion || up.ambientOcclusion || west.ambientOcclusion,
                  hasAoCorner00 = eastDown.ambientOcclusion || down.ambientOcclusion || east.ambientOcclusion,
                  hasAoCorner01 = eastUp.ambientOcclusion || up.ambientOcclusion || east.ambientOcclusion
                )
              } else {
                AO(
                  hasAoCorner00 = westDown.ambientOcclusion || down.ambientOcclusion || west.ambientOcclusion,
                  hasAoCorner01 = westUp.ambientOcclusion || up.ambientOcclusion || west.ambientOcclusion,
                  hasAoCorner10 = eastDown.ambientOcclusion || down.ambientOcclusion || east.ambientOcclusion,
                  hasAoCorner11 = eastUp.ambientOcclusion || up.ambientOcclusion || east.ambientOcclusion
                )
              }
            }

            else -> AO(
              hasAoCorner00 = false,
              hasAoCorner01 = false,
              hasAoCorner10 = false,
              hasAoCorner11 = false
            )
          }

          array[dir.ordinal] = ao.value
        }
      }

      return AOArray(array)
    }
  }
}
