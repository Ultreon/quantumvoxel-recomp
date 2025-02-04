package dev.ultreon.quantum.scripting.function

import com.badlogic.gdx.utils.JsonValue

class CallContext {
  val paramValues = hashMapOf<String, ContextValue<*>>()

  operator fun <T : Any> set(type: ContextType<out T>, value: T) {
    paramValues[type.name] = ContextValue(type, value)
  }

  operator fun <T : Any> set(name: String, value: ContextValue<T>) {
    paramValues[name] = value
  }

  fun getString(name: String): String? {
    return paramValues[name]?.value as? String
  }

  fun getInt(name: String): Int? {
    return paramValues[name]?.value as? Int
  }

  fun getDouble(name: String): Double? {
    return paramValues[name]?.value as? Double
  }

  fun getBoolean(name: String): Boolean? {
    return paramValues[name]?.value as? Boolean
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : Any> get(contextType: ContextType<T>): ContextValue<T>? {
    return paramValues[contextType.name] as ContextValue<T>?
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : Any> getList(contextType: ContextType<T>): List<ContextValue<*>>? {
    return paramValues[contextType.name] as? List<ContextValue<*>>
  }

  inline fun <reified T : ContextAware<T>> get(name: String): T? {
    return paramValues[name] as? T
  }

  inline fun <reified T : ContextAware<T>> getList(name: String): List<T>? {
    return (paramValues[name] as? List<*>)?.mapNotNull { it as? T? }
  }

  inline fun <reified T : Any> getNullableList(name: String): List<T?>? {
    return (paramValues[name] as? List<*>)?.map { it as? T? }
  }

  fun getRaw(s: String): Any? {
    return paramValues[s]
  }

  companion object {
    fun from(value: JsonValue): CallContext {
      val context = CallContext()
      if (!value.isArray) {
        throw IllegalArgumentException("CallContext value must be an array")
      }
      value.forEach {
        context.paramValues[it.getString("type")] = ContextType[it.getString("type")]?.parse(it) as ContextValue<*>
      }
      return context
    }
  }
}
