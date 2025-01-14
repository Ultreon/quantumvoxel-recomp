package dev.ultreon.quantum.client.input

import ktx.math.vec2

class KeyMovement : PlayerMovement {
  var forward = false
  var backward = false
  var strafeLeft = false
  var strafeRight = false
  var up = false
  var down = false

  override var motionX = 0f
  override var motionZ = 0f

  override var movement = vec2()

  override fun update() {
    forward = KeyBinds.walkForwardsKey.isJustPressed()
    backward = KeyBinds.walkBackwardsKey.isJustPressed()
    strafeLeft = KeyBinds.walkLeftKey.isJustPressed()
    strafeRight = KeyBinds.walkRightKey.isJustPressed()
    up = KeyBinds.jumpKey.isJustPressed()
    down = KeyBinds.crouchKey.isJustPressed()

    motionX = 0f
    motionZ = 0f
    if (forward) motionZ -= 1f
    if (backward) motionZ += 1f
    if (strafeLeft) motionX -= 1f
    if (strafeRight) motionX += 1f

    movement.set(motionX, motionZ).nor()
  }

  override fun reset() {
    forward = false
    backward = false
    strafeLeft = false
    strafeRight = false
    up = false
    down = false

    motionX = 0f
    motionZ = 0f
  }
}
