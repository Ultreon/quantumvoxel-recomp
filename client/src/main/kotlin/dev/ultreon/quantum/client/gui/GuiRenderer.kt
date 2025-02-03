package dev.ultreon.quantum.client.gui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.github.tommyettinger.textra.Layout
import dev.ultreon.quantum.client.QuantumVoxel

class GuiRenderer(val batch: SpriteBatch) {
  val font = QuantumVoxel.instance.font

  private val tmpRegion = TextureRegion()
  private val layout = Layout()

  fun drawTexture(
    texture: TextureRegion,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    u: Float = 0F,
    v: Float = 0F,
    uWidth: Float = texture.regionWidth.toFloat(),
    uHeight: Float = texture.regionHeight.toFloat(),
    texWidth: Int = tmpRegion.regionWidth,
    texHeight: Int = tmpRegion.regionHeight,
  ) {
    tmpRegion.setRegion(texture)
    tmpRegion.u += (u / texWidth)
    tmpRegion.v += (v / texHeight)
    tmpRegion.u2 = tmpRegion.u + (uWidth / texWidth)
    tmpRegion.v2 = tmpRegion.v + (uHeight / texHeight)
    batch.draw(tmpRegion, x, y, width, height)
  }

  fun drawNinePatch(
    texture: TextureRegion,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    leftInset: Int,
    topInset: Int,
    rightInset: Int,
    bottomInset: Int,
    texWidth: Int = tmpRegion.regionWidth,
    texHeight: Int = tmpRegion.regionHeight,
  ) {
    // Corners
    drawTexture(
      texture = texture,
      x = x,
      y = y,
      width = width,
      height = height,
      u = 0F,
      v = 0F,
      uWidth = leftInset.toFloat(),
      uHeight = topInset.toFloat(),
      texWidth = texWidth,
      texHeight = texHeight
    )
    drawTexture(
      texture = texture,
      x = x,
      y = y + height - bottomInset,
      width = width,
      height = bottomInset.toFloat(),
      u = 0F,
      v = texHeight - bottomInset.toFloat(),
      uWidth = leftInset.toFloat(),
      uHeight = bottomInset.toFloat(),
      texWidth = texWidth,
      texHeight = texHeight
    )
    drawTexture(
      texture = texture,
      x = x + width - rightInset,
      y = y,
      width = rightInset.toFloat(),
      height = height,
      u = texWidth - rightInset.toFloat(),
      v = 0F,
      uWidth = rightInset.toFloat(),
      uHeight = topInset.toFloat(),
      texWidth = texWidth,
      texHeight = texHeight
    )
    drawTexture(
      texture = texture,
      x = x + width - rightInset,
      y = y + height - bottomInset,
      width = rightInset.toFloat(),
      height = bottomInset.toFloat(),
      u = texWidth - rightInset.toFloat(),
      v = texHeight - bottomInset.toFloat(),
      uWidth = rightInset.toFloat(),
      uHeight = bottomInset.toFloat(),
      texWidth = texWidth,
      texHeight = texHeight
    )

    // Edges
    for (dx in leftInset until (width - rightInset).toInt()) {
      drawTexture(
        texture = texture,
        x = x + dx,
        y = y,
        width = width - leftInset - rightInset,
        height = topInset.toFloat(),
        u = leftInset + dx.toFloat(),
        v = 0F,
        uWidth = width - leftInset - rightInset,
        uHeight = topInset.toFloat(),
        texWidth = texWidth,
        texHeight = texHeight
      )
      drawTexture(
        texture = texture,
        x = x + dx,
        y = y + height - bottomInset,
        width = width - leftInset - rightInset,
        height = bottomInset.toFloat(),
        u = leftInset + dx.toFloat(),
        v = texHeight - bottomInset.toFloat(),
        uWidth = width - leftInset - rightInset,
        uHeight = bottomInset.toFloat(),
        texWidth = texWidth,
        texHeight = texHeight
      )
    }
    for (dy in topInset until (height - bottomInset).toInt()) {
      drawTexture(
        texture = texture,
        x = x,
        y = y + dy,
        width = leftInset.toFloat(),
        height = height - topInset - bottomInset,
        u = 0F,
        v = topInset + dy.toFloat(),
        uWidth = leftInset.toFloat(),
        uHeight = height - topInset - bottomInset,
        texWidth = texWidth,
        texHeight = texHeight
      )
      drawTexture(
        texture = texture,
        x = x + width - rightInset,
        y = y + dy,
        width = rightInset.toFloat(),
        height = height - topInset - bottomInset,
        u = texWidth - rightInset.toFloat(),
        v = topInset + dy.toFloat(),
        uWidth = rightInset.toFloat(),
        uHeight = height - topInset - bottomInset,
        texWidth = texWidth,
        texHeight = texHeight
      )
    }

    // Center
    for (dx in leftInset until (width - rightInset).toInt() step texture.regionWidth - leftInset - rightInset)
      for (dy in topInset until (height - bottomInset).toInt() step texture.regionHeight - topInset - bottomInset)
        drawTexture(
          texture = texture,
          x = x + dx,
          y = y + dy,
          width = width - leftInset - rightInset,
          height = height - topInset - bottomInset,
          u = leftInset + dx.toFloat(),
          v = topInset + dy.toFloat(),
          uWidth = width - leftInset - rightInset,
          uHeight = height - topInset - bottomInset,
          texWidth = texWidth,
          texHeight = texHeight
        )
  }

  fun drawText(text: String, x: Float, y: Float) {
    font.drawMarkupText(batch, text, x, y)
  }

  fun drawText(layout: Layout, x: Float, y: Float) {
    font.drawGlyphs(batch, layout, x, y)
  }

  fun textWidth(text: String): Float {
    layout.reset()
    return font.markup(text, layout).let { layout.width }
  }

  fun textHeight(text: String): Float {
    layout.reset()
    return font.markup(text, layout).let { layout.height }
  }

  fun begin() {
    batch.begin()
  }

  fun end() {
    batch.end()
  }

  fun dispose() {
    batch.dispose()
  }

  fun use(block: (GuiRenderer) -> Unit) {
    begin()
    block(this)
    end()
  }
}
