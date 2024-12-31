@file:JvmName("TeaVMLauncher")

package dev.ultreon.quantum.teavm

import com.github.xpenatan.gdx.backends.teavm.TeaApplication
import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration
import dev.ultreon.quantum.client.QuantumVoxel

/** Launches the TeaVM/HTML application. */
fun main() {
  val config = TeaApplicationConfiguration("canvas").apply {
    //// If width and height are each greater than 0, then the app will use a fixed size.
    //width = 640
    //height = 480
    //// If width and height are both 0, then the app will use all available space.
    //width = 0
    //height = 0
    //// If width and height are both -1, then the app will fill the canvas size.
    width = -1
    height = -1
  }
  TeaApplication(QuantumVoxel(), config)
}
