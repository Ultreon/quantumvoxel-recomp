package dev.ultreon.quantum.blocks

import dev.ultreon.quantum.scripting.ContextAware
import dev.ultreon.quantum.scripting.ContextParam
import dev.ultreon.quantum.scripting.ContextType
import dev.ultreon.quantum.scripting.function.VirtualFunction
import kotlinx.coroutines.runBlocking

class BlockEntity : ContextAware<BlockEntity> {
  private val tickables = ArrayList<VirtualFunction>()

  fun tick() {
    for (it in this.tickables) {
      runBlocking { it.call(this@BlockEntity) }
    }
  }

  override fun contextType(): ContextType<BlockEntity> = ContextType.blockEntity
}
