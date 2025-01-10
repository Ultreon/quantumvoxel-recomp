package dev.ultreon.quantum.network

import com.artemis.Entity
import dev.ultreon.quantum.entity.PlayerComponent

open class Player(
  val entity: Entity,
  open val playerComponent: PlayerComponent
) {

}
