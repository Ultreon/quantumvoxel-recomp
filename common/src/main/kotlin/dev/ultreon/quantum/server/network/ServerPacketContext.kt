//package dev.ultreon.quantum.server.network
//
//import dev.ultreon.quantum.network.ConnectionStage
//import dev.ultreon.quantum.network.Packet
//import dev.ultreon.quantum.network.PacketContext
//import dev.ultreon.quantum.network.Player
//
//class ServerPacketContext(private val serverConnection: ServerConnection) : PacketContext() {
//  override fun reply(packet: Packet) {
//    serverConnection.sendPacket(packet)
//  }
//
//  override fun moveStage(stage: ConnectionStage) {
//    serverConnection.stage = stage
//  }
//
//  override fun disconnect(reason: String) {
//    serverConnection.disconnect(reason)
//  }
//
//  override val player: Player
//    get() = serverConnection.player!!
//}
