@file:JvmName("HeadlessLauncher")

package dev.ultreon.quantum.dedicated

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import dev.ultreon.quantum.GamePlatform
import dev.ultreon.quantum.gamePlatform
import dev.ultreon.quantum.resource.ResourceManager

/**
 * Launches the dedicated server.
 *
 * @see DedicatedServer
 * @author XyperCode
 * @since 0.0.1
 */
fun main() {
  gamePlatform = object : GamePlatform {
    override val isServer: Boolean
      get() = true

    override fun loadResources(resourceManager: ResourceManager) {
      resourceManager.loadFromAssetsTxt(Gdx.files.internal("assets.txt"))
    }
  }

  HeadlessApplication(DedicatedServer(), HeadlessApplicationConfiguration().apply {
    updatesPerSecond = 20
  })
}
