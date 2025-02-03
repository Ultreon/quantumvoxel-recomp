package dev.ultreon.quantum.func

import com.badlogic.gdx.math.GridPoint3
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.BlockEntity
import dev.ultreon.quantum.blocks.BlockState
import dev.ultreon.quantum.entity.Entity
import dev.ultreon.quantum.entity.EntityTemplate
import dev.ultreon.quantum.entity.ItemStack
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.math.Axis
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.util.Direction
import dev.ultreon.quantum.world.Dimension

class ContextParam<T>(val name: String, val clazz: Class<T>) {
  fun of(contextAware: ContextAware) {
    val contextParam = contextAware.getContextParam(this)
    if (contextParam != null) {
      throw IllegalStateException("Context param $this is already set.")
    }
  }

  fun cast(dimension: Any): T {
    return clazz.cast(dimension)
  }

  companion object {
    private val registry = HashMap<String, ContextParam<*>>()

    val ENTITY_TYPE: ContextParam<EntityTemplate> = register(ContextParam("entity-type", EntityTemplate::class.java))
    val ENTITY: ContextParam<Entity> = register(ContextParam("entity", Entity::class.java))
    val DIMENSION: ContextParam<Dimension> = register(ContextParam("dimension", Dimension::class.java))
    val BLOCK: ContextParam<Block> = register(ContextParam("block", Block::class.java))
    val BLOCK_STATE: ContextParam<BlockState> = register(ContextParam("block-state", BlockState::class.java))
    val BLOCK_ENTITY: ContextParam<BlockEntity> = register(ContextParam("block-entity", BlockEntity::class.java))
    val ITEM: ContextParam<Item> = register(ContextParam("item", Item::class.java))
    val GRID_POS: ContextParam<GridPoint3> = register(ContextParam("grid-pos", GridPoint3::class.java))
    val POS: ContextParam<Vector3D> = register(ContextParam("pos", Vector3D::class.java))
    val DIRECTION: ContextParam<Direction> = register(ContextParam("direction", Direction::class.java))
    val FACING: ContextParam<Direction> = register(ContextParam("facing", Direction::class.java))
    val FACE: ContextParam<Direction> = register(ContextParam("face", Direction::class.java))
    val AXIS: ContextParam<Axis> = register(ContextParam("axis", Axis::class.java))
    val VELOCITY: ContextParam<Vector3D> = register(ContextParam("velocity", Vector3D::class.java))
    val LOOK_VEC: ContextParam<Vector3D> = register(ContextParam("look-vec", Vector3D::class.java))
    val LOOK_DIR: ContextParam<Direction> = register(ContextParam("look-dir", Direction::class.java))
    val POWERED: ContextParam<Boolean> = register(ContextParam("powered", Boolean::class.java))
    val ITEM_STACK: ContextParam<ItemStack> = register(ContextParam("item-stack", ItemStack::class.java))
    val COUNT: ContextParam<Int> = register(ContextParam("count", Int::class.java))
    val X: ContextParam<Double> = register(ContextParam("x", Double::class.java))
    val Y: ContextParam<Double> = register(ContextParam("y", Double::class.java))
    val Z: ContextParam<Double> = register(ContextParam("z", Double::class.java))

    private fun <T> register(contextParam: ContextParam<T>): ContextParam<T> {
      registry[contextParam.name] = contextParam
      return contextParam
    }

    operator fun get(name: String): ContextParam<*>? {
      return registry[name]
    }
  }
}
