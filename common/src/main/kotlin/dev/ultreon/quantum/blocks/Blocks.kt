package dev.ultreon.quantum.blocks

import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.util.NamespaceID

object Blocks {
  val air: Block = register("air", Block())
  val dirt = register("dirt", Block())


  fun register(name: String, block: Block): Block {
    Registries.blocks.register(NamespaceID.of(path = name), block)
    return block
  }
}
