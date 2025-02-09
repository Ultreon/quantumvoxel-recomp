package dev.ultreon.quantum.scripting

import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.BlockEntity
import dev.ultreon.quantum.blocks.BlockState
import dev.ultreon.quantum.entity.*
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.math.Axis
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.scripting.condition.VirtualCondition
import dev.ultreon.quantum.scripting.function.VirtualFunction
import dev.ultreon.quantum.scripting.function.VirtualFunctions
import dev.ultreon.quantum.util.Direction
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.quantum.util.asIdOrNull
import dev.ultreon.quantum.world.Dimension
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.reflect.KClass

typealias ComponentTypeList = List<ComponentType<*>>
typealias ComponentMap = Map<ComponentType<*>, Component<*>>


class ContextType<T : Any>(
    val name: String,
    val clazz: Class<T>,
    val parser: (ContextType<T>, JsonValue) -> ContextValue<T>?,
) : ContextAware<ContextType<*>> {
  fun cast(dimension: Any): T {
    return clazz.cast(dimension)
  }

  override fun contextType(): ContextType<ContextType<*>> {
    return type
  }

  override fun fieldOf(name: String, contextJson: JsonValue?): ContextValue<*>? {
    return when (name) {
      "name" -> ContextValue(string, name)
      else -> null
    }
  }

  override fun supportedTypes(): List<ContextType<*>> {
    return listOf(type, this)
  }

  override fun toString(): String {
    return "ContextType(${registry.entries.first { it.value === this }.key})"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is ContextType<*>) return false
    return clazz == other.clazz
  }

  override fun hashCode(): Int {
    return clazz.hashCode()
  }

  fun parse(json: JsonValue): ContextValue<T>? {
    return this.parser.invoke(this, json)
  }

  fun isSame(self: ContextValue<T>, other: ContextValue<out Any>): Boolean {
    return self == other.value
  }

  companion object {
    private val registry = HashMap<String, ContextType<*>>()
    val entityTemplate: ContextType<EntityTemplate> = register("entity-template", parser = { json ->
      val id = json.get("id").asString().asIdOrNull() ?: throw IllegalArgumentException(
        "Invalid ID: ${
          json.get("id").asString()
        }"
      )
      return@register Registries.entityTemplates[id]?.let { ContextValue(this, it) }
        ?: throw IllegalArgumentException("Unknown entity template: ${json.get("id").asString()}")
    })
    val entity: ContextType<Entity> =
      register("entity", parser = { logger.error("Unsupported entity: ${it.trace()}"); return@register null })
    val condition: ContextType<VirtualCondition> = register("condition", parser = { json ->
      val type = json.get("type").asString()
      return@register VirtualCondition[type]?.let { cond -> ContextValue(this, cond) }
        ?: throw IllegalArgumentException("Unknown condition: $type")
    })

    val dimension: ContextType<Dimension> =
      register("dimension", parser = { logger.error("Unsupported dimension: ${it.trace()}"); return@register null })
    val block: ContextType<Block> = register("block", parser = { json ->
      val id = json.get("id").asString().asIdOrNull() ?: throw IllegalArgumentException(
        "Invalid ID: ${
          json.get("id").asString()
        }"
      )
      return@register Registries.blocks[id]?.let { ContextValue(this, it) }
        ?: throw IllegalArgumentException("Unknown block: ${json.get("id").asString()}")
    })
    val blockState: ContextType<BlockState> = register("block-state", parser = { json ->
      val id = json.get("id").asString().asIdOrNull() ?: throw IllegalArgumentException(
        "Invalid ID: ${
          json.get("id").asString()
        }"
      )
      val block = Registries.blocks[id]?.let { ContextValue(block, it) }
        ?: throw IllegalArgumentException("Unknown block state: ${json.get("id").asString()}")

      return@register ContextValue(this, BlockState.parse(json, block))
    })
    val blockEntity: ContextType<BlockEntity> =
      register(
        "block-entity",
        parser = { logger.error("Unsupported block-entity: ${it.trace()}"); return@register null })
    val item: ContextType<Item> = register("item", parser = { json ->
      val id = json.get("id").asString().asIdOrNull() ?: run {
        logger.error("Invalid ID: ${json.get("id").asString()}")
        return@register null
      }
      return@register Registries.items[id]?.let { ContextValue(this, it) }
        ?: run {
          logger.error("Unknown item: ${json.get("id").asString()}")
          null
        }
    })
    val gridPoint: ContextType<GridPoint3> = register("grid-point", parser = { json ->
      val x = json.get("x").asInt()
      val y = json.get("y").asInt()
      val z = json.get("z").asInt()
      return@register ContextValue(this, GridPoint3(x, y, z))
    })
    val vector: ContextType<Vector3D> = register("vector", parser = { json ->
      if (json.isArray) {
        val x = if (json.size >= 1) json.get(0).asDouble() else 0.0
        val y = if (json.size >= 2) json.get(1).asDouble() else 0.0
        val z = if (json.size >= 3) json.get(2).asDouble() else 0.0
        return@register ContextValue(this, Vector3D(x, y, z))
      } else if (json.isObject) {
        val x = json.get("x")?.asDouble() ?: 0.0
        val y = json.get("y")?.asDouble() ?: 0.0
        val z = json.get("z")?.asDouble() ?: 0.0
        return@register ContextValue(this, Vector3D(x, y, z))
      } else if (json.isNumber) {
        return@register ContextValue(this, Vector3D(json.asDouble(), json.asDouble(), json.asDouble()))
      } else {
        logger.error("Invalid vector: $json")
        return@register null
      }
    })
    val direction: ContextType<Direction> = register("direction", parser = { json ->
      val name = json.asString()
      return@register ContextValue(this, enumValues<Direction>().firstOrNull {
          it.name.equals(name, ignoreCase = true)
      } ?: run {
          logger.error("Invalid direction: $name")
          return@register null
      })
    })
    val axis: ContextType<Axis> = register("axis", parser = { json ->
      val name = json.asString()
      return@register ContextValue(this, enumValues<Axis>().firstOrNull {
          it.name.equals(name, ignoreCase = true)
      } ?: run {
          logger.error("Invalid axis: $name")
          return@register null
      })
    })
    val itemStack: ContextType<ItemStack> = register("item-stack", parser = { json ->
      val item = json.get("item").asString().asIdOrNull() ?: run {
        logger.error(
          "Invalid item: ${
            json.get("item").asString()
          }"
        )
        return@register null
      }
      val count = json.get("count").asInt()
      return@register ContextValue(
          this,
          ItemStack(
              count,
              Registries.items[item] ?: throw IllegalArgumentException("Unknown item: ${json.get("item").asString()}")
          )
      )
    })
    val matrix4: ContextType<Matrix4> = register("mat4", parser = { json ->
      if (json.isArray) {
        val a = json.get(0)?.asFloatArray()
        val b = json.get(1)?.asFloatArray()
        val c = json.get(2)?.asFloatArray()
        val d = json.get(3)?.asFloatArray()
        return@register ContextValue(
            this, Matrix4(
                floatArrayOf(
                    a?.get(0) ?: 1.0f, a?.get(1) ?: 0.0f, a?.get(2) ?: 0.0f, a?.get(3) ?: 0.0f,
                    b?.get(0) ?: 0.0f, b?.get(1) ?: 1.0f, b?.get(2) ?: 0.0f, b?.get(3) ?: 0.0f,
                    c?.get(0) ?: 0.0f, c?.get(1) ?: 0.0f, c?.get(2) ?: 1.0f, c?.get(3) ?: 0.0f,
                    d?.get(0) ?: 0.0f, d?.get(1) ?: 0.0f, d?.get(2) ?: 0.0f, d?.get(3) ?: 1.0f
                )
            )
        )
      } else if (json.isObject) {
        val a = json.get("a")?.asFloatArray()
        val b = json.get("b")?.asFloatArray()
        val c = json.get("c")?.asFloatArray()
        val d = json.get("d")?.asFloatArray()
        return@register ContextValue(
            this, Matrix4(
                floatArrayOf(
                    a?.get(0) ?: 1.0f, a?.get(1) ?: 0.0f, a?.get(2) ?: 0.0f, a?.get(3) ?: 0.0f,
                    b?.get(0) ?: 0.0f, b?.get(1) ?: 1.0f, b?.get(2) ?: 0.0f, b?.get(3) ?: 0.0f,
                    c?.get(0) ?: 0.0f, c?.get(1) ?: 0.0f, c?.get(2) ?: 1.0f, c?.get(3) ?: 0.0f,
                    d?.get(0) ?: 0.0f, d?.get(1) ?: 0.0f, d?.get(2) ?: 0.0f, d?.get(3) ?: 1.0f
                )
            )
        )
      } else {
        logger.error("Invalid matrix: $json")
        return@register null
      }
    })
    val matrix3: ContextType<Matrix3> = register("mat3", parser = { json ->
      if (json.isArray) {
        val a = json.get(0)?.asFloatArray()
        val b = json.get(1)?.asFloatArray()
        val c = json.get(2)?.asFloatArray()
        return@register ContextValue(
            this, Matrix3(
                floatArrayOf(
                    a?.get(0) ?: 1.0f, a?.get(1) ?: 0.0f, a?.get(2) ?: 0.0f,
                    b?.get(0) ?: 0.0f, b?.get(1) ?: 1.0f, b?.get(2) ?: 0.0f,
                    c?.get(0) ?: 0.0f, c?.get(1) ?: 0.0f, c?.get(2) ?: 1.0f
                )
            )
        )
      } else if (json.isObject) {
        val a = json.get("a")?.asFloatArray()
        val b = json.get("b")?.asFloatArray()
        val c = json.get("c")?.asFloatArray()
        return@register ContextValue(
            this, Matrix3(
                floatArrayOf(
                    a?.get(0) ?: 1.0f, a?.get(1) ?: 0.0f, a?.get(2) ?: 0.0f,
                    b?.get(0) ?: 0.0f, b?.get(1) ?: 1.0f, b?.get(2) ?: 0.0f,
                    c?.get(0) ?: 0.0f, c?.get(1) ?: 0.0f, c?.get(2) ?: 1.0f
                )
            )
        )
      } else {
        logger.error("Invalid matrix: $json")
        return@register null
      }
    })
    val int: ContextType<Int> = register("int", parser = { json -> ContextValue(this, json.asInt()) })
    val long: ContextType<Long> = register("int64", parser = { json -> ContextValue(this, json.asLong()) })
    val float: ContextType<Float> = register("float", parser = { json -> ContextValue(this, json.asFloat()) })
    val double: ContextType<Double> = register("float64", parser = { json -> ContextValue(this, json.asDouble()) })
    val string: ContextType<String> = register("string", parser = { json -> ContextValue(this, json.asString()) })
    val boolean: ContextType<Boolean> = register("boolean", parser = { json -> ContextValue(this, json.asBoolean()) })

    @OptIn(ExperimentalEncodingApi::class)
    val binary: ContextType<ByteArray> = register("binary", parser = { json ->
      val base64 = json.asString()
      try {
        return@register ContextValue(this, Base64.decode(base64))
      } catch (e: Exception) {
        logger.error("Failed to decode base64: $base64", e)
        return@register null
      }
    })
    val json: ContextType<JsonValue> = register("json", parser = { json -> ContextValue(this, json) })
    val id: ContextType<NamespaceID> = register("id", parser = { json ->
        ContextValue(
            this, json.asString()?.asIdOrNull()
                ?: run {
                    logger.error("Invalid id: ${json.asString()}")
                    return@register null
                })
    })
    val type: ContextType<ContextType<*>> = register("type", parser = { json ->
      val name = json.asString()
      val type = registry[name] ?: run {
        logger.error("Unknown type: $name")
        return@register null
      }
        ContextValue(this, type)
    })
    val componentMap: ContextType<ComponentMap> = register("component-map", parser = { json ->
      val map = mutableMapOf<ComponentType<*>, Component<*>>()
      if (!json.isArray) {
        logger.error("ComponentMap value must be an array")
        return@register null
      }
      json.forEach { value ->
        val component = ComponentType.parse(value)
        map[component.componentType] = component
      }
        ContextValue(this, map)
    })
    val componentTypeList: ContextType<ComponentTypeList> = register("component-type-list", parser = { json ->
      val list = mutableListOf<ComponentType<*>>()
      if (!json.isArray) {
        logger.error("ComponentTypeList value must be an array")
        return@register null
      }
      json.forEach { value ->
        list.add(ComponentType[value.asString()] ?: run {
          logger.error("Unknown component type: ${value.asString()}")
          return@register null
        })
      }
        ContextValue(this, list)
    })
    val component: ContextType<Component<*>> = register("component", parser = { json ->
      val name = json.asString()
      val componentType = ComponentType[name] ?: run {
        logger.error("Unknown component type: $name")
        return@register null
      }
      return@register ContextValue(this, componentType.parse(json) ?: return@register null)
    })

    val function: ContextType<VirtualFunction> = register("function", parser = { json ->
      if (!json.isObject) {
        logger.error("Function value must be an object")
        return@register null
      }
      val name = json.get("name").asString()
      val function = VirtualFunctions[name] ?: run {
        logger.error("Unknown function: $name")
        return@register null
      }
      return@register ContextValue(this, function(json.get("context")))
    })

    val core: ContextType<CoreUtils> = register("core", parser = { json ->
      return@register ContextValue(this, CoreUtils)
    })

    val math: ContextType<MathUtils> = register("math", parser = { json ->
      return@register ContextValue(this, MathUtils)
    })

    private fun <T : Any> register(
        name: String,
        contextType: Class<T>,
        parser: ContextType<T>.(JsonValue) -> ContextValue<T>,
    ): ContextType<T> {
      return ContextType(name, contextType, parser).apply {
        registry[name] = this
      }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> register(
        name: String,
        parser: ContextType<T>.(JsonValue) -> ContextValue<T>?,
        vararg typeGetter: T,
    ): ContextType<T> {
      val type = typeGetter.javaClass.componentType
      return ContextType(name, type as Class<T>, parser).apply {
        registry[name] = this
      }
    }

    operator fun get(name: String): ContextType<*>? {
      return registry[name]
    }

    operator fun get(kClass: KClass<out Any>): ContextType<*>? {
      return registry.values.firstOrNull { it.clazz == kClass.java }
    }
  }
}
