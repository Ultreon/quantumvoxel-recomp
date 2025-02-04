package dev.ultreon.quantum.blocks

import dev.ultreon.quantum.scripting.function.ContextAware
import dev.ultreon.quantum.scripting.function.ContextParam
import dev.ultreon.quantum.scripting.function.ContextType
import dev.ultreon.quantum.scripting.function.VirtualFunction

class BlockEntity : ContextAware<BlockEntity> {
  private val tickables = ArrayList<VirtualFunction>()

  fun tick() {
    for (it in this.tickables) {
      it.call(this)
    }
  }

  override fun contextType(): ContextType<BlockEntity> = ContextType.blockEntity
}
