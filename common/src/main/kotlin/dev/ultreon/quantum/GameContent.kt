package dev.ultreon.quantum

import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.registry.Registry
import dev.ultreon.quantum.resource.ResourceId
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.quantum.util.asIdOrNull

open class GameContent<T : Any>(private val registry: ResourceId<Registry<T>>) {
  @Suppress("UNCHECKED_CAST")
  val id: Registry<T> get() = Registries.get(registry, javaClass as Class<T>)

  fun register(name: String) {
    @Suppress("UNCHECKED_CAST")
    id.register(name.asIdOrNull() ?: throw IllegalArgumentException("Invalid ID: $name"), this as T)
  }

  fun register(name: NamespaceID) {
    @Suppress("UNCHECKED_CAST")
    id.register(name, this as T)
  }
}
