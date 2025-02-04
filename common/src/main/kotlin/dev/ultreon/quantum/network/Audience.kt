package dev.ultreon.quantum.network

import dev.ultreon.quantum.ExperimentalQuantumApi

interface Audience {
  fun sendPacket(packet: Packet, callback: () -> Unit = {})

  fun disconnect(reason: String)
  @ExperimentalQuantumApi
  fun sendMessage(message: String)
}
