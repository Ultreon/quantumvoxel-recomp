package dev.ultreon.quantum.client

import dev.ultreon.quantum.client.entity.ClientComponentTypes
import dev.ultreon.quantum.entity.ComponentType
import dev.ultreon.quantum.entity.PlayerComponent

class LocalPlayerComponent @JvmOverloads constructor(name: String = "local") : PlayerComponent<LocalPlayerComponent>(name) {
  override val componentType: ComponentType<out LocalPlayerComponent> = ClientComponentTypes.localPlayer
}
