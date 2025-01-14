package dev.ultreon.quantum.client.input

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import ktx.math.vec2

class TouchMovement(val touchpad: Touchpad) : PlayerMovement {
  var up = false
  var down = false

  override var motionX = 0f
  override var motionZ = 0f

  override var movement = vec2()

  override fun update() {
    motionX = touchpad.knobPercentX
    motionZ = touchpad.knobPercentY

    movement.set(motionX, motionZ).nor()
  }

  override fun reset() {
    motionX = 0f
    motionZ = 0f
    up = false
    down = false
  }
}
