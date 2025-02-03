package dev.ultreon.quantum.client.gui.screens

import com.badlogic.gdx.Gdx
import dev.ultreon.quantum.client.*
import dev.ultreon.quantum.client.gui.draw
import dev.ultreon.quantum.util.id
import ktx.app.KtxScreen

class SplashScreen : KtxScreen {
  private val batch = globalBatch
  private val duration = 2f
  private var time = 0f

  override fun render(delta: Float) {
    batch.begin()
    batch.draw(id(path = "textures/gui/logo.png"), Gdx.graphics.width / 2f - 32f, Gdx.graphics.height / 2f - 32f, 64f, 64f)
    batch.end()

    time += delta
    if (time > duration) {
      time = 0f
      backgroundRenderer = BackgroundRenderer()
      quantum.showScreen(LoadScreen())
    }
  }

  override fun show() {
    batch.projectionMatrix.setToOrtho2D(0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
  }

  override fun resize(width: Int, height: Int) {
    batch.projectionMatrix.setToOrtho2D(0f, 0f, width.toFloat(), height.toFloat())
  }

  override fun dispose() {
    batch.dispose()
  }
}
