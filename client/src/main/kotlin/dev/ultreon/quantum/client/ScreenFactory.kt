package dev.ultreon.quantum.client

import com.badlogic.gdx.Screen

class ScreenImpl(private val screenFactory: ScreenFactory) : Screen {
  override fun show() {
    screenFactory.onShow()
  }
  override fun render(delta: Float) {
    screenFactory.onRender(delta)
  }
  override fun resize(width: Int, height: Int) {
    screenFactory.onResize(width, height)
  }
  override fun pause() {
    screenFactory.onPause()
  }
  override fun resume() {
    screenFactory.onResume()
  }

  override fun hide() {
    screenFactory.onHide()
  }

  override fun dispose() {
    screenFactory.onDispose()
  }
}

class ScreenFactory {
  private var screen: ScreenImpl? = ScreenImpl(this)

  @JvmField var onShow: () -> Unit = {}
  @JvmField var onRender: (delta: Float) -> Unit = {}
  @JvmField var onResize: (width: Int, height: Int) -> Unit = { _, _ -> }
  @JvmField var onPause: () -> Unit = {}
  @JvmField var onResume: () -> Unit = {}
  @JvmField var onHide: () -> Unit = {}
  @JvmField var onDispose: () -> Unit = {}

  companion object {
    @JvmStatic
    fun newScreen(): ScreenFactory = ScreenFactory()
  }
}
