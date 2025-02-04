package dev.ultreon.quantum.blocks

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.InternalApi
import dev.ultreon.quantum.scripting.function.ContextValue
import ktx.collections.GdxArray
import ktx.collections.gdxArrayOf

class BlockState(val definition: BlockStateDefinition) {
  private val properties = gdxArrayOf<Any>()

  @Suppress("UNCHECKED_CAST")
  internal operator fun set(key: PropertyKey<*>, value: Any) {
    val index = (key as PropertyKey<Any>).indexByValue(value)
    if (index == -1) {
      throw IllegalArgumentException("No such property: $key")
    }
    properties[index] = value
  }

  operator fun <T : Any> get(key: PropertyKey<T>, type: Class<T>): T {
    val index = key.indexByValue(key.valueByIndex(0))
    return type.cast(properties[index])
  }

  inline operator fun <reified T : Any> get(key: PropertyKey<T>): T = get(key, T::class.java)

  override fun toString(): String {
    return "BlockState(definition=$definition, properties=$properties)"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is BlockState) return false
    if (definition != other.definition) return false
    return true
  }

  override fun hashCode(): Int {
    var result = definition.hashCode()
    result = 31 * result + properties.hashCode()
    return result
  }

  companion object {
    fun parse(json: JsonValue, block: ContextValue<Block>): BlockState {
      return block.value.definition.stateWith(

      )
    }
  }
}

class BlockStateDefinition(
  private val properties: GdxArray<PropertyKey<*>>,
) : Iterable<PropertyKey<*>> {
  private val states = gdxArrayOf<BlockState>()

  fun empty(): BlockState = BlockState(this)

  fun stateWith(vararg values: Pair<PropertyKey<*>, Any>): BlockState {
    val state = BlockState(this)
    for (pair in values) {
      state[pair.first] = pair.second
    }
    return state
  }

  override fun iterator(): Iterator<PropertyKey<*>> = properties.asIterable().iterator()
}

@BlockStateDsl
class StateDefinitionBuilder {
  private val properties = gdxArrayOf<PropertyKey<*>>()

  fun <T : Any> property(key: PropertyKey<T>): StateDefinitionBuilder {
    properties.add(key)
    return this
  }

  @InternalApi
  fun build(): BlockStateDefinition = BlockStateDefinition(this.properties)
}

@OptIn(InternalApi::class)
fun blockState(func: StateDefinitionBuilder.() -> Unit): BlockStateDefinition {
  val builder = StateDefinitionBuilder()
  builder.func()
  return builder.build()
}

@DslMarker
annotation class BlockStateDsl
