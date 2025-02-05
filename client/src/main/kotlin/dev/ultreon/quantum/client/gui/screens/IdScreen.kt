package dev.ultreon.quantum.client.gui.screens

import dev.ultreon.quantum.client.globalBatch
import dev.ultreon.quantum.client.quantum
import dev.ultreon.quantum.client.scripting.ClientContextTypes
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.resource.*
import dev.ultreon.quantum.scripting.condition.VirtualCondition
import dev.ultreon.quantum.scripting.function.CallContext
import dev.ultreon.quantum.scripting.function.ContextType
import dev.ultreon.quantum.scripting.function.ContextValue
import dev.ultreon.quantum.util.NamespaceID
import kotlin.reflect.KProperty

class IdScreen(screenId: NamespaceID) : Screen() {
  private val batch = globalBatch

  val screenId = screenId.mapPath { path ->
    return@mapPath path.substringAfter("gui/screens/").substringBeforeLast('.')
  }

  init {
    this.id = screenId.toString()

    run {
      val resource: Resource = quantum.clientResources[screenId] ?: return@run
      val json = resource.json()
      val titleVar = json["title"]

      if (titleVar.isString || titleVar.isNull || titleVar.isNumber) {
        this.title = titleVar.asString()
      } else if (titleVar.isObject) {
        // TODO
        this.title = titleVar.toString()
      } else {
        this.title = titleVar.toString()
      }

      val widgetsVal = json["widgets"]
      if (widgetsVal.isArray) {
        for ((i, widget) in widgetsVal.withIndex()) run main@ {
          if (widget.isObject) {
            var visible = true
            val conditions = widget["if"]
            if (conditions?.isArray == true) {
              for (condition in conditions) {
                if (condition.isBoolean) {
                  visible = condition.asBoolean()
                } else if (condition.isString) {
                  val conditionString = condition.asString()
                  if (conditionString.matches("@\\(.*\\)".toRegex())) {
                    val conditionName = conditionString.substringAfter("@(").substringBeforeLast(")")
                    logger.error("Condition variables not implemented in screen ($screenId).${condition.trace()}")
                    // TODO
                  } else if (conditionString == "false") {
                    visible = false
                  }
                } else if (condition.isObject) {
                  val type = condition["type"]?.asString() ?: run {
                    logger.error("Condition type must be a string in screen ($screenId).${condition.trace()}")
                    return@main
                  }
                  val virtualCondition = VirtualCondition[type] ?: run {
                    logger.error("Unknown condition type: $type in screen ($screenId).${condition["type"].trace()}")
                    return@main
                  }
                  virtualCondition.test(CallContext.of(
                    ContextValue(ClientContextTypes.screen, this),
                    ContextValue(ClientContextTypes.batch, batch),
                  ) ?: return@main).let { visible = visible && it }
                } else {
                  logger.error("Condition must be a boolean, string or object in screen ($screenId).${condition.trace()}")
                }
              }
            }
            val widgetType = widget["type"]
            if (widgetType.isString) {
              val type = widgetType.asString()
              when (type) {
                "button" -> {
                  val button = TextButton(this, widget)
                  this.add(button)
                }
                "text" -> {
                  val text = Text(this, widget)
                  this.add(text)
                }
                else -> {
                  logger.error("Unknown widget type: $type in ($screenId).widgets.$i")
                }
              }
            } else {
              logger.error("Widget type must be a string in ($screenId).widgets.$i")
            }
          } else {
            logger.error("Widget must be an object in ($screenId).widgets.$i")
          }
        }
      } else {
        logger.error("Widgets must be an array in ($screenId)")
      }
    }
  }

  override fun setup() {

  }

  companion object {
    private val screens = HashMap<NamespaceID, IdScreen>()

    fun add(screen: IdScreen) {
      screens[screen.screenId] = screen
    }

    fun get(screenId: NamespaceID): IdScreen? {
      return screens[screenId]
    }

    fun load(clientResources: ResourceManager) {
      clientResources["gui"]?.asDirOrNull()?.let { dir ->
        dir["screens"]?.asDirOrNull()?.asDirectoryOrNull()?.walk { resource ->
          val idScreen = IdScreen(resource.location)
          add(idScreen)
        } ?: run {
          logger.warn("No GUI screens found in resources.")
        }
      } ?: run {
        logger.warn("No GUI elements found in resources.")
      }
    }
  }
}

class ScreenProperty(val screenId: NamespaceID) {
  operator fun getValue(thisRef: Any?, property: KProperty<*>): Screen {
    return IdScreen.get(screenId) ?: throw IllegalStateException("Screen not found: $screenId")
  }
}

fun screen(screenId: NamespaceID) = ScreenProperty(screenId)

fun screen(namespace: String = "quantum", path: String) = screen(NamespaceID(namespace, path))
