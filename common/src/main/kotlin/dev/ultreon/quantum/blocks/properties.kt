package dev.ultreon.quantum.blocks

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.math.Axis
import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.util.Direction
import dev.ultreon.quantum.util.NamespaceID
import ktx.collections.GdxArray
import ktx.collections.gdxArrayOf
import ktx.collections.toGdxArray

abstract class PropertyKey<T : Any>(
  var name: String,
  var values: GdxArray<T>,
) {
  fun indexByValue(v: T): Int = values.indexOf(v)
  fun valueByIndex(i: Int): T = values[i]
  abstract fun read(it: JsonValue): T
  abstract fun read(it: String): T
}

class EnumPropertyKey<T : Enum<T>>(name: String, values: GdxArray<T>, val type: Class<T>) :
  PropertyKey<T>(name, values) {
  override fun toString(): String {
    return "EnumPropertyKey(name=$name, values=$values)"
  }

  override fun read(it: JsonValue): T {
    if (!it.isString) throw IllegalArgumentException("Expected a string value, but got '$it'")
    return type.enumConstants.first { enum -> it.asString() == enum.name.lowercase() }
  }

  override fun read(it: String): T {
    return type.enumConstants.first { enum -> it == enum.name.lowercase() }
  }
}

inline fun <reified T : Enum<T>> enumPropertyOf(name: String, values: GdxArray<T>) =
  EnumPropertyKey(name, values, T::class.java)

class IntPropertyKey(name: String, val min: Int, val max: Int) : PropertyKey<Int>(name, (min..max).toGdxArray()) {
  override fun toString(): String {
    return "IntPropertyKey(name=$name, min=$min, max=$max)"
  }

  override fun read(it: JsonValue): Int {
    if (!it.isNumber) throw IllegalArgumentException("Expected an integer value, but got '$it'")
    return it.asInt().coerceIn(min, max)
  }

  override fun read(it: String): Int {
    return it.toIntOrNull()?.coerceIn(min, max)
      ?: throw IllegalArgumentException("Expected an integer value, but got '$it'")
  }
}

fun intPropertyOf(name: String, min: Int, max: Int) = IntPropertyKey(name, min, max)

class BoolPropertyKey(name: String) : PropertyKey<Boolean>(name, gdxArrayOf(true, false)) {
  override fun toString(): String {
    return "BoolPropertyKey(name=$name)"
  }

  override fun read(it: JsonValue): Boolean {
    if (!it.isBoolean) throw IllegalArgumentException("Expected a boolean value, but got '$it'")
    return it.asBoolean()
  }

  override fun read(it: String): Boolean {
    return it.toBooleanStrictOrNull() ?: throw IllegalArgumentException("Expected a boolean value, but got '$it'")
  }
}

fun boolPropertyOf(name: String) = BoolPropertyKey(name)

enum class BlockHalf {
  BOTTOM,
  TOP,
  FULL
}

object PropertyKeys {
  val facing = enumPropertyOf(
    "facing", gdxArrayOf(
      Direction.NORTH,
      Direction.EAST,
      Direction.SOUTH,
      Direction.WEST
    )
  )

  val direction = enumPropertyOf(
    "direction", gdxArrayOf(
      Direction.UP,
      Direction.DOWN,
      Direction.NORTH,
      Direction.SOUTH,
      Direction.WEST,
      Direction.EAST
    )
  )

  val axis = enumPropertyOf(
    "axis", gdxArrayOf(
      Axis.X,
      Axis.Y,
      Axis.Z
    )
  )

  val powered = boolPropertyOf("powered")
  val open = boolPropertyOf("open")
  val attached = boolPropertyOf("attached")
  val waterlogged = boolPropertyOf("waterlogged")
  val half = enumPropertyOf(
    "half", gdxArrayOf(
      BlockHalf.BOTTOM,
      BlockHalf.TOP,
      BlockHalf.FULL
    )
  )

  fun init() {
    Registries.stateProperties.register(NamespaceID.parse(facing.name), facing)
    Registries.stateProperties.register(NamespaceID.parse(direction.name), direction)
    Registries.stateProperties.register(NamespaceID.parse(axis.name), axis)
    Registries.stateProperties.register(NamespaceID.parse(powered.name), powered)
    Registries.stateProperties.register(NamespaceID.parse(open.name), open)
    Registries.stateProperties.register(NamespaceID.parse(attached.name), attached)
    Registries.stateProperties.register(NamespaceID.parse(waterlogged.name), waterlogged)
    Registries.stateProperties.register(NamespaceID.parse(half.name), half)

    Registries.stateProperties.freeze()
  }
}
