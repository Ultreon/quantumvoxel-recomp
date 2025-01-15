package dev.ultreon.quantum.client.gui.widget

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor

class Background : Actor() {
  private val background = Pixmap(1, 1, Pixmap.Format.RGBA8888).let { pixmap ->
    pixmap.setColor(0f, 0f, 0f, 0.5f)
    pixmap.drawPixel(0, 0)

    Texture(pixmap).let { texture ->
      texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
      texture
    }
  }

  override fun draw(batch: Batch?, parentAlpha: Float) {
    batch?.color = batch?.color?.set(1f, 1f, 1f, parentAlpha)
    batch?.draw(background, x, y, width, height)
  }
}
