package dev.ultreon.quantum.registry

import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.entity.EntityTemplate
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.resource.ResourceId
import dev.ultreon.quantum.util.NamespaceID.Companion.of

object RegistryKeys {
  val entityTypes = ResourceId.create<Registry<EntityTemplate>>(of(path = "entity_types"))
  val blocks = ResourceId.create<Registry<Block>>(of(path = "blocks"))
  val items = ResourceId.create<Registry<Item>>(of(path = "items"))
}
