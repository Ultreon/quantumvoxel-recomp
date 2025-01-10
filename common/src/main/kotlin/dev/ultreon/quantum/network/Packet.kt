package dev.ultreon.quantum.network

abstract class Packet {
  abstract fun encode(buffer: PacketIO)
  abstract fun handle(context: PacketContext)
}
