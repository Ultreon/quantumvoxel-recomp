package dev.ultreon.quantum.client.world

import dev.ultreon.quantum.client.LocalPlayerComponent
import dev.ultreon.quantum.entity.*
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.world.Dimension

class PlayerEntity(val dimension: Dimension, position: Vector3D) {
  fun tick() {
    collisionComponent.tick()
  }

  val positionComponent = PositionComponent(position)
  val playerComponent = LocalPlayerComponent()
  val runningComponent = RunningComponent()
  val inventoryComponent = InventoryComponent()
  val collisionComponent = CollisionComponent()

  init {
    collisionComponent.positionComponent = positionComponent
    collisionComponent.dimension = dimension
  }
}
