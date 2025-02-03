package dev.ultreon.quantum.func

interface ContextAware {
  fun <T> getContextParam(key: ContextParam<T>): T?
  fun selfParam(): ContextParam<*>
  fun supportedParams(): List<ContextParam<*>> = listOf(selfParam())
}
