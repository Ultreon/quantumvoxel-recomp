package dev.ultreon.quantum.entity

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.scripting.json

class NetworkComponent : Component<NetworkComponent>() {
  override val componentType = ComponentType.network
  override fun json(): JsonValue {
    return null.json()
  }

  override fun load(json: JsonValue) = Unit
}
