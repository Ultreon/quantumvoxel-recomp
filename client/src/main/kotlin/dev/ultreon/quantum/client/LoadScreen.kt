package dev.ultreon.quantum.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import dev.ultreon.quantum.client.gui.screens.*
import dev.ultreon.quantum.client.input.ControllerMovement
import dev.ultreon.quantum.client.input.KeyMovement
import dev.ultreon.quantum.client.input.TouchMovement
import dev.ultreon.quantum.client.model.ModelRegistry
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.util.id
import ktx.graphics.use
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.defaultStyle
import ktx.style.addStyle
import ktx.style.skin
import kotlin.concurrent.thread

class LoadScreen : GameScreen() {
  private var loaded: Boolean = false
  private var crash: Array<StackTraceElement>? = null
  private val batch: SpriteBatch = quantum.globalBatch
  private var t: Thread? = null
  private var message = ""

  override fun show() {
    if (t != null) {
      logger.warn("LoadScreen was already initialized!")
      return
    }
    t = thread {
      try {
        message = "Loading textures..."
        textureManager.init()
        message = "Loading block textures..."
        textureManager.registerAtlas("block")
        message = "Loading item textures..."
        textureManager.registerAtlas("font")
        message = "Loading GUI textures..."
        textureManager.registerAtlas("gui")

        message = "Packing textures..."
        textureManager.pack()

        message = "Loading models..."
        ModelRegistry.loadModels()

        message = "Loading skin..."
        Scene2DSkin.defaultSkin = skin {
          addStyle(
            defaultStyle, Button.ButtonStyle(
              NinePatchDrawable(NinePatch(textureManager[id(path = "textures/gui/buttons/dark.png")], 7, 7, 7, 7)),
              NinePatchDrawable(
                NinePatch(
                  textureManager[id(path = "textures/gui/buttons/dark_pressed.png")],
                  7,
                  7,
                  7,
                  7
                )
              ),
              null
            )
          )
          addStyle(defaultStyle, Label.LabelStyle(quantum.bitmapFont, Color.WHITE))
          addStyle(
            defaultStyle,
            Touchpad.TouchpadStyle(
              TextureRegionDrawable(textureManager[id(path = "textures/gui/touchpad.png")]),
              TextureRegionDrawable(textureManager[id(path = "textures/gui/touchpad_knob.png")])
            )
          )
        }

        message = "Initializing..."
        QuantumVoxel.await {
          quantum.touchpad = Touchpad(0.1f, Scene2DSkin.defaultSkin).also {
            it.setPosition(10f, 10f)
            it.setSize(1f, 1f)
          }

          quantum.keyMovement = KeyMovement()
          quantum.touchMovement = TouchMovement(quantum.touchpad)
          quantum.controllerMovement = ControllerMovement()

          quantum.addScreen(SplashScreen())
          quantum.addScreen(TitleScreen())
          quantum.addScreen(PlaceholderScreen())
          quantum.addScreen(PauseScreen())
          quantum.setScreen<TitleScreen>()

          message = "Done!"

          this.loaded = true
        }
      } catch (e: Exception) {
        e.printStackTrace()
        crash = e.stackTrace
      }
    }

    super.show()
  }

  override fun render(delta: Float) {
    if (crash != null) {
      globalBatch.use {
        var y = 0
        for (stackTraceElement in crash!!) {
          y += quantum.bitmapFont.lineHeight.toInt()
          val fileName = stackTraceElement.fileName
          if (fileName != null) {
            quantum.bitmapFont.draw(
              batch,
              stackTraceElement.className.replace(
                "/",
                "."
              ) + "." + stackTraceElement.methodName + " (" + fileName.substring(
                fileName.lastIndexOf("/") + 1
              ) + ":" + stackTraceElement.lineNumber + ")",
              Gdx.graphics.width.toFloat(),
              Gdx.graphics.height / 2f - y.toFloat()
            )
          } else {
            quantum.bitmapFont.draw(
              batch,
              stackTraceElement.className.replace(
                "/",
                "."
              ) + "." + stackTraceElement.methodName + " (<Unknown File>:" + stackTraceElement.lineNumber + ")",
              Gdx.graphics.width.toFloat(),
              Gdx.graphics.height / 2f - y.toFloat()
            )
          }
        }
      }
    }

    if (t?.isAlive != false) {
      globalBatch.use {
        quantum.bitmapFont.draw(batch, "Loading...", Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)
        quantum.bitmapFont.draw(batch, message, Gdx.graphics.width / 2f, Gdx.graphics.height / 2f - 20f)
      }
    }
  }
}
