@file:JvmName("TeaVMLauncher")

package dev.ultreon.quantum.teavm

import com.badlogic.gdx.Gdx
import com.github.xpenatan.gdx.backends.teavm.TeaApplication
import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration
import dev.ultreon.quantum.Logger
import dev.ultreon.quantum.LoggerFactory
import dev.ultreon.quantum.GamePlatform
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.gamePlatform
import dev.ultreon.quantum.factory
import dev.ultreon.quantum.resource.ResourceManager
import org.teavm.jso.JSObject
import org.teavm.jso.browser.Window

/** Launches the TeaVM/HTML application. */
fun main() {
  val config = TeaApplicationConfiguration("canvas").apply {
    //// If width and height are each greater than 0, then the app will use a fixed size.
    //width = 640
    //height = 480
    //// If width and height are both 0, then the app will use all available space.
    width = 0
    height = 0
    //// If width and height are both -1, then the app will fill the canvas size.
//    width = -1
//    height = -1
    this.antialiasing = false
    this.padVertical = 0
    this.padHorizontal = 0
    this.usePhysicalPixels = true
    this.powerPreference = "high-performance"

    this.useGL30 = true
  }

  factory = TeaVMFactory()
  gamePlatform = object : GamePlatform {
    override val isWebGL3: Boolean
      get() = Gdx.gl30 != null

    override val isWebGL2: Boolean
      get() = Gdx.gl20 != null

    override fun loadResources(resourceManager: ResourceManager) {
      resourceManager.load(Gdx.files.internal("."))
    }

    override val isMobile: Boolean
      get() = TeaApplication.isMobileDevice()

    override val isWeb: Boolean
      get() = true

    override val isDebug: Boolean
      get() = Window.current().location.hostName == "localhost"
  }
  dev.ultreon.quantum.teavm.TeaApplication(QuantumVoxel(), config)
}

class TeaVMFactory : LoggerFactory {
  override fun getLogger(name: String): Logger = TeaVMLogger(name)
}

class TeaVMLogger(val name: String) : Logger, JSObject {
  override fun info(message: String) {
    TeaVMConsole.info("$name: $message")
  }

  override fun warn(message: String) {
    TeaVMConsole.warn("$name: $message")
  }

  override fun error(message: String) {
    TeaVMConsole.error("$name: $message")
  }

  override fun debug(message: String) {
    TeaVMConsole.debug("$name: $message")
  }

  override fun trace(message: String) {
    TeaVMConsole.debug("$name (trace): $message")
  }

  override fun info(message: String, obj: Any?) {
    TeaVMConsole.info("$name: $message", obj)
  }

  override fun warn(message: String, obj: Any?) {
    TeaVMConsole.warn("$name: $message", obj)
  }

  override fun error(message: String, obj: Any?) {
    TeaVMConsole.error("$name: $message", obj)
  }

  override fun debug(message: String, obj: Any?) {
    TeaVMConsole.debug("$name: $message", obj)
  }

  override fun trace(message: String, obj: Any?) {
    TeaVMConsole.debug("$name (trace): $message", obj)
  }
}

