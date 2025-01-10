package dev.ultreon.quantum.android

import android.os.Bundle
import android.view.InputDevice
import android.view.MotionEvent
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import dev.ultreon.quantum.client.GamePlatform
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.gamePlatform
import dev.ultreon.quantum.resource.ResourceManager


/** Launches the Android application. */
class AndroidLauncher : AndroidApplication() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    gamePlatform = object : GamePlatform {
      override fun loadResources(resourceManager: ResourceManager) {
        resourceManager.load(Gdx.files.internal("quantum.zip"))
      }

      override val isMobile: Boolean
        get() = true
    }
    initialize(QuantumVoxel, AndroidApplicationConfiguration().apply {
      // Configure your application here.
      useImmersiveMode = true // Recommended, but not required.
      useGyroscope = true
    })
  }

  override fun onGenericMotionEvent(event: MotionEvent): Boolean {
    if ((event.source and InputDevice.SOURCE_MOUSE) == InputDevice.SOURCE_MOUSE) {
      if (event.actionMasked == MotionEvent.ACTION_HOVER_MOVE) {
        QuantumVoxel.gameInput.onMouseMove(event.x, event.y) // Handle the movement

        // Reset cursor to the center
        resetCursorToCenter()
      }
      return true
    }
    return super.onGenericMotionEvent(event)
  }

  private fun resetCursorToCenter() {
    val centerEvent = MotionEvent.obtain(
      System.currentTimeMillis(),
      System.currentTimeMillis(),
      MotionEvent.ACTION_HOVER_MOVE,
      Gdx.graphics.width / 2f,
      Gdx.graphics.height / 2f,
      0
    )
    super.onGenericMotionEvent(centerEvent)
    centerEvent.recycle()
  }
}
