package dev.ultreon.quantum.entity

import com.badlogic.gdx.utils.JsonValue

class RunningComponent(var runSpeedModifier: Float = 1F, var running: Boolean = false) : Component<RunningComponent>() {
  override val componentType = ComponentType.running
  override fun json(): JsonValue {
    return JsonValue(JsonValue.ValueType.`object`).also { json ->
      json.addChild("runSpeedModifier", JsonValue(runSpeedModifier.toDouble()))
    }
  }

  override fun load(json: JsonValue) {
    runSpeedModifier = json["runSpeedModifier"]?.asFloat() ?: 1F
  }
}
