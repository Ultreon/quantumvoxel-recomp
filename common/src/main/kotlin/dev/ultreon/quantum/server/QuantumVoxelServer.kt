package dev.ultreon.quantum.server

//import dev.ultreon.quantum.event.EventBus
import dev.ultreon.quantum.network.Networker
import dev.ultreon.quantum.world.Dimension

const val TPS = 20
const val MSPT = 1000 / TPS

abstract class QuantumVoxelServer {
  val dimension: Dimension = ServerDimension()
  abstract val networker: Networker

  fun runTick() {

  }

  private fun loop() {
    var lastTick = System.currentTimeMillis()
    while (true) {
      val currentTick = System.currentTimeMillis()
      val delta = currentTick - lastTick
      if (delta >= MSPT) {
        lastTick = currentTick
        runTick()
      }
    }
  }

  abstract val isDedicatedServer: Boolean
}

//val serverEventBus = EventBus()
