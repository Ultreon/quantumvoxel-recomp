package dev.ultreon.quantum.network

import com.badlogic.gdx.net.Socket

enum class ConnectionStage(val collection: PacketCollection) {
  HANDSHAKE(PacketCollections.handshake),
  STATUS(PacketCollections.status),
  LOGIN(PacketCollections.login),
  PLAY(PacketCollections.play);

  fun readServer(socket: Socket, io: PacketIO): Any? {
    return collection.packetToServer.decode(io)
  }
}
