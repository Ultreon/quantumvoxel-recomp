package dev.ultreon.quantum.entity

import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.GameContent
import dev.ultreon.quantum.blocks.key
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.scripting.function.ContextAware
import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.registry.RegistryKeys
import dev.ultreon.quantum.scripting.function.ContextType
import dev.ultreon.quantum.scripting.function.ContextValue
import dev.ultreon.quantum.util.id
import dev.ultreon.quantum.world.Dimension

class EntityTemplate(val componentTypes: List<ComponentType<*>>) :
  GameContent<EntityTemplate>(RegistryKeys.entityTypes), ContextAware<EntityTemplate> {

  override fun contextType(): ContextType<EntityTemplate> = ContextType.entityTemplate
  override fun fieldOf(name: String, contextJson: JsonValue?): ContextValue<*>? {
    return when (name) {
      "id" -> Registries.entityTemplates[this]?.let { ContextValue(ContextType.id, it) }
      "components" -> ContextValue(ContextType.componentTypeList, componentTypes)
      else -> null
    }
  }

  override fun toString(): String = "EntityType($id)"

  fun createEntity(dimension: Dimension): Entity {
    val entity = Entity(this, dimension)
    entity.id = dimension.entityManager.createEntity(entity)
    return entity
  }

  companion object {
    val player by key(Registries.entityTemplates, id(path = "player"))
  }
}

class Entity(val template: EntityTemplate, var dimension: Dimension) : ContextAware<Entity>, GameObject("Entity [${template.id}]") {
  var id: Int = -1
  var x: Double = 0.0
  var y: Double = 0.0
  var z: Double = 0.0

  fun edit(action: Entity.() -> Unit) {
    action(this)
  }

  override fun contextType(): ContextType<Entity> {
    return ContextType.entity
  }

  override fun supportedTypes(): List<ContextType<*>> {
    return listOf(ContextType.entity)
  }

  override fun fieldOf(name: String, contextJson: JsonValue?): ContextValue<*>? {
    return when (name) {
      "entity" -> ContextValue(contextType(), this)
      "template" -> ContextValue(ContextType.entityTemplate, template)
      "dimension" -> ContextValue(ContextType.dimension, dimension)
      "entity-id" -> ContextValue(ContextType.int, id)
      "x" -> ContextValue(ContextType.double, x)
      "y" -> ContextValue(ContextType.double, y)
      "z" -> ContextValue(ContextType.double, z)
      "position" -> ContextValue(ContextType.vector, Vector3D(x, y, z))
      "block-position" -> ContextValue(ContextType.gridPoint, GridPoint3(x.toInt(), y.toInt(), z.toInt()))
      "components" -> ContextValue(ContextType.componentMap, getComponents())
      "component" -> {
        val type = contextJson?.get("type")?.asString() ?: return null
        template.componentTypes.firstOrNull { it.name == type }?.let {
          ContextValue(ContextType.component, getComponent(it) as Component<*>)
        }
      }
      else -> null
    }
  }

  override fun toString(): String {
    return "Entity($id)"
  }
}
