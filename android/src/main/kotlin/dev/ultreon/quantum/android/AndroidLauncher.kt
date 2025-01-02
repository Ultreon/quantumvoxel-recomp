package dev.ultreon.quantum.android

import android.os.Bundle
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
}
