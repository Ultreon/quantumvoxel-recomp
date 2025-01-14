package dev.ultreon.quantum.client.debug

import com.artemis.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Cubemap
import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture3D
import com.badlogic.gdx.graphics.TextureArray
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.draw
import dev.ultreon.quantum.client.world.allLoading
import dev.ultreon.quantum.entity.CollisionComponent
import dev.ultreon.quantum.entity.PositionComponent
import dev.ultreon.quantum.entity.RunningComponent
import java.util.*

private val runtime: Runtime = Runtime.getRuntime()

class DebugRenderer {
  var line = 1

  fun render() {
    line = 1

    if (!QuantumVoxel.debug) {
      return
    }

    QuantumVoxel.globalBatch.begin()
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
        left("🌊","On Ground", collision.onGround)
        left("👉", "Collide XYZ", "${collision.isCollidingX}, ${collision.isCollidingY}, ${collision.isCollidingZ}")
        left("👉", "Colliding", collision.isColliding)
        left("👻", "No Clip", collision.noClip)
      }
    } else {
      left("👻", "No Player", null)
    }

    val memory = runtime.totalMemory() - runtime.freeMemory()
    val mb = memory / 1024.0 / 1024.0
    left("💾", "Used Memory", "${String.format(Locale.getDefault(), "%.2f", mb)} MB")
    left("💾", "Total Memory", "${String.format(Locale.getDefault(), "%.2f", runtime.totalMemory() / 1024.0 / 1024.0)} MB")
    left("🕒", "FPS", Gdx.graphics.framesPerSecond)

    left("📦", "Mesh Status", Mesh.getManagedStatus())
    left("📦", "Shader Status", ShaderProgram.getManagedStatus())
    left("📦", "Texture Status", Texture.getManagedStatus())
    left("📦", "Texture 3D Status", Texture3D.getManagedStatus())
    left("📦", "Texture Array Status", TextureArray.getManagedStatus())
    left("📦", "Framebuffer Status", GLFrameBuffer.getManagedStatus())
    left("📦", "Cubemap Status", Cubemap.getManagedStatus())

    left("📥", "Loading chunks count", allLoading)
    QuantumVoxel.globalBatch.end()
  }

  fun left(name: String, value: Any?) {
    QuantumVoxel.font.draw(QuantumVoxel.globalBatch, "[gold]$name: [white]$value", 10f, 10f + (line++ * 10f))
  }

  fun left(emoji: String, name: String, value: Any?) {
    QuantumVoxel.font.draw(QuantumVoxel.globalBatch, "[+$emoji][gold]$name: [white]$value", 10f, 10f + (line++ * 10f))
  }
}
