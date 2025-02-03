package dev.ultreon.quantum.registry

import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.PropertyKey
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.resource.ResourceId
import dev.ultreon.quantum.util.NamespaceID.Companion.of

object Registries {
  inline operator fun <reified T : Any> get(registry: ResourceId<Registry<T>>): Registry<T> {
    return registryFor(registry.name, T::class.java)
  }

  fun <T : Any> get(registry: ResourceId<Registry<T>>, type: Class<T>): Registry<T> {
    return registries[registry.name]?.cast(type) ?: throw NoSuchElementException("Registry not found: $registry")
  }

  val blocks = Registry<Block>(of(path = "blocks"))
  val items = Registry<Item>(of(path = "items"))
  val stateProperties = Registry<PropertyKey<*>>(of(path = "state_properties"))
}
