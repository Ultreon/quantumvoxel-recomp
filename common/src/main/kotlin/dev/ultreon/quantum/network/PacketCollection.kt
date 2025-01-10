package dev.ultreon.quantum.network

import dev.ultreon.quantum.InternalApi

class PacketCollection(val name: String, val id: Int, val builder: PacketCollector.() -> Unit) {
  val packetToClient = PacketRegistry()
  val packetToServer = PacketRegistry()

  init {
    builder(PacketCollector(this))
  }
}

@OptIn(InternalApi::class)
class PacketCollector(@property:InternalApi val collection: PacketCollection) {
  fun <T : Any> registerClientEndpoint(
    id: Int,
    type: Class<T>,
    encoder: PacketEncoder<T>,
    decoder: PacketDecoder<T>,
    handler: PacketHandler<T>
  ) {
    collection.packetToClient.register(id, type, decoder, encoder, handler)
  }

  fun <T : Any> registerServerEndpoint(
    id: Int,
    type: Class<T>,
    encoder: PacketEncoder<T>,
    decoder: PacketDecoder<T>,
    handler: PacketHandler<T>
  ) {
    collection.packetToServer.register(id, type, decoder, encoder, handler)
  }

  inline fun <reified T : Any> registerClientEndpoint(
    encoder: PacketEncoder<T>,
    decoder: PacketDecoder<T>,
    handler: PacketHandler<T>
  ) {
    collection.packetToClient.register(decoder, encoder, handler)
  }

  inline fun <reified T : Any> registerServerEndpoint(
    encoder: PacketEncoder<T>,
    decoder: PacketDecoder<T>,
    handler: PacketHandler<T>
  ) {
    collection.packetToServer.register(decoder, encoder, handler)
  }
}

fun interface PacketEncoder<T : Any> {
  fun encode(packet: T, buffer: PacketIO)
}

fun interface PacketDecoder<T : Any> {
  fun decode(buffer: PacketIO): T
}

fun interface PacketHandler<T : Any> {
  fun handle(packet: T, context: PacketContext)
}
