package dev.ultreon.quantum.network

object PacketCollections {
  val handshake = PacketCollection("Handshake", 0) {
    registerServerEndpoint<ConnectionGuard>(ConnectionGuard::encode, ConnectionGuard::decode, ConnectionGuard::handle)
    registerClientEndpoint<ConnectionAnswer>(ConnectionAnswer::encode, ConnectionAnswer::decode, ConnectionAnswer::handle)
    registerServerEndpoint<ConnectionResponse>(ConnectionResponse::encode, ConnectionResponse::decode, ConnectionResponse::handle)
  }
  val status = PacketCollection("Status", 1) {

  }
  val login = PacketCollection("Login", 1) {

  }
  val play = PacketCollection("Play", 1) {

  }
}

data class ConnectionGuard(val question: Int) : Packet() {
  override fun encode(buffer: PacketIO) {
    buffer.writeInt(question)
  }

  override fun handle(context: PacketContext) {
    context.reply(ConnectionAnswer(question))
  }

  companion object {
    fun decode(buffer: PacketIO): ConnectionGuard {
      return ConnectionGuard(buffer.readInt())
    }
  }
}

data class ConnectionAnswer(val answer: Int) : Packet() {
  override fun encode(buffer: PacketIO) {
    buffer.writeInt(answer)
  }

  override fun handle(context: PacketContext) {
    context.reply(ConnectionResponse(answer % 64 == 0 && answer != 0))
  }

  companion object {
    fun decode(buffer: PacketIO): ConnectionAnswer {
      return ConnectionAnswer(buffer.readInt())
    }
  }
}

data class ConnectionResponse(val accepted: Boolean) : Packet() {
  override fun encode(buffer: PacketIO) {
    buffer.writeBoolean(accepted)
  }

  override fun handle(context: PacketContext) {
    context.moveStage(ConnectionStage.LOGIN)
  }

  companion object {
    fun decode(buffer: PacketIO): ConnectionResponse {
      return ConnectionResponse(buffer.readBoolean())
    }
  }
}
