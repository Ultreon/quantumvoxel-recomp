package dev.ultreon.quantum.entity

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.id
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.item.Items
import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.util.asIdOrNull

data class ItemStack(
  val count: Int = 1,
  val item: Item
) {
  constructor() : this(0, Items.air)
  constructor(jsonValue: JsonValue) : this(
    count = jsonValue["count"].asInt(),
    item = Registries.items[jsonValue["item"].asString().asIdOrNull() ?: Items.air.id] ?: Items.air
  )

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

  fun json(): JsonValue {
    return JsonValue(JsonValue.ValueType.`object`).also {
      it.addChild("count", JsonValue(count.toLong()))
      it.addChild("item", JsonValue(item.id.toString()))
    }
  }

  companion object {
    val EMPTY = ItemStack(0, Items.air)
  }
}
