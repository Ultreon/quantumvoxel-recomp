package dev.ultreon.quantum.server.player

import dev.ultreon.quantum.entity.ComponentType
import dev.ultreon.quantum.entity.PlayerComponent

class ServerPlayerComponent(name: String) : PlayerComponent<ServerPlayerComponent>(name) {
  override val componentType: ComponentType<out ServerPlayerComponent>
    get() = ComponentType.Companion.serverPlayer
}
