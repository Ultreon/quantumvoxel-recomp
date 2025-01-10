package dev.ultreon.quantum.client

import com.badlogic.gdx.Gdx
import io.socket.client.IO
import io.socket.engineio.client.Socket

class Network {
  fun init() {
    IO.socket("http://localhost:38800").apply {
      on("connect") {
        println("Connected")
        emit("message", "Hello from GDX")
        on("message") { println("Message: ${it[0]::class.qualifiedName}") }
      }

      on("disconnect") {
        println("Disconnected")
      }

      connect()
    }
  }
}

fun main() {
  Network().init()
}
