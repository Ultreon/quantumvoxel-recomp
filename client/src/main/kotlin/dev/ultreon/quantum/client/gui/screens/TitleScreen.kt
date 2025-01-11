package dev.ultreon.quantum.client.gui.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.QuantumVoxel.globalBatch
import ktx.actors.*
import ktx.scene2d.Scene2DSkin

class TitleScreen : GameScreen() {
  private val batch = globalBatch

  override fun Stage.init() {
    addActor(Button(Scene2DSkin.defaultSkin).apply button@ {
      val button = this
      val apply = Label("Quantum Voxel", Scene2DSkin.defaultSkin).apply {
        setAlignment(Align.center)
        setOrigin(Align.center)
        setPosition(50f, 10f, Align.center)
      }
      val container = Container(apply)
      addActor(container.clip().apply {
        pad(1f, 1f, 5f, 1f)
      }.apply {
        width = 100f
        height = 20f
      })
      onTouchEvent { event, x, y ->
        if (isPressed) {
          container.pad(1f, 3f, 3f, 1f)
          apply.setY(height / 2f - 2f, Align.center)
        } else {
          container.pad(1f, 1f, 5f, 1f)
          apply.setY(height / 2f, Align.center)
        }
      }
      onExitEvent { event, x, y ->
        container.pad(1f, 1f, 5f, 1f)
        apply.setY(height / 2f, Align.center)
      }

      width = 100f
      height = 20f

      onClick {
        QuantumVoxel.startWorld()
      }

      setOrigin(Align.center)
      align(Align.center)

      setPosition(screenWidth / 2f - width / 2f, screenHeight / 2f - height / 2f)
    })
  }
}
