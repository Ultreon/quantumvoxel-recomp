package dev.ultreon.quantum.entity

import dev.ultreon.quantum.id
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.item.Items

data class ItemStack(
  val count: Int = 1,
  val item: Item
) {
  constructor() : this(0, Items.air)
  fun isEmpty(): Boolean = count == 0

  fun isNotEmpty(): Boolean = count > 0

  fun clone(): ItemStack = ItemStack(count, item)

  override fun toString(): String {
    return "${count}x ${item.id}"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ItemStack

    if (count != other.count) return false
    if (item != other.item) return false

    return true
  }

  override fun hashCode(): Int {
    var result = count
    result = 31 * result + item.hashCode()
    return result
  }

  companion object {
    val EMPTY = ItemStack(0, Items.air)
  }
}
