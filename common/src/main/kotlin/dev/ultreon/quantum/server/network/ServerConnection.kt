package dev.ultreon.quantum.server.network//package dev.ultreon.quantum.server.network
//
//import dev.ultreon.quantum.network.*
//import io.socket.socketio.server.SocketIoSocket
//import java.util.concurrent.SynchronousQueue
//import java.util.concurrent.TimeUnit
//
//class ServerConnection(val socket: SocketIoSocket) : Connection() {
//  var player: Player? = null
//  private val sendQueue = SynchronousQueue<Pair<Packet, () -> Unit>>()
//  private val receiveQueue = SynchronousQueue<Packet>()
//
//  var stage = ConnectionStage.HANDSHAKE
//  val id = socket.id
//
//  fun receiverThread() {
//    while (socket.isConnected) {
//      val packet = read()
//      if (packet != null) {
//        receiveQueue.offer(packet)
//      }
//    }
//  }
//
//  fun senderThread() {
//    while (socket.isConnected) {
//      val (packet, callback) = sendQueue.poll(30, TimeUnit.SECONDS) ?: continue
//      socket.emit(packet.id, packet)
//      callback()
//    }
//  }
//
//  fun close() {
//    socket.disconnect(true)
//  }
//
//  fun send(packet: Packet) {
//    socket.emit(packet.id, packet)
//  }
//
//  fun poll(): Packet? {
//    return receiveQueue.poll(30, TimeUnit.SECONDS) ?: run {
//      disconnect("Timeout")
//      return null
//    }
//  }
//
//  fun read(): Packet? {
////    return when (val received = stage.readServer(socket, io)) {
////      is Packet -> received
////
////      is Disconnection -> {
////        this.onDisconnect(received.reason)
////        null
////      }
////
////      else -> {
////        println("Received invalid packet of type ${received?.javaClass?.simpleName}")
////        null
////      }
////    }
//
//    return null
//  }
//
//  override fun sendPacket(packet: Packet, callback: () -> Unit) {
//    sendQueue.add(packet to callback)
//  }
//
//  fun disconnect(message: String) {
////    output.writeInt(-1)
////    output.writeString(message)
////    output.flush()
//    close()
//  }
//}
