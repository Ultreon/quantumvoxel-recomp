package dev.ultreon.quantum.network

import com.corundumstudio.socketio.*
import com.corundumstudio.socketio.listener.DataListener
import kotlin.concurrent.thread

object NamespaceChatLauncher {
  @Throws(InterruptedException::class)
  @JvmStatic
  fun main(args: Array<String>) {
    val config = Configuration()
    config.isWebsocketCompression = true
    config.hostname = "localhost"
    config.port = 38800

    val server = SocketIOServer(config)
    val serverNamespace = server.addNamespace("/")
    server.addEventListener(
      "message", Packet::class.java
    ) { client, data, ackRequest -> // broadcast messages to all clients
      println(data)
      serverNamespace.broadcastOperations.sendEvent("message", data)
    }
    serverNamespace.addEventListener(
      "message", String::class.java
    ) { client, data, ackRequest -> // broadcast messages to all clients
      println(data)
      serverNamespace.broadcastOperations.sendEvent("message", data)
    }
    serverNamespace.addConnectListener {
      println("Client connected")
      it.sendEvent("message", "Hello World")
    }

    server.start()
    thread {
      while (true) {
        print("Enter message: ")
        val readLine: String = readln()
        server.broadcastOperations.sendEvent("message", Chat(readLine))
        if (readLine.equals("exit", ignoreCase = true)) {
          break
        }
      }
    }.join()
    server.stop()
  }
}
