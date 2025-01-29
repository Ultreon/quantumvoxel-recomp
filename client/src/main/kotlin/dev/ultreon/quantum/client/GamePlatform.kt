package dev.ultreon.quantum.client

import dev.ultreon.quantum.resource.ResourceManager

interface GamePlatform {
  val isGLES3: Boolean get() = false

  val isGLES2: Boolean get() = isGLES3

  val isGL46: Boolean get() = false

  val isGL45: Boolean get() = isGL46

  val isGL44: Boolean get() = isGL45

  val isGL43: Boolean get() = isGL44

  val isGL42: Boolean get() = isGL43

  val isGL41: Boolean get() = isGL42

  val isGL40: Boolean get() = isGL41

  val isGL33: Boolean get() = isGL40

  val isGL32: Boolean get() = isGL33

  val isGL31: Boolean get() = isGL32

  val isGL30: Boolean get() = isGL31

  val isGL20: Boolean get() = isGL30

  fun loadResources(resourceManager: ResourceManager)

  fun dispose() {

  }

  val isMobile: Boolean get() = false

  fun nextFrame() {

  }

  val isWebGL3: Boolean get() = false

  val isWebGL2: Boolean get() = false
}
