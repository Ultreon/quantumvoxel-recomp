package dev.ultreon.quantum.blocks

import dev.ultreon.quantum.registry.Registry
import dev.ultreon.quantum.resource.ResourceManager
import dev.ultreon.quantum.util.NamespaceID

abstract class GameContent<T : Any>(
  private val registry: Registry<T>
) {
  fun register(name: String, block: T): T {
    registry.register(NamespaceID.of(path = name), block)
    return block
  }

  fun register(name: NamespaceID, block: T): T {
    registry.register(name, block)
    return block
  }

  open fun init() {
    // No-op
  }

  abstract fun loadContent(resources: ResourceManager)
}
