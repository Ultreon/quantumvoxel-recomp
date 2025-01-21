package dev.ultreon.quantum.blocks

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.id
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.util.NamespaceID
import ktx.collections.GdxArray

// Data classes representing the structure of the JSON file
data class BlockStateData @JvmOverloads constructor(
  val states: HashMap<String, List<StateVariant>> = hashMapOf(),
  val behavior: BlockBehavior = BlockBehavior(),
  val interactions: BlockInteractions = BlockInteractions(),
  val physics: BlockPhysics = BlockPhysics(),
  val rendering: BlockRendering = BlockRendering(),
)

private val jsonReader = JsonReader()

data class BlockRendering @JvmOverloads constructor(
  private val renderTypeName: String = "default",
  // TODO Add more rendering properties
) {
  val renderType get() = renderTypeName
}

object StateDefinitions {
  private val _defaults: HashMap<Block, BlockState> = HashMap()
  val defaults: Map<Block, BlockState>
    get() = _defaults
  private val stateDefinitions: HashMap<Block, BlockStateDefinition> = HashMap()

  operator fun get(block: Block): BlockStateDefinition? = stateDefinitions[block]

  operator fun set(block: Block, definition: BlockStateDefinition) {
    stateDefinitions[block] = definition
  }

  operator fun set(block: Block, state: BlockState) {
    _defaults[block] = state
  }

  fun load(block: Block) {
    val fileHandle = FileHandle("blocks/statedefs/${block.id}.quant")
    if (!fileHandle.exists()) return
    val data = parseDefinition(fileHandle.readString())
    stateDefinitions[block] = data.first
    _defaults[block] = data.second
  }

  @Suppress("UNCHECKED_CAST")
  private fun parseDefinition(text: String): Pair<BlockStateDefinition, BlockState> {
    jsonReader.parse(text).run {
      val definition = BlockStateDefinition(
        get("properties").run {
          val properties = GdxArray<PropertyKey<*>>()
          forEach { property ->
            properties.add((Registries.stateProperties[NamespaceID.parse(property.name)]))
          }
          properties
        }
      )
      return definition to get("defaultState").run {
        definition.stateWith(
          *definition.map { property ->
            property to (property as PropertyKey<Any>).valueByIndex(0)
          }.toTypedArray()
        )
      }
    }
  }
}

data class StateVariant @JvmOverloads constructor(
  val value: Any,
  val model: String = "",
  val conditions: HashMap<String, String>? = null,
)

data class BlockBehavior @JvmOverloads constructor(
  val breakSpeed: Float = 1f,
  val tools: ArrayList<String> = arrayListOf(),
  val drop: String? = "",
) {
  private val toolIds: Set<NamespaceID> = tools.mapNotNull { NamespaceID.parseOrNull(it) }.toSet()
  fun isToolValid(tool: Item): Boolean = toolIds.contains(tool.id)

  val dropItem: Item? = drop?.let { NamespaceID.parseOrNull(it)?.let { Registries.items[it] } }
}

data class BlockInteractions @JvmOverloads constructor(
  val onStep: InteractionEffect = InteractionEffect(),
  val onPlace: InteractionEffect = InteractionEffect(),
)

data class InteractionEffect @JvmOverloads constructor(
  val sound: String? = null,
  val particles: String? = null,
  val effect: String? = null,
)

data class BlockPhysics @JvmOverloads constructor(
  val collisionTypeName: String = "",
  val friction: Float = 0.6f,
) {
  enum class Collision {
    SOLID, LIQUID, GAS, NONE
  }

  val collision: Collision = when (collisionTypeName) {
    "solid" -> Collision.SOLID
    "liquid" -> Collision.LIQUID
    "gas" -> Collision.GAS
    "none" -> Collision.NONE
    else -> throw IllegalStateException("Invalid collision type: $collisionTypeName")
  }
}

private val json = Json()

fun parseBlockState(json: JsonValue): BlockStateData {
  return BlockStateData(
    states = json["states"].run {
      val stateMap = HashMap<String, List<StateVariant>>()
      forEach { state ->
        stateMap[state.name] = state.map {
          val parse = NamespaceID.parse(state.name)
          StateVariant(
            value = (Registries.stateProperties[parse]
              ?: throw IllegalStateException("Property not registered: $parse")).read(it.name),
            model = it.get("model").asString(),
            conditions = it.get("conditions")?.run {
              if (isObject) {
                val condMap = HashMap<String, String>()
                forEach { cond -> condMap[cond.name] = cond.asString() }
                condMap
              } else null
            }
          )
        }
      }

      stateMap
    },
    behavior = json["behavior"].run {
      BlockBehavior(
        breakSpeed = get("breakSpeed").asFloat(),
        tools = get("tools")?.run {
          val list = ArrayList<String>()
          forEach { list.add(it.asString()) }
          list
        } ?: ArrayList(),
        drop = get("drop")?.asString(),
      )
    },
    interactions = json["interactions"]?.run {
      BlockInteractions(
        onStep = get("onStep")?.run {
          InteractionEffect(
            sound = get("sound").asString(),
            particles = get("particles").asString(),
            effect = get("effect").asString(),
          )
        } ?: InteractionEffect(),
        onPlace = get("onPlace")?.run {
          InteractionEffect(
            sound = get("sound").asString(),
            particles = get("particles").asString(),
            effect = get("effect").asString(),
          )
        } ?: InteractionEffect(),
      )
    } ?: BlockInteractions(),
    physics = json["physics"].run {
      BlockPhysics(
        collisionTypeName = get("collision").asString(),
        friction = get("friction").asFloat(),
      )
    },
    rendering = json["rendering"]?.run {
      BlockRendering(
        renderTypeName = get("render_type").asString(),
      )
    } ?: BlockRendering()
  )
}

fun parseBlockState(handle: FileHandle): BlockStateData = parseBlockState(jsonReader.parse(handle.readString()))

fun main() {
  val jsonString = """
    {
      "defaultState": "blockstate_test",
      "states": {
        "powered": {
          "normal": {
            "model": "blocks/switch_on"
          },
          "snowy": {
            "model": "blocks/grass_block_snowy"
            "conditions": {
              "temperature": "<=0"
            }
          }
        }
      },
      "behavior": {
        "breakSpeed": 1.5,
        "tools": [
          "shovel"
        ],
        "drop": "grass_block"
      },
      "interactions": {
        // Experimental
      },
      "physics": {
        "collision": "solid",
        "friction": 0.6
      },
      "extra": {
        // Experimental
      }
    }

    """.trimIndent()

  PropertyKeys.init()

  val blockState = parseBlockState(jsonReader.parse(jsonString))
  println("States: ${blockState.states.keys}")
  println("Break Speed: ${blockState.behavior.breakSpeed}")
  println("Tools: ${blockState.behavior.tools}")
  println("Collision: ${blockState.physics.collision}")
}
