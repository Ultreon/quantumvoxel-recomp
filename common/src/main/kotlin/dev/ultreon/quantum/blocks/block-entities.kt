package dev.ultreon.quantum.blocks

import dev.ultreon.quantum.func.ContextAware
import dev.ultreon.quantum.func.ContextParam
import dev.ultreon.quantum.func.VirtualFunction

class BlockEntity : ContextAware {
  private val tickables = ArrayList<VirtualFunction>()

  fun tick() {
    for (it in this.tickables) {
      it.call(this)
    }
  }

  override fun <T> getContextParam(key: ContextParam<T>): T? {
    @Suppress("UNCHECKED_CAST")
    return when (key) {
      ContextParam.BLOCK_ENTITY -> this as T
      else -> null
    }
  }

  override fun selfParam(): ContextParam<*> {
    return ContextParam.BLOCK_ENTITY
  }
}
