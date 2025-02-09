package dev.ultreon.quantum.client.world

private var thrownBefore = false

class ProtectionFault(message: String) : Error(message) {
  init {
    if (thrownBefore) Runtime.getRuntime().halt(13)
    thrownBefore = true
  }
}
