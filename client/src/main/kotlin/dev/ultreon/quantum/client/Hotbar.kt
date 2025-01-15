package dev.ultreon.quantum.client

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import dev.ultreon.quantum.client.gui.draw
import dev.ultreon.quantum.entity.InventoryComponent
import dev.ultreon.quantum.util.id

private val hotbarLeft = id(path = "textures/gui/hud/hotbar/hotbar_left.png")
private val hotbarRight = id(path = "textures/gui/hud/hotbar/hotbar_right.png")
private val hotbarDisplay = id(path = "textures/gui/hud/hotbar/hotbar_display.png")
private val hotbarBridge = id(path = "textures/gui/hud/hotbar/hotbar_bridge.png")
private val hotbarSelect = id(path = "textures/gui/hud/hotbar/hotbar_select.png")

class Hotbar : Actor() {
  val index: Int
    get() {
      val player = quantum.player
      val inventory = player?.inventoryComponent
      return inventory?.hotbarIndex ?: 0
    }

  override fun draw(batch: Batch?, parentAlpha: Float) {
    batch?.let {
      if (width > 208) batch.draw(hotbarBridge, x + 100, y, width - 200, 32F)
      batch.draw(hotbarLeft, x, y, 104F, 32F)
      batch.draw(hotbarRight, x + width - 104, y, 104F, 32F)

      batch.draw(hotbarDisplay, x + 1F, y + 32F, 102F, 16F)
      batch.draw(hotbarDisplay, x + width - 103F, y + 32F, 102F, 16F)

      val value: Int = if (index >= 5) (width - 104 + 5 - 105 + 5).toInt() else 0
      batch.draw(hotbarSelect, x + 5 + index * 19 + value, y + 8, 18F, 18F)
    }
  }
}
