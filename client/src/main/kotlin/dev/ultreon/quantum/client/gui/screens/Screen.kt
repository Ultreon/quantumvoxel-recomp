package dev.ultreon.quantum.client.gui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Stage
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.gui.GuiRenderer
import dev.ultreon.quantum.client.gui.widget.GuiContainer
import dev.ultreon.quantum.client.guiScale
import dev.ultreon.quantum.client.quantum
import dev.ultreon.quantum.client.shapes
import ktx.app.KtxScreen
import ktx.graphics.color

private val background = color(0f, 0f, 0f, 0.5f)

abstract class Screen(parent: Screen? = null) : KtxScreen, GuiContainer(parent) {
  var title: String = ""

  final override fun render(delta: Float) {
    id = "screen-${this::class.simpleName}"

    if (quantum.environmentRenderer != null) {
      if (this !is PlaceholderScreen) {
        PlaceholderScreen.render(delta)
        quantum.shapes
      }
    }

    if (!quantum.isTouch) {
      quantum.submit {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
          if (quantum.environmentRenderer != null) {
            quantum.showScreen(PlaceholderScreen)
            Gdx.input.setCursorPosition(Gdx.graphics.width / 2, Gdx.graphics.height / 2)
            Gdx.input.isCursorCatched = true
          }
        }
      }
    }

    if (this !is PlaceholderScreen) {
      shapes.filledRectangle(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), background)
    }

    super<KtxScreen>.render(delta)

    this.preRender(this)
    this.render(
      renderer = QuantumVoxel.instance.guiRenderer,
      x = (Gdx.input.x / guiScale).toInt(),
      y = (Gdx.input.y / guiScale).toInt(),
      delta
    )
  }

  override fun render(renderer: GuiRenderer, x: Int, y: Int, delta: Float) {
    super<GuiContainer>.render(renderer, x, y, delta)
  }

  final override fun resize(width: Int, height: Int) {
    super.resize(width, height)
    super.width = width.toFloat()
    super.height = height.toFloat()

    this.resized()
  }

  open fun resized() {

  }

  override fun show() {
    super.show()

    this.width = Gdx.graphics.width.toFloat() / guiScale
    this.height = Gdx.graphics.height.toFloat() / guiScale

    setup()
  }

  open fun Stage.init() = Unit

  abstract fun setup()

  override fun dispose() {
    super<KtxScreen>.dispose()
    super<GuiContainer>.dispose()
  }

  override fun hide() {

  }
}
