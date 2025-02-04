package dev.ultreon.quantum.client.debug

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import dev.ultreon.quantum.client.draw
import dev.ultreon.quantum.client.quantum
import dev.ultreon.quantum.client.world.LocalPlayer
import dev.ultreon.quantum.client.world.allLoading
import dev.ultreon.quantum.entity.PhysicsComponent
import dev.ultreon.quantum.entity.PositionComponent
import dev.ultreon.quantum.entity.RunningComponent
import ktx.graphics.use
import java.util.*

private val runtime: Runtime = Runtime.getRuntime()

class DebugRenderer {
  var line = 1

  fun render() {
    line = 1

    if (!quantum.debug) {
      return
    }

    quantum.globalBatch.use {
      val player: LocalPlayer? = quantum.player
      if (player != null) {
        val position: PositionComponent? = player.positionComponent
        if (position != null) {
          left("📍", "XYZ", position.position)
          left("🔄", "X Rotation", "${position.xRot}, ${position.yRot}")
        }

        val running: RunningComponent? = player.runningComponent
        if (running != null) {
          left("💨", "Running", running.running)
        }

        val collision: PhysicsComponent? = player.physicsComponent
        if (collision != null) {
          left("🌊", "On Ground", collision.onGround)
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
      left(
        "💾",
        "Total Memory",
        "${String.format(Locale.getDefault(), "%.2f", runtime.totalMemory() / 1024.0 / 1024.0)} MB"
      )
      left("🕒", "FPS", Gdx.graphics.framesPerSecond)

      left("📦", "Mesh Status", Mesh.getManagedStatus())
      left("📦", "Shader Status", ShaderProgram.getManagedStatus())
      left("📦", "Texture Status", Texture.getManagedStatus())
      left("📦", "Texture 3D Status", Texture3D.getManagedStatus())
      left("📦", "Texture Array Status", TextureArray.getManagedStatus())
      left("📦", "Framebuffer Status", GLFrameBuffer.getManagedStatus())
      left("📦", "Cubemap Status", Cubemap.getManagedStatus())

      left("📥", "Loading chunks count", allLoading)

      // Input
      left("🖱️", "Mouse X", Gdx.input.x)
      left("🖱️", "Mouse Y", Gdx.input.y)
      left("🖱️", "Input Processor", Gdx.input.inputProcessor?.javaClass?.simpleName ?: "None")
      left("🖱️", "Cursor Catched", Gdx.input.isCursorCatched)
      left("🖱️", "Mouse Delta X", Gdx.input.deltaX)
      left("🖱️", "Mouse Delta Y", Gdx.input.deltaY)
      for (i in 0 until Gdx.input.maxPointers) {
        if (!Gdx.input.isTouched(i)) continue
        left("🖱️", "Pointer $i", Gdx.input.isTouched(i))
        left("🖱️", "Pointer $i Delta X", Gdx.input.getDeltaX(i))
        left("🖱️", "Pointer $i Delta Y", Gdx.input.getDeltaY(i))
      }
    }
  }

  fun left(name: String, value: Any?) {
    quantum.font.draw(quantum.globalBatch, "[gold]$name: [white]$value", 10f, 10f + (line++ * 10f))
  }

  fun left(emoji: String, name: String, value: Any?) {
    quantum.font.draw(quantum.globalBatch, "[+$emoji][gold]$name: [white]$value", 10f, 10f + (line++ * 10f))
  }
}
