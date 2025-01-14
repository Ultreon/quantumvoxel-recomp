package dev.ultreon.quantum.item

import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.util.id

object Items {
  val air = Item()
  val soil = BlockItem { Blocks.soil }
  val stone = BlockItem { Blocks.stone }
  val grass = BlockItem { Blocks.grass }
  val cobblestone = BlockItem { Blocks.cobblestone }
  val iron = BlockItem { Blocks.iron }
  val snowyGrass = BlockItem { Blocks.snowyGrass }
  val shortGrass = BlockItem { Blocks.shortGrass }
  val sand = BlockItem { Blocks.sand }
  val crate = BlockItem { Blocks.crate }

  init {
    Registries.items.register(id(path = "air"), air)
    Registries.items.register(id(path = "soil"), soil)
    Registries.items.register(id(path = "stone"), stone)
    Registries.items.register(id(path = "grass"), grass)
    Registries.items.register(id(path = "cobblestone"), cobblestone)
    Registries.items.register(id(path = "iron"), iron)
    Registries.items.register(id(path = "snowy_grass"), snowyGrass)
    Registries.items.register(id(path = "short_grass"), shortGrass)
    Registries.items.register(id(path = "sand"), sand)
    Registries.items.register(id(path = "crate"), crate)
    Registries.items.freeze()
  }
}
