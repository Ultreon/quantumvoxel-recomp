//package dev.ultreon.quantum.network
//
//import dev.ultreon.quantum.server.network.ServerConnection
//import io.socket.socketio.server.SocketIoSocket
//
//
//class TcpNetworker : Networker {
////  private var server: ServerWrapper? = null
//  private val connections: MutableMap<String, ServerConnection> = mutableMapOf()
//
//  override fun init() {
////    server = ServerWrapper("localhost", 8080, null).apply { startServer() }
////    server.socketIoServer.namespace("/").apply {
////      on("connection") { args ->
////        val socket = args[0] as SocketIoSocket
////        println("Client " + socket.id + " (" + socket.initialHeaders["remote_addr"] + ") has connected.")
////        socket.on("message") {
////          println("[Client " + socket.id + "] " + args)
////          socket.send("message", "test message", 1)
////        }
////      }
////    }
//  }
//}
//
//fun main() {
//  val networker = TcpNetworker()
//  networker.init()
//
//  while (true) {
//    Thread.sleep(1000)
//  }
//}
