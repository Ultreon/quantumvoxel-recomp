package dev.ultreon.quantum.client.gui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Stage
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.QuantumVoxel.globalBatch
import dev.ultreon.quantum.client.QuantumVoxel.guiScale
import dev.ultreon.quantum.client.QuantumVoxel.shapes
import dev.ultreon.quantum.client.gui.widget.Background
import ktx.app.KtxScreen
import ktx.graphics.color

private val background = color(0f, 0f, 0f, 0.5f)

abstract class GameScreen : KtxScreen {
  open val stage: Stage = Stage(QuantumVoxel.guiViewport)

  var screenWidth: Int = (stage.viewport.worldWidth.toInt() / guiScale).toInt()
    private set
  var screenHeight: Int = (stage.viewport.worldHeight.toInt() / guiScale).toInt()
    private set

  override fun render(delta: Float) {
    if (QuantumVoxel.environmentRenderer != null) {
      if (this !is InGameHudScreen) {
        QuantumVoxel.getScreen<InGameHudScreen>().render(delta)
        QuantumVoxel.shapes
      }
    }

    if (!QuantumVoxel.isTouch) {
      QuantumVoxel.nextFrame {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
          if (QuantumVoxel.environmentRenderer != null) {
            QuantumVoxel.setScreen<InGameHudScreen>()
            Gdx.input.setCursorPosition(Gdx.graphics.width / 2, Gdx.graphics.height / 2)
            Gdx.input.isCursorCatched = true
          }
        }
      }
    }

    super.render(delta)

    if (this !is InGameHudScreen) {
      globalBatch.begin()
      shapes.filledRectangle(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), background)
      globalBatch.end()
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
    Gdx.input.inputProcessor = stage

    stage.clear()
    stage.addActor(Background())
    stage.init()
  }

  open fun Stage.init() = Unit

  override fun hide() {
    Gdx.input.inputProcessor = null
  }
}
