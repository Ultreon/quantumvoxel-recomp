package dev.ultreon.quantum.client.gui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import dev.ultreon.quantum.client.quantum
import ktx.actors.onClick
import ktx.scene2d.*

class PauseScreen : GameScreen() {
  override fun Stage.init() {
    actors {
      label("Paused") {
        setAlignment(Align.center)
        setOrigin(0.5f, 0.5f)
        setPosition(screenWidth / 2f - width / 2f, screenHeight / 2f - height / 2f)
      }

      button {
        container {
          label("Resume") {
            setAlignment(Align.center)
            setOrigin(0.5f, 0.5f)
          }
        }

        onClick {
          quantum.setScreen<PlaceholderScreen>()
          Gdx.input.isCursorCatched = true
        }

        setPosition(screenWidth / 2f - width / 2f, screenHeight / 2f - height / 2f)
        setSize(150f, 20f)
      }

      button {
        container {
          label("Main Menu") {
            setAlignment(Align.center)
            setOrigin(0.5f, 0.5f)
          }
        }

        onClick {
          quantum.stopWorld {
            quantum.setScreen<TitleScreen>()
            Gdx.input.isCursorCatched = false
          }
        }

        setPosition(screenWidth / 2f - width / 2f, screenHeight / 2f - height / 2f - 40f)
        setSize(150f, 20f)
      }
    }
  }
}
