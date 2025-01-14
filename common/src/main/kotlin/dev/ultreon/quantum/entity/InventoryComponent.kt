package dev.ultreon.quantum.entity

import com.artemis.Component
import dev.ultreon.quantum.inventory.Slot

class InventoryComponent : Component() {
  var hotbarIndex: Int = 0
  private val slots = Array(30) { ItemStack() }
  private val hotbar = Array(10) { ItemStack() }
}
