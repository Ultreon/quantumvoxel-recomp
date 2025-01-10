package dev.ultreon.quantum.network

import com.artemis.Entity
import dev.ultreon.quantum.entity.PlayerComponent

abstract class PacketContext {
  protected val connection: Connection? = null

  abstract fun reply(packet: Packet)
  abstract fun moveStage(stage: ConnectionStage)
  abstract fun disconnect(reason: String)

  abstract val player: Player
}
