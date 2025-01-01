package dev.ultreon.quantum.blocks

import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.util.NamespaceID

object Blocks {
  val air: Block = register("air", Block())
  val soil = register("soil", Block())
  val grass = register("grass", Block())
  val stone = register("stone", Block())
  val crate = register("crate", Block())
  val cobblestone = register("cobblestone", Block())

  fun register(name: String, block: Block): Block {
    Registries.blocks.register(NamespaceID.of(path = name), block)
    return block
  }
}
