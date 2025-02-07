package dev.ultreon.quantum.client.gui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.github.tommyettinger.textra.Layout
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.quantum
import dev.ultreon.quantum.util.NamespaceID

class GuiRenderer(private val batch: SpriteBatch) {
  val font = QuantumVoxel.instance.font

  private val tmpRegion = TextureRegion()
  private val layout = Layout()

  private fun blit(
    texture: TextureRegion,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    u: Float,
    v: Float,
    uSize: Float,
    vSize: Float,
    texWidth: Int,
    texHeight: Int,
  ) {
    val uDiff = texture.u2 - texture.u
    val vDiff = texture.v2 - texture.v

    tmpRegion.texture = texture.texture
    tmpRegion.u = texture.u + (u / texWidth) * uDiff
    tmpRegion.v = texture.v + (v / texHeight) * vDiff
    tmpRegion.u2 = (u + uSize) / texWidth * uDiff + texture.u
    tmpRegion.v2 = (v + vSize) / texHeight * vDiff + texture.v
    batch.draw(tmpRegion, x, y, width, height)
  }

  private fun drawTexture(
    texture: TextureRegion,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    u: Float = 0F,
    v: Float = 0F,
    uSize: Float,
    vSize: Float,
    texWidth: Int,
    texHeight: Int,
  ) {
    blit(texture, x, y, width, height, u, v, uSize, vSize, texWidth, texHeight)
  }

  fun drawTexture(
    texture: NamespaceID,
    x: Float,
    y: Float,
    width: Float,
    height: Float
  ) {
    val texture1 = quantum.textureManager[texture]
    drawTexture(
      texture1,
      x,
      y,
      width,
      height,
      0F,
      0F,
      texture1.regionWidth.toFloat(),
      texture1.regionHeight.toFloat(),
      texture1.regionWidth,
      texture1.regionHeight
    )
  }

  fun drawTexture(
    texture: NamespaceID,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    texWidth: Int,
    texHeight: Int
  ) {
    drawTexture(quantum.textureManager[texture], x, y, width, height, 0F, 0F, width, height, texWidth, texHeight)
  }

  fun drawTexture(
    texture: NamespaceID,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    u: Float,
    v: Float,
    texWidth: Int,
    texHeight: Int
  ) {
    drawTexture(quantum.textureManager[texture], x, y, width, height, u, v, width, height, texWidth, texHeight)
  }

  fun drawTexture(
    texture: NamespaceID,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    u: Float,
    v: Float,
    uSize: Float,
    vSize: Float,
    texWidth: Int,
    texHeight: Int
  ) {
    drawTexture(quantum.textureManager[texture], x, y, width, height, u, v, uSize, vSize, texWidth, texHeight)
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

  private fun drawNinePatch(
    texture: TextureRegion,
    leftInset: Float,
    topInset: Float,
    rightInset: Float,
    bottomInset: Float,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    texWidth: Int,
    texHeight: Int,
  ) {
    // Corners
    drawTexture(
      texture,
      x,
      y + height - bottomInset,
      leftInset,
      topInset,
      0F,
      0F,
      leftInset,
      topInset,
      texWidth,
      texHeight
    )
    drawTexture(
      texture,
      x + width - rightInset,
      y + height - bottomInset,
      rightInset,
      topInset,
      texWidth - rightInset,
      0F,
      rightInset,
      topInset,
      texWidth,
      texHeight
    )
    drawTexture(
      texture,
      x,
      y,
      leftInset,
      bottomInset,
      0F,
      texHeight - bottomInset,
      leftInset,
      bottomInset,
      texWidth,
      texHeight
    )
    drawTexture(
      texture,
      x + width - rightInset,
      y,
      rightInset,
      bottomInset,
      texWidth - rightInset,
      texHeight - bottomInset,
      rightInset,
      bottomInset,
      texWidth,
      texHeight
    )

    // Horizontal
    for (i in leftInset.toInt() until (width - rightInset).toInt() step (texWidth - leftInset - rightInset).toInt()) {
      drawTexture(
        texture,
        x + i,
        y + height - bottomInset,
        texWidth - leftInset - rightInset,
        topInset,
        leftInset,
        0F,
        texWidth - leftInset - rightInset,
        topInset,
        texWidth,
        texHeight
      )
      drawTexture(
        texture,
        x + i,
        y,
        texWidth - leftInset - rightInset,
        bottomInset,
        leftInset,
        texHeight - bottomInset,
        texWidth - leftInset - rightInset,
        bottomInset,
        texWidth,
        texHeight
      )
    }

    // Vertical
    for (i in topInset.toInt() until (height - bottomInset).toInt() step (texHeight - topInset - bottomInset).toInt()) {
      drawTexture(
        texture,
        x,
        y + i,
        leftInset,
        texHeight - topInset - bottomInset,
        0F,
        topInset,
        leftInset,
        texHeight - topInset - bottomInset,
        texWidth,
        texHeight
      )
      drawTexture(
        texture,
        x + width - rightInset,
        y + i,
        rightInset,
        texHeight - topInset - bottomInset,
        texWidth - rightInset,
        topInset,
        rightInset,
        texHeight - topInset - bottomInset,
        texWidth,
        texHeight
      )
    }

    // Center
    for (i in leftInset.toInt() until (width - rightInset).toInt() step (texWidth - leftInset - rightInset).toInt()) {
      for (j in topInset.toInt() until (height - bottomInset).toInt() step (texHeight - topInset - bottomInset).toInt()) {
        drawTexture(
          texture,
          x + i,
          y + j,
          texWidth - leftInset - rightInset,
          texHeight - topInset - bottomInset,
          leftInset,
          topInset,
          texWidth - leftInset - rightInset,
          texHeight - topInset - bottomInset,
          texWidth,
          texHeight
        )
      }
    }
  }

  fun drawNinePatch(
    texture: NamespaceID,
    inset: Float,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    texWidth: Int,
    texHeight: Int,
  ) {
    val textureRegion = quantum.textureManager[texture]
    drawNinePatch(textureRegion, inset, inset, inset, inset, x, y - height, width, height, texWidth, texHeight)
  }

  fun drawNinePatch(
    texture: NamespaceID,
    leftInset: Float,
    topInset: Float,
    rightInset: Float,
    bottomInset: Float,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    texWidth: Int,
    texHeight: Int,
  ) {
    val textureRegion = quantum.textureManager[texture]
    drawNinePatch(textureRegion, leftInset, topInset, rightInset, bottomInset, x, y - height, width, height, texWidth, texHeight)
  }
}
