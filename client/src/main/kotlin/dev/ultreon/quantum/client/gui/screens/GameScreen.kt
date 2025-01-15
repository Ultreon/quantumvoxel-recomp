package dev.ultreon.quantum.client.gui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Stage
import dev.ultreon.quantum.client.*
import dev.ultreon.quantum.client.gui.widget.Background
import ktx.app.KtxScreen
import ktx.graphics.color
import ktx.graphics.use

private val background = color(0f, 0f, 0f, 0.5f)

abstract class GameScreen : KtxScreen {
  val stage: Stage = Stage(guiViewport).apply {
    isDebugAll = true
  }

  var screenWidth: Int = (stage.viewport.worldWidth.toInt() / guiScale).toInt()
    private set
  var screenHeight: Int = (stage.viewport.worldHeight.toInt() / guiScale).toInt()
    private set

  override fun render(delta: Float) {
    if (quantum.environmentRenderer != null) {
      if (this !is PlaceholderScreen) {
        quantum.getScreen<PlaceholderScreen>().render(delta)
        quantum.shapes
      }
    }

    if (!quantum.isTouch) {
      quantum.submit {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
          if (quantum.environmentRenderer != null) {
            quantum.setScreen<PlaceholderScreen>()
            Gdx.input.setCursorPosition(Gdx.graphics.width / 2, Gdx.graphics.height / 2)
            Gdx.input.isCursorCatched = true
          }
        }
      }
    }

    super.render(delta)

    if (this !is PlaceholderScreen) {
      globalBatch.use {
        shapes.filledRectangle(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), background)
      }
    }

    stage.act(delta)
    stage.draw()
  }

  override fun resize(width: Int, height: Int) {
    screenWidth = (stage.viewport.worldWidth.toInt() / guiScale).toInt()
    screenHeight = (stage.viewport.worldHeight.toInt() / guiScale).toInt()

    stage.viewport.update(width, height, false)
    stage.root.setPosition(0f, 0f)

    stage.clear()
    this.show()
  }

  override fun show() {
    stage.clear()
    stage.addActor(Background())
    stage.init()
    Gdx.input.inputProcessor = stage
  }

  open fun Stage.init() = Unit

  override fun hide() {

  }
}
