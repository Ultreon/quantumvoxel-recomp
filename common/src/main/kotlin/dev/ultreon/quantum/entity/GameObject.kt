package dev.ultreon.quantum.entity

import dev.ultreon.quantum.scripting.function.CallContext

open class GameObject(val name: String) {
  private val components: MutableMap<ComponentType<*>, Component<*>> = mutableMapOf()

  fun <T : Component<T>> addComponent(componentType: ComponentType<T>, callContext: CallContext): T {
    val component = componentType(callContext)
    components[componentType] = component
    return component
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : Component<T>> getComponent(componentType: ComponentType<T>): T? {
    return components[componentType] as T?
  }

  fun removeComponent(componentType: ComponentType<*>) {
    components.remove(componentType)
  }

  fun getComponents(): Map<ComponentType<*>, Component<*>> {
    return components
  }

  operator fun contains(componentType: ComponentType<*>): Boolean {
    return components.containsKey(componentType)
  }
}
