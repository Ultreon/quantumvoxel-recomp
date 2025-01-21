package dev.ultreon.quantum.registry

import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.PropertyKey
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.util.NamespaceID.Companion.of

object Registries {
  val blocks = Registry<Block>(of(path = "blocks"))
  val items = Registry<Item>(of(path = "items"))
  val stateProperties = Registry<PropertyKey<*>>(of(path = "state_properties"))
}
