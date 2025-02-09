package dev.ultreon.quantum.entity

import com.badlogic.gdx.utils.JsonValue

abstract class PlayerComponent<T : PlayerComponent<T>>(val name: String = "Player") : Component<T>() {
  override fun json(): JsonValue {
    return JsonValue(JsonValue.ValueType.`object`).also { json ->
      // No-op
    }
  }

  override fun load(json: JsonValue) {

  }
}
