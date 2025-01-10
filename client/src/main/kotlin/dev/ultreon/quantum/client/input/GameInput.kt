package dev.ultreon.quantum.client.input

import com.badlogic.gdx.math.Vector2

open class GameInput {
  private val mousePosition = Vector2()
  private val mouseDelta = Vector2()
  private val oldMousePosition = Vector2()
  private val tmp = Vector2()

  fun onMouseMove(x: Float, y: Float) {
    mousePosition.set(x, y)
  }

  fun update() {
    tmp.set(mousePosition)
    mouseDelta.set(tmp).sub(oldMousePosition)
    oldMousePosition.set(tmp)
  }

  val mouseX: Float get() = mousePosition.x
  val mouseY: Float get() = mousePosition.y

  val mouseDeltaX: Float get() = mouseDelta.x
  val mouseDeltaY: Float get() = mouseDelta.y

  val isMouseSupported: Boolean get() = false
}
