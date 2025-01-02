package dev.ultreon.quantum.client

import dev.ultreon.quantum.resource.ResourceManager

interface GamePlatform {
    fun loadResources(resourceManager: ResourceManager)
  fun dispose() {

  }

  val isMobile: Boolean get() = false
}