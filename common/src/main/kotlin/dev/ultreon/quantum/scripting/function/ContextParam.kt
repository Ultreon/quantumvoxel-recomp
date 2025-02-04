package dev.ultreon.quantum.scripting.function

class ContextParam<T>(val name: String, val type: ContextType<T>) {
  /**
   * Cast the value to the correct type
   * @param value The value to cast
   */
  fun cast(value: Any): T {
    return type.cast(value)
  }

  override fun toString(): String {
    return "ContextParam(name=$name, type=$type)"
  }
}
