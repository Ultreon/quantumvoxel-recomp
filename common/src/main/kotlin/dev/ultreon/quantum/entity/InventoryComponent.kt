package dev.ultreon.quantum.entity

class InventoryComponent : Component<InventoryComponent>() {
  var hotbarIndex: Int = 0
  private val slots = Array(30) { ItemStack() }
  private val hotbar = Array(10) { ItemStack() }
  override val componentType = ComponentType.inventory
}
