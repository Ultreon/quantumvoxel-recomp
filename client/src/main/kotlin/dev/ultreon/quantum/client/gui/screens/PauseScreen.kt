package dev.ultreon.quantum.client.gui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import dev.ultreon.quantum.client.QuantumVoxel
import ktx.actors.onClick
import ktx.scene2d.Scene2DSkin

class PauseScreen : GameScreen() {
  override fun Stage.init() {
    addActor(Label("Paused", Scene2DSkin.defaultSkin).apply {
      setWrap(true)
      setAlignment(Align.center)
      setOrigin(0.5f, 0.5f)
      setPosition(screenWidth / 2f - width / 2f, screenHeight / 2f - height / 2f + 40f)
    })
    addActor(Button(Scene2DSkin.defaultSkin).apply {
      addActor(Container<Label>(Label("Resume", Scene2DSkin.defaultSkin).apply {
        setWrap(true)
        setAlignment(Align.center)
        setOrigin(0.5f, 0.5f)
        setPosition(screenWidth / 2f - width / 2f, screenHeight / 2f - height / 2f)
      }))

      onClick {
        QuantumVoxel.setScreen<InGameHudScreen>()
        Gdx.input.isCursorCatched = true
      }

      setSize(150f, 20f)
      setPosition(screenWidth / 2f - width / 2f, screenHeight / 2f - height / 2f + 40f)
    })

    addActor(Button(Scene2DSkin.defaultSkin).apply {
      addActor(Container(Label("Quit", Scene2DSkin.defaultSkin).apply {
        setWrap(true)
        setAlignment(Align.center)
        setOrigin(0.5f, 0.5f)
        setPosition(screenWidth / 2f - width / 2f, screenHeight / 2f - height / 2f - 20f)
      }))

      onClick {
        QuantumVoxel.stopWorld {
          Gdx.app.exit()
        }
      }

      setSize(150f, 20f)
      setPosition(screenWidth / 2f - width / 2f, screenHeight / 2f - height / 2f - 50f)
    })
  }
}
