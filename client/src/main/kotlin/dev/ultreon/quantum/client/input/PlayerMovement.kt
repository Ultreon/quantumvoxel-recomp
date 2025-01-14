package dev.ultreon.quantum.client.input

import com.badlogic.gdx.math.Vector2

interface PlayerMovement {
  var movement: Vector2
  var motionX: Float
  var motionZ: Float

  fun update()
  fun reset()
}
