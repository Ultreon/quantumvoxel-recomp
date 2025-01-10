@file:JvmName("IOSLauncher")

package dev.ultreon.quantum.ios

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.iosrobovm.IOSApplication
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration
import dev.ultreon.quantum.client.GamePlatform
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.gamePlatform
import dev.ultreon.quantum.resource.ResourceManager
import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication

/** Launches the iOS (RoboVM) application. */
class IOSLauncher : IOSApplication.Delegate() {
  override fun createApplication(): IOSApplication {
    gamePlatform = object : GamePlatform {
      override fun loadResources(resourceManager: ResourceManager) {
        resourceManager.load(Gdx.files.internal("quantum.zip"))
      }

      override val isMobile: Boolean
        get() = true
    }

    return IOSApplication(QuantumVoxel, IOSApplicationConfiguration().apply
      {
        // Configure your application here.
      })
  }

  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val pool = NSAutoreleasePool()
      val principalClass: Class<UIApplication>? = null
      val delegateClass = IOSLauncher::class.java
      UIApplication.main(args, principalClass, delegateClass)
      pool.close()
    }
  }
}
