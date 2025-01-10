package dev.ultreon.quantum.server.player

import com.artemis.Entity
import dev.ultreon.quantum.network.Player

class ServerPlayer(entity: Entity, override val playerComponent: ServerPlayerComponent) : Player(entity, playerComponent)