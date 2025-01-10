package dev.ultreon.quantum.network

import com.badlogic.gdx.net.Socket
import com.badlogic.gdx.utils.DataInput
import com.badlogic.gdx.utils.DataOutput

abstract class Connection(val socket: Socket) {
  val output = DataOutput(socket.outputStream)
  val input = DataInput(socket.inputStream)

  val io = PacketIO(input, output)

  abstract fun sendPacket(packet: Packet, callback: () -> Unit = {})

  fun onDisconnect(reason: String) = Unit
}
