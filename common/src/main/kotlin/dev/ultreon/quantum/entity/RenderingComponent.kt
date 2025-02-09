package dev.ultreon.quantum.entity

import com.badlogic.gdx.utils.JsonValue

class RenderingComponent : Component<RenderingComponent>() {
  override val componentType = ComponentType.rendering
  override fun json(): JsonValue {
    return JsonValue(JsonValue.ValueType.nullValue)
  }

  override fun load(json: JsonValue) {
    // No-op
  }
}
