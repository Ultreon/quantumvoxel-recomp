package dev.ultreon.quantum.client.model

import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.JsonReader
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.id
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.registry.RegistryKeys
import dev.ultreon.quantum.resource.ResourceId
import dev.ultreon.quantum.util.NamespaceID

object ModelRegistry : Disposable {
  private val blockModels = mutableMapOf<Block, JsonModel>()
  private val itemModels = mutableMapOf<Item, JsonModel>()
  private val _fallbackModel: JsonModel = QuantumVoxel.jsonModelLoader.load(
    ResourceId.of(RegistryKeys.blocks, NamespaceID.of(path = "error")), JsonReader().parse("""
    {
      "parent": "block/cube_all",
      "textures": {
        "all": "block/error"
      },
      "elements": [
        {
          "from": [ 0, 0, 0 ],
          "to": [ 16, 16, 16 ],
          "faces": {
            "down":  { "uv": [ 0, 0, 16, 16 ], "texture": "#all", "cullface": "down" },
            "up":    { "uv": [ 0, 0, 16, 16 ], "texture": "#all", "cullface": "up" },
            "north": { "uv": [ 0, 0, 16, 16 ], "texture": "#all", "cullface": "north" },
            "south": { "uv": [ 0, 0, 16, 16 ], "texture": "#all", "cullface": "south" },
            "west":  { "uv": [ 0, 0, 16, 16 ], "texture": "#all", "cullface": "west" },
            "east":  { "uv": [ 0, 0, 16, 16 ], "texture": "#all", "cullface": "east" }
          }
        }
      ]
    }
    """.trimIndent()))

  val fallbackModel: JsonModel
    get() = _fallbackModel

  fun loadModels() {
    val jsonModelLoader = QuantumVoxel.jsonModelLoader
    Registries.blocks.values.forEach {
      if (it != Blocks.air) logger.debug("Loading block model for ${it.id}")
      else return@forEach

      val load = jsonModelLoader.load(it)
      load?.let { model ->
        blockModels[it] = model
      } ?: run {
        logger.warn("No block model for ${it.id}")
      }
    }
    Registries.items.values.forEach {
      val load = jsonModelLoader.load(it)
      load?.let { model ->
        itemModels[it] = model
      } ?: run {
        logger.warn("No item model for ${it.id}")
      }
    }
  }

  operator fun get(block: Block): JsonModel = blockModels[block] ?: fallbackModel
  operator fun get(item: Item): JsonModel = itemModels[item] ?: fallbackModel

  override fun dispose() {
    blockModels.values.forEach { it.dispose() }
    itemModels.values.forEach { it.dispose() }
  }
}
