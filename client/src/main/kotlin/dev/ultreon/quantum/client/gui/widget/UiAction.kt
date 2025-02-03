package dev.ultreon.quantum.client.gui.widget

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.client.gui.screens.IdScreen
import dev.ultreon.quantum.client.gui.screens.PlaceholderScreen
import dev.ultreon.quantum.client.quantum
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.util.asIdOrNull
import dev.ultreon.quantum.util.id

object UiAction {
  fun of(json: JsonValue): (Widget) -> Unit {
    when (val type = json["type"].asString()) {
      "change-screen" -> run `change-screen`@ {
        val screenId = json["screen"].asString().asIdOrNull() ?: run {
          logger.error("Invalid screen: ${json["screen"].asString()}")
          return@`change-screen`
        }

        val screen = IdScreen.get(screenId) ?: run {
          logger.error("Screen not found: $screenId")
          return@`change-screen`
        }

        return {
          quantum.showScreen(screen)
        }
      }

      "enter-dev-world" -> {
        return {
          logger.info("Starting dev world")
          quantum.startWorld()
        }
      }

      "close-screen" -> {
        return {
          quantum.showScreen(if (quantum.dimension != null) {
            PlaceholderScreen
          } else {
            IdScreen.get(id(path = "title")) ?: run {
              logger.error("Title screen not found")
              PlaceholderScreen
            }
          })
        }
      }

      else -> {
        logger.error("Unknown action type: $type")
      }
    }

    return {}
  }
}
