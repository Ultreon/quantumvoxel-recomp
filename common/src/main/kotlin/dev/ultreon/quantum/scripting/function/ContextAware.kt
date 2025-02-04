package dev.ultreon.quantum.scripting.function

import com.badlogic.gdx.utils.JsonValue

interface ContextAware<T : ContextAware<T>> {
  fun contextType(): ContextType<T>
  fun supportedTypes(): List<ContextType<*>> = listOf(contextType())
  fun fieldOf(name: String, contextJson: JsonValue?): ContextValue<*>? = null

  fun setArg(param: ContextParam<*>, callContext: CallContext) {

  }
}
