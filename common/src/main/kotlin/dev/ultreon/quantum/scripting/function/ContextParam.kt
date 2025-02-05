package dev.ultreon.quantum.scripting.function

import com.badlogic.gdx.utils.JsonValue

class ContextParam<T : Any>(val name: String, val type: ContextType<T>) {
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

  fun of(json: JsonValue): ContextValue<T>? {
    return type.parse(json)
  }
}
