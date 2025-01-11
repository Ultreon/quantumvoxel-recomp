package dev.ultreon.quantum.blocks

import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.util.NamespaceID

object Blocks {
  val air: Block = register("air", Block().apply { hasCollider = false })
  val soil = register("soil", Block())
  val grass = register("grass", Block())
  val stone = register("stone", Block())
  val crate = register("crate", Block())
  val water = register("water", Block().apply { renderType = "water"; isFluid = true })
  val sand = register("sand", Block())
  val cobblestone = register("cobblestone", Block())
  val snowyGrass = register("snowy_grass", Block())
  val iron: Block = register("iron_block", Block())

  fun register(name: String, block: Block): Block {
    Registries.blocks.register(NamespaceID.of(path = name), block)
    return block
  }

  /**
   * Initializes the necessary components or configurations for the system.
   * Specifically required for TeaVM to function properly as it doesn't handle
   * dynamically loading the class by relying solely on the "Blocks" construct in Kotlin.
   */
  fun init() {
    // No-op
  }
}
