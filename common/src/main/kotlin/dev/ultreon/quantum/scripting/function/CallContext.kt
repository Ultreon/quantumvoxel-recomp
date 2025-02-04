package dev.ultreon.quantum.scripting.function

import com.badlogic.gdx.utils.JsonValue

class CallContext {
  private val paramValues = hashMapOf<String, Any>()

  operator fun set(name: ContextType<String>, value: String) {
    paramValues[name] = value
  }

  operator fun set(name: ContextType<Int>, value: Int) {
    paramValues[name] = value
  }

  operator fun set(name: ContextType<Double>, value: Double) {
    paramValues[name] = value
  }

  operator fun set(name: ContextType<Boolean>, value: Boolean) {
    paramValues[name] = value
  }

  operator fun <T> set(name: ContextType<T>, value: T) {
    paramValues[name] = value
  }

  fun getString(name: String): String? {
    return paramValues[name] as? String
  }

  fun getInt(name: String): Int? {
    return paramValues[name] as? Int
  }

  fun getDouble(name: String): Double? {
    return paramValues[name] as? Double
  }

  fun getBoolean(name: String): Boolean? {
    return paramValues[name] as? Boolean
  }

  fun <T> get(contextType: ContextType<T>, type: Class<T>): T? {
    return type.cast(paramValues[contextType])
  }

  inline fun <reified T : ContextAware<T>> get(contextType: ContextType<T>): T? {
    return get(contextType, T::class.java)
  }

  @Suppress("UNCHECKED_CAST")
  fun getList(name: String): List<ContextAware<*>>? {
    return paramValues[name] as? List<ContextAware>
  }

  inline fun <reified T : ContextAware<T>> get(name: String): T? {
    return get() as? T
  }

  inline fun <reified T : ContextAware<T>> getList(name: String): List<T>? {
    return getList(name)?.mapNotNull { it as? T? }
  }

  fun getRaw(s: String): Any? {
    return paramValues[s]
  }
}
