package dev.ultreon.quantum.client.gui.widget

import dev.ultreon.quantum.client.gui.screens.Screen

interface WidgetPositioning {
  fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float>

  companion object {
    fun absolute(absX: Float, absY: Float): WidgetPositioning {
      return object : WidgetPositioning {
        override fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float> {
          return absX to absY
        }
      }
    }

    fun relativeToWidget(widget: Widget, relativeX: Float, relativeY: Float): WidgetPositioning {
      return object : WidgetPositioning {
        override fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float> {
          return (widget.x ?: 0F) + relativeX to (widget.y ?: 0F) + relativeY
        }
      }
    }

    fun relativeToParent(relativeX: Float, relativeY: Float): WidgetPositioning {
      return object : WidgetPositioning {
        override fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float> {
          return x + relativeX to y + relativeY
        }
      }
    }

    fun relativeToScreen(relativeX: Float, relativeY: Float): WidgetPositioning {
      return object : WidgetPositioning {
        override fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float> {
          return screen.x + relativeX to screen.y + relativeY
        }
      }
    }

    fun relativeToScreenTop(relativeX: Float, relativeY: Float): WidgetPositioning {
      return object : WidgetPositioning {
        override fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float> {
          return screen.x + screen.width / 2 + relativeX to screen.y + relativeY
        }
      }
    }

    fun relativeToScreenBottom(relativeX: Float, relativeY: Float): WidgetPositioning {
      return object : WidgetPositioning {
        override fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float> {
          return screen.x + screen.width / 2 + relativeX to screen.y + screen.height + relativeY
        }
      }
    }

    fun relativeToScreenLeft(relativeX: Float, relativeY: Float): WidgetPositioning {
      return object : WidgetPositioning {
        override fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float> {
          return screen.x + relativeX to screen.y + screen.height / 2 + relativeY
        }
      }
    }

    fun relativeToScreenRight(relativeX: Float, relativeY: Float): WidgetPositioning {
      return object : WidgetPositioning {
        override fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float> {
          return screen.x + screen.width + relativeX to screen.y + screen.height / 2 + relativeY
        }
      }
    }

    fun relativeToScreenCenter(relativeX: Float, relativeY: Float): WidgetPositioning {
      return object : WidgetPositioning {
        override fun getPosition(screen: Screen, x: Float, y: Float, width: Float, height: Float): Pair<Float, Float> {
          return screen.x + screen.width / 2 + relativeX to screen.y + screen.height / 2 + relativeY
        }
      }
    }
  }
}
