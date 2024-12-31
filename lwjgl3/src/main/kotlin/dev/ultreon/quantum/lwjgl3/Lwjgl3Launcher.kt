@file:JvmName("Lwjgl3Launcher")

package dev.ultreon.quantum.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.caoccao.javet.interop.NodeRuntime
import com.caoccao.javet.interop.V8Host
import com.caoccao.javet.javenode.JNEventLoop
import dev.ultreon.quantum.LoggerFactory
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.factory

/** Launches the desktop (LWJGL3) application. */
fun main() {
  factory = LoggerFactory { Lwjgl3Logger(it) }

  val logger = LoggerFactory["Lwjgl3Launcher"]

  try {
    ANSI.enableWindowsAnsi()
  } catch (e: Throwable) {
    logger.error("Failed to enable ANSI support: ${e.message}")
  }

  // This handles macOS support and helps on Windows.
  if (StartupHelper.startNewJvmIfRequired())
    return

//  V8Host.getNodeInstance().createV8Runtime<NodeRuntime>().use { v8Runtime ->
//    JNEventLoop(v8Runtime).use { eventLoop ->
      try {
//        QuantumVoxel(v8Runtime, eventLoop).also { qv ->
          Lwjgl3Application(QuantumVoxel, Lwjgl3ApplicationConfiguration().apply {
            setTitle("Quantum Voxel")
            setWindowedMode(640, 480)
            setForegroundFPS(0)
            useVsync(false)
            setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
          })
//        }
      } catch (e: Throwable) {
        logger.error("Failed to create Quantum Voxel:\n${e.stackTraceToString()}")
      }
//    }
//  }
}
