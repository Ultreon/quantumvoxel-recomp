//package dev.ultreon.quantum.network
//
//import io.socket.engineio.server.EngineIoServer
//import io.socket.socketio.server.SocketIoServer
//import io.socket.socketio.server.SocketIoSocket
//import jakarta.servlet.annotation.WebServlet
//import jakarta.servlet.http.HttpServlet
//import java.io.IOException
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import java.lang.ref.Cleaner
//
//@WebServlet("quantum-voxel/*")
//open class SocketIoServlet : HttpServlet() {
//  val mEngineIoServer: EngineIoServer = EngineIoServer()
//  val mSocketIoServer: SocketIoServer = SocketIoServer(mEngineIoServer)
//
//  init {
//    mSocketIoServer.namespace("/").apply {
//      on("connection") { args ->
//        val socket = args[0] as SocketIoSocket
//        println("Client " + socket.id + " (" + socket.initialHeaders["remote_addr"] + ") has connected.")
//        socket.on("message") {
//          println("[Client " + socket.id + "] " + args.contentDeepToString())
//          socket.send("message", "test message", 1)
//        }
//      }
//    }
//  }
//
//  @Throws(IOException::class)
//  override fun service(request: HttpServletRequest?, response: HttpServletResponse?) {
//    mEngineIoServer.handleRequest(request, response)
//  }
//
//  override fun destroy() {
//    mEngineIoServer.shutdown()
//  }
//}
//
//fun main() {
//  val socketIoServlet = SocketIoServlet()
//
//  while (true) {
//    Thread.sleep(1000)
//  }
//}
