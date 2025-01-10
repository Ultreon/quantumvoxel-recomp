package dev.ultreon.quantum.network

abstract class Packet(val id: String) {
  abstract fun handle(context: PacketContext)
}
