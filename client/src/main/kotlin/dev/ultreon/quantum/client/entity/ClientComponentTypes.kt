package dev.ultreon.quantum.client.entity

import dev.ultreon.quantum.client.LocalPlayerComponent
import dev.ultreon.quantum.entity.ComponentType

object ClientComponentTypes {
  val localPlayer = ComponentType("player") { LocalPlayerComponent() }
}
