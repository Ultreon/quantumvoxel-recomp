package dev.ultreon.quantum.entity

import dev.ultreon.quantum.scripting.function.CallContext
import dev.ultreon.quantum.scripting.function.ContextParam
import dev.ultreon.quantum.server.player.ServerPlayerComponent

class ComponentType<T : Component<T>>(
  val name: String,
  vararg val params: ContextParam<*>,
  val constructor: (context: CallContext) -> T
) : Comparable<ComponentType<*>> {
  operator fun invoke(context: CallContext): T {
    return constructor(context)
  }

  override fun toString(): String {
    return name
  }

  override fun compareTo(other: ComponentType<*>): Int {
    return name.compareTo(other.name)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    other as ComponentType<*>
    return name == other.name
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }

  companion object {
    val running: ComponentType<RunningComponent> = ComponentType("running") { RunningComponent() }
    val collision: ComponentType<PhysicsComponent> = ComponentType("physics") { PhysicsComponent() }
    val position: ComponentType<PositionComponent> = ComponentType("position") { PositionComponent() }
    val velocity: ComponentType<VelocityComponent> = ComponentType("velocity") { VelocityComponent() }
    val physics: ComponentType<PhysicsComponent> = ComponentType("physics") { PhysicsComponent() }
    val rendering: ComponentType<RenderingComponent> = ComponentType("rendering") { RenderingComponent() }
    val animation: ComponentType<AnimationComponent> = ComponentType("animation") { AnimationComponent() }
    val network: ComponentType<NetworkComponent> = ComponentType("network") { NetworkComponent() }
    val inventory: ComponentType<InventoryComponent> = ComponentType("inventory") { InventoryComponent() }
    val serverPlayer: ComponentType<ServerPlayerComponent> = ComponentType("player", ContextParam.NAME) { ServerPlayerComponent(it.getString("name") ?: throw IllegalStateException("Missing name param")) }
  }
}
