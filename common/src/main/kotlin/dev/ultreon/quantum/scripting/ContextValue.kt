package dev.ultreon.quantum.scripting

import com.badlogic.gdx.utils.JsonValue

typealias PersistentData = HashMap<String, ContextValue<*>>

data class ContextValue<T : Any>(val type: ContextType<out T>, val value: T) {
  @Suppress("UNCHECKED_CAST")
  fun isSame(t: ContextValue<*>?): ContextValue<Boolean> {
    if (t == null) return ContextValue(ContextType.boolean, false)
    val let = (type as ContextType<T>).isSame(this, t)
    return ContextValue(ContextType.boolean, let)
  }

  @Suppress("UNCHECKED_CAST")
  fun serialize(): JsonValue {
    return JsonValue(JsonValue.ValueType.`object`).also {
      it.addChild("type", ContextType.type.serialize(type))
      it.addChild("value", (type as ContextType<T>).serialize(value))
    }
  }

  val persistentData: PersistentData
    get() = asObject.persistentData

  val asInt: Int
    get() = value as Int

  val asLong: Long
    get() = value as Long

  val asFloat: Float
    get() = value as Float

  val asDouble: Double
    get() = value as Double

  val asObject: ContextAware<*>
    get() = value as ContextAware<*>

  val isInt: Boolean
    get() = value is Int

  val isLong: Boolean
    get() = value is Long

  val isFloat: Boolean
    get() = value is Float

  val isDouble: Boolean
    get() = value is Double

  val isObject: Boolean
    get() = value is ContextAware<*>
}
