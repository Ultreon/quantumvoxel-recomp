package dev.ultreon.quantum.scripting

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.scripting.function.CallContext

interface ContextAware<T : ContextAware<T>> {
  val persistentData: PersistentData

  fun contextType(): ContextType<T>
  fun supportedTypes(): List<ContextType<*>> = listOf(contextType())
  fun fieldOf(name: String, contextJson: JsonValue?): ContextValue<*>? = null

  fun setArg(param: ContextParam<*>, callContext: CallContext) {

  }
}
