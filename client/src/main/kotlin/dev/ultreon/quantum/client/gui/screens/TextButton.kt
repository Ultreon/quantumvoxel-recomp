package dev.ultreon.quantum.client.gui.screens

import com.badlogic.gdx.utils.JsonValue
import com.github.tommyettinger.textra.Layout
import dev.ultreon.quantum.client.gui.GuiRenderer
import dev.ultreon.quantum.client.gui.widget.GuiContainer
import dev.ultreon.quantum.client.gui.widget.Widget
import dev.ultreon.quantum.client.quantum
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.util.id

class TextButton(parent: GuiContainer?, widget: JsonValue) : Widget(parent, widget) {
  private var textLabel: Layout = Layout()

  init {
    val text = widget["text"]?.asString() ?: "..."
    var color = widget["appearance"]?.run { this["color"]?.asString() ?: "#ffffff" } ?: "#ffffff"
    var size = widget["size"]?.asIntArray() ?: intArrayOf(21, 21)

    if (size.isEmpty()) {
      size = intArrayOf(21, 21)
    } else if (size.size == 1) {
      size = intArrayOf(size[0], size[0])
    }

    width = size[0].toFloat()
    height = size[1].toFloat()

    if (!color.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"))) {
      logger.error("Invalid color: $color")
      color = "#ffffff"
    }

    quantum.font.markup("[$color]$text", textLabel)
  }

  override fun render(renderer: GuiRenderer, mouseX: Int, mouseY: Int, delta: Float) {
    renderer.drawNinePatch(
      id(path = "textures/gui/buttons/dark.png"),
      x, y, width, height,
      7, 7, 7, 7
    )

    renderer.drawText(layout = textLabel, x, y)
  }
}
