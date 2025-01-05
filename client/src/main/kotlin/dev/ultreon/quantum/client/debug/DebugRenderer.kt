package dev.ultreon.quantum.client.debug

import com.artemis.Entity
import com.badlogic.gdx.ai.utils.Collision
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.draw
import dev.ultreon.quantum.entity.CollisionComponent
import dev.ultreon.quantum.entity.PositionComponent
import dev.ultreon.quantum.entity.RunningComponent
import ktx.graphics.use

class DebugRenderer {
  var line = 1

  fun render() {
    line = 1

    if (!QuantumVoxel.debug) {
      QuantumVoxel.spriteBatch.use {
        QuantumVoxel.font.draw(it, "Not Debug", 10f, 10f + (line++ * 10f))
      }
      return
    }

    QuantumVoxel.spriteBatch.begin()
    val player: Entity? = QuantumVoxel.player
    if (player != null) {
      val position: PositionComponent? = player.getComponent(PositionComponent::class.java)
      if (position != null) {
        left("📍", "XYZ", position.position)
        left("🔄", "X Rotation", "${position.xRot}, ${position.yRot}")
      }

      val running: RunningComponent? = player.getComponent(RunningComponent::class.java)
      if (running != null) {
        left("💨", "Running", running.running)
      }

      val collision: CollisionComponent? = player.getComponent(CollisionComponent::class.java)
      if (collision != null) {
        left("On Ground", collision.onGround)
        left("Collide XYZ", "${collision.isCollidingX}, ${collision.isCollidingY}, ${collision.isCollidingZ}")
        left("Colliding", collision.isColliding)
        left("No Clip", collision.noClip)
      }
    }
    QuantumVoxel.spriteBatch.end()
  }

  fun left(name: String, value: Any?) {
    QuantumVoxel.font.draw(QuantumVoxel.spriteBatch, "[gold]$name: [white]$value", 10f, 10f + (line++ * 10f))
  }

  fun left(emoji: String, name: String, value: Any?) {
    QuantumVoxel.font.draw(QuantumVoxel.spriteBatch, "[+$emoji][gold]$name: [white]$value", 10f, 10f + (line++ * 10f))
  }
}
