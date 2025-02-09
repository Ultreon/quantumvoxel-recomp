package dev.ultreon.quantum.blocks

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.scripting.ContextAware
import dev.ultreon.quantum.scripting.ContextParam
import dev.ultreon.quantum.scripting.ContextType
import dev.ultreon.quantum.scripting.PersistentData
import dev.ultreon.quantum.scripting.function.VirtualFunction
import kotlinx.coroutines.runBlocking

class BlockEntity : ContextAware<BlockEntity> {
  private val tickables = ArrayList<VirtualFunction>()

  fun tick() {
    for (it in this.tickables) {
//      runBlocking { it.call() }
    }
  }

  override val persistentData: PersistentData = PersistentData()

  override fun contextType(): ContextType<BlockEntity> = ContextType.blockEntity

  fun save(jsonValue: JsonValue) {
    for ((key, value) in this.persistentData) {
      jsonValue.addChild(key, value.serialize())
    }
  }
}
