@file:JvmName("TeaVMLauncher")

package dev.ultreon.quantum.teavm

import com.badlogic.gdx.Gdx
import com.github.xpenatan.gdx.backends.teavm.TeaApplication
import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration
import dev.ultreon.quantum.client.GamePlatform
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.gamePlatform
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.resource.ResourceManager

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
  }

  logger
  gamePlatform = object : GamePlatform {
    override fun loadResources(resourceManager: ResourceManager) {
      resourceManager.load(Gdx.files.internal("quantum.zip"))
    }
  }
  TeaApplication(QuantumVoxel, config)
}
