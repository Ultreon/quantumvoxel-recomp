package dev.ultreon.quantum.util

import com.badlogic.gdx.math.Vector3
import dev.ultreon.quantum.ExperimentalQuantumApi
import dev.ultreon.quantum.math.Axis
import dev.ultreon.quantum.math.opposite
import ktx.math.vec3

enum class Direction(val normal: Vector3) {
  UP(vec3(0f, 1f, 0f)),     // Top
  DOWN(vec3(0f, -1f, 0f)),  // Bottom
  NORTH(vec3(0f, 0f, -1f)), // Forward
  SOUTH(vec3(0f, 0f, 1f)),  // Backward
  WEST(vec3(-1f, 0f, 0f)),  // Left
  EAST(vec3(1f, 0f, 0f));   // Right

  fun opposite() = when (this) {
    UP -> DOWN
    DOWN -> UP
    NORTH -> SOUTH
    SOUTH -> NORTH
    WEST -> EAST
    EAST -> WEST
  }

  val axis: Axis
    get() = when (this) {
      UP -> Axis.Y
      DOWN -> Axis.Y
      NORTH -> Axis.Z
      SOUTH -> Axis.Z
      WEST -> Axis.X
      EAST -> Axis.X
    }

  fun clockwise(axis: Axis): Direction {
    return when (axis) {
      Axis.Y -> when (this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
        else -> this
      }

      Axis.Z -> when (this) {
        UP -> NORTH
        NORTH -> DOWN
        DOWN -> SOUTH
        SOUTH -> UP
        else -> this
      }

      Axis.X -> when (this) {
        UP -> EAST
        EAST -> UP
        DOWN -> WEST
        WEST -> DOWN
        else -> this
      }
    }
  }

  @ExperimentalQuantumApi
  fun counterClockwise(axis: Axis): Direction {
    return clockwise(axis.opposite())
  }
}
