package dev.ultreon.quantum

import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.resource.ResourceId
import dev.ultreon.quantum.util.NamespaceID

val logger = LoggerFactory["QuantumVoxel"]

val Block.id: NamespaceID
  get() = Registries.blocks[this] ?: throw NoSuchElementException("Block not registered: $this")

val Item.id: NamespaceID
  get() = Registries.items[this] ?: throw NoSuchElementException("Item not registered: $this")

val Block.key: ResourceId<Block>
  get() = Registries.blocks.getKey(this) ?: throw NoSuchElementException("Block not registered: $this")

val Item.key: ResourceId<Item>
  get() = Registries.items.getKey(this) ?: throw NoSuchElementException("Item not registered: $this")
