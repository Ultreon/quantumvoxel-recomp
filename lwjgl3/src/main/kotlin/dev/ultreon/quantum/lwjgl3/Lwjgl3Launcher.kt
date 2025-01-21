@file:JvmName("Lwjgl3Launcher")

package dev.ultreon.quantum.lwjgl3

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Os
import com.badlogic.gdx.utils.SharedLibraryLoader
import com.caoccao.javet.interop.NodeRuntime
import com.caoccao.javet.interop.V8Host
import com.caoccao.javet.javenode.JNEventLoop
import dev.ultreon.gdx.lwjgl3.angle.ANGLELoader
import dev.ultreon.quantum.LoggerFactory
import dev.ultreon.quantum.client.*
import dev.ultreon.quantum.factory
import dev.ultreon.quantum.resource.ResourceManager
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.zip.ZipInputStream
import kotlin.io.path.Path
import kotlin.io.path.toPath
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application as OpenGLApp
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration as OpenGLConfig
import com.github.dgzt.gdx.lwjgl3.Lwjgl3ApplicationConfiguration as VulkanConfig
import com.github.dgzt.gdx.lwjgl3.Lwjgl3VulkanApplication as VulkanApp
import dev.ultreon.gdx.lwjgl3.Lwjgl3ApplicationConfiguration as MetalConfig
import dev.ultreon.gdx.lwjgl3.Lwjgl3MetalApplication as MetalApp

/** Launches the desktop (LWJGL3) application. */
fun main() {
  factory = Lwjgl3LoggerFactory

  val logger = LoggerFactory["Lwjgl3Launcher"]

  try {
    ANSI.enableWindowsAnsi()
  } catch (e: Throwable) {
    logger.error("Failed to enable ANSI support: ${e.message}")
  }

  gamePlatform = object : GamePlatform {
    override fun loadResources(resourceManager: ResourceManager) {
//      try {
//        // Locate resource "_assetroot" and use its parent directory as the root
//        val resource = QuantumVoxel::class.java.classLoader.getResource("_assetroot")
//        val path = resource?.toURI()?.toPath()?.parent ?: throw FileNotFoundException("Asset root not found")
//        resourceManager.load(Gdx.files.absolute(path.toString()))
//
//      } catch (e: Exception) {
//        ZipInputStream(QuantumVoxel::class.java.getResourceAsStream("/quantum.zip")?.buffered()
//                ?: Files.newInputStream(Paths.get("quantum.zip")).buffered()).use {
//          resourceManager.loadZip(it)
//        }
//      }

      resourceManager.loadFromAssetsTxt(Gdx.files.internal("assets.txt"))
    }

    override val isMobile: Boolean
      get() = false

    override fun dispose() {
      logger.info("Exiting...")

      // Loop through threads and interrupt them, unless they are the main thread
      Thread.getAllStackTraces().keys.filter { it != Thread.currentThread() && it.isAlive && !it.isDaemon && it.name != "Finalizer" }.forEach {
        if (it.id == Thread.currentThread().id) return@forEach
        logger.warn("Interrupting thread ${it.name} due to it being stuck")
        it.interrupt()
        it.join(1000)
        if (it.isAlive) {
          logger.error("Thread ${it.name} is still running! Halting JVM...")
          Runtime.getRuntime().halt(1)
        }
      }
    }
  }

  // Extract mac64/*.dylib or macarm64/*.dylib into the same directory as where it ran from
  val osName = System.getProperty("os.name").lowercase()
  if (osName.contains("mac", ignoreCase = true)) {
    val archName = System.getProperty("os.arch").lowercase()
    if (archName.contains("aarch64", ignoreCase = true)) {
      Lwjgl3Logger::class.java.getResourceAsStream("/macarm64/libEGL.dylib")?.use { input ->
        Files.copy(input, Path("./libEGL.dylib"), StandardCopyOption.REPLACE_EXISTING)
      }
      Lwjgl3Logger::class.java.getResourceAsStream("/macarm64/libGLESv2.dylib")?.use { input ->
        Files.copy(input, Path("./libGLESv2.dylib"), StandardCopyOption.REPLACE_EXISTING)
      }
    } else {
      Lwjgl3Logger::class.java.getResourceAsStream("/mac64/libEGL.dylib")?.use { input ->
        Files.copy(input, Path("./libEGL.dylib"), StandardCopyOption.REPLACE_EXISTING)
      }
      Lwjgl3Logger::class.java.getResourceAsStream("/mac64/libGLESv2.dylib")?.use { input ->
        Files.copy(input, Path("./libGLESv2.dylib"), StandardCopyOption.REPLACE_EXISTING)
      }
    }
  }

  // This handles macOS support and helps on Windows.
  if (StartupHelper.startNewJvmIfRequired())
    return

  try {
    when (SharedLibraryLoader.os) {
//      Os.MacOsX -> {
//        MetalApp(quantum, MetalConfig().apply {
//          setTitle("Quantum Voxel")
//          setWindowedMode(MINIMUM_WIDTH * 2, MINIMUM_HEIGHT * 2)
//          setOpenGLEmulation(MetalConfig.GLEmulation.ANGLE_GLES32, 2, 0)
//          setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
//          setBackBufferConfig(4, 4, 4, 4, 8, 8, 0)
//        })
//      }

      Os.Windows -> {
        VulkanApp(quantum, VulkanConfig().apply {
          setTitle("Quantum Voxel")
          setWindowedMode(MINIMUM_WIDTH * 2, MINIMUM_HEIGHT * 2)
          setForegroundFPS(0)
          useVsync(false)
          setOpenGLEmulation(VulkanConfig.GLEmulation.ANGLE_GLES32, 4, 3)
          setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
          setBackBufferConfig(4, 4, 4, 4, 8, 8, 0)
        })
      }

      else -> {
        OpenGLApp(QuantumVoxel(), OpenGLConfig().apply {
          setTitle("Quantum Voxel")
          setWindowedMode(MINIMUM_WIDTH * 3 - 2, MINIMUM_HEIGHT * 3 - 2)
          setForegroundFPS(0)
          useVsync(false)
          setOpenGLEmulation(OpenGLConfig.GLEmulation.GL32, 4, 1)
          setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
          setBackBufferConfig(4, 4, 4, 4, 8, 8, 0)
        })
      }
    }
  } catch (e: Throwable) {
    logger.error("Failed to create Quantum Voxel:\n${e.stackTraceToString()}")
  }
}
