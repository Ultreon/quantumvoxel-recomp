package dev.ultreon.quantum.scripting.function

import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.BlockEntity
import dev.ultreon.quantum.blocks.BlockState
import dev.ultreon.quantum.entity.*
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.math.Axis
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.scripting.condition.VirtualCondition
import dev.ultreon.quantum.util.Direction
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.quantum.world.Dimension

class ContextType<T>(val name: String, val clazz: Class<T>) : ContextAware<ContextType<*>> {
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

  companion object {
    private val registry = HashMap<String, ContextType<*>>()
    val entityTemplate: ContextType<EntityTemplate> = register("entity-template", EntityTemplate::class.java)
    val entity: ContextType<Entity> = register("entity", Entity::class.java)
    val condition: ContextType<VirtualCondition> = register("condition")

    val dimension: ContextType<Dimension> = register("dimension")
    val block: ContextType<Block> = register("block")
    val blockState: ContextType<BlockState> = register("block-state")
    val blockEntity: ContextType<BlockEntity> = register("block-entity")
    val item: ContextType<Item> = register("item")
    val gridPoint: ContextType<GridPoint3> = register("grid-point")
    val vector: ContextType<Vector3D> = register("vector")
    val direction: ContextType<Direction> = register("direction")
    val axis: ContextType<Axis> = register("axis")
    val itemStack: ContextType<ItemStack> = register("item-stack")
    val matrix4: ContextType<Matrix4> = register("mat4")
    val matrix3: ContextType<Matrix4> = register("mat3")
    val int: ContextType<Int> = register("int")
    val long: ContextType<Long> = register("int64")
    val float: ContextType<Float> = register("float")
    val double: ContextType<Double> = register("float64")
    val string: ContextType<String> = register("string")
    val boolean: ContextType<Boolean> = register("boolean")
    val binary: ContextType<ByteArray> = register("binary")
    val json: ContextType<JsonValue> = register("json")
    val id: ContextType<NamespaceID> = register("id")
    val type: ContextType<ContextType<*>> = register("type")
    val componentMap: ContextType<Map<ComponentType<*>, Component<*>>> = register("component-map")
    val componentTypeList: ContextType<List<ComponentType<*>>> = register("component-type-list")
    val component: ContextType<Component<*>> = register("component")

    private fun <T> register(name: String, contextType: Class<T>): ContextType<T> {
      registry[name] = ContextType(name, contextType)
      return ContextType(name, contextType)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> register(name: String, vararg typeGetter: T): ContextType<T> {
      val type = typeGetter.javaClass.componentType
      return ContextType(name, type as Class<T>).apply {
        registry[name] = this
      }
    }

    operator fun get(name: String): ContextType<*>? {
      return registry[name]
    }
  }
}
