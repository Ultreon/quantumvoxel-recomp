package dev.ultreon.quantum.network

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.net.SocketHints
import dev.ultreon.quantum.server.network.ServerConnection

class TcpNetworker : Networker {
  private val connections: MutableMap<String, ServerConnection> = mutableMapOf()

  override fun init() {
    Gdx.net.newServerSocket(Net.Protocol.TCP, "localhost", 38800, null).also {
      it.accept(SocketHints().apply {
        keepAlive = true
      }).also { socket ->
        Gdx.app.log("quantum", "Connected to client!")
        this.connections[socket.remoteAddress] = ServerConnection(socket)
      }
    }
  }
}
