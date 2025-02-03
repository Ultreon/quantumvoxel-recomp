package dev.ultreon.quantum.entity

import dev.ultreon.quantum.GameContent
import dev.ultreon.quantum.func.ContextAware
import dev.ultreon.quantum.func.ContextParam
import dev.ultreon.quantum.registry.RegistryKeys
import dev.ultreon.quantum.world.Dimension

class EntityTemplate(val componentTypes: List<ComponentType<Component>>) :
  GameContent<EntityTemplate>(RegistryKeys.entityTypes), ContextAware {
  @Suppress("UNCHECKED_CAST")
  override fun <T> getContextParam(key: ContextParam<T>): T? = when (key) {
    ContextParam.ENTITY_TYPE -> this as T
    else -> null
  }

  override fun selfParam(): ContextParam<*> = ContextParam.ENTITY_TYPE
  override fun toString(): String = "EntityType($id)"

  fun createEntity(dimension: Dimension): Entity {
    val entity = Entity(this, dimension)
    entity.id = dimension.entityManager.createEntity(entity)
    return entity
  }
}

class Entity(val type: EntityTemplate, var dimension: Dimension) : ContextAware {
  private val components: MutableMap<String, Component> = mutableMapOf()
  var id: Int = -1
  var x: Double = 0.0
  var y: Double = 0.0
  var z: Double = 0.0

  override fun <T> getContextParam(key: ContextParam<T>): T? {
    @Suppress("UNCHECKED_CAST")
    return when (key) {
      ContextParam.ENTITY -> this as T
      ContextParam.ENTITY_TYPE -> type as T
      ContextParam.DIMENSION -> dimension as T
      ContextParam.X -> x as T
      ContextParam.Y -> y as T
      ContextParam.Z -> z as T
      else -> null
    }
  }

  override fun selfParam(): ContextParam<*> {
    return ContextParam.ENTITY
  }

  override fun supportedParams(): List<ContextParam<*>> {
    return listOf(
      ContextParam.ENTITY,
      ContextParam.ENTITY_TYPE,
      ContextParam.DIMENSION,
      ContextParam.X,
      ContextParam.Y,
      ContextParam.Z
    )
  }

  override fun toString(): String {
    return "Entity($id)"
  }
}
