package dev.ultreon.quantum.entity

import com.badlogic.gdx.utils.JsonValue

abstract class Component<T : Component<T>> : Comparable<Component<*>> {
  abstract val componentType: ComponentType<out T>

  override fun toString(): String {
    return "Component(${componentType.name})"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    other as Component<*>
    return componentType == other.componentType
  }

  override fun compareTo(other: Component<*>): Int {
    return componentType.compareTo(other.componentType)
  }

  override fun hashCode(): Int {
    return componentType.hashCode()
  }

  abstract fun json(): JsonValue
  abstract fun load(json: JsonValue)
}
