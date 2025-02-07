package dev.ultreon.quantum.scripting

data class ContextValue<T : Any>(val type: ContextType<out T>, val value: T) {
  @Suppress("UNCHECKED_CAST")
  fun isSame(t: ContextValue<*>?): ContextValue<Boolean> {
    if (t == null) return ContextValue(ContextType.boolean, false)
    val let = (type as ContextType<T>).isSame(this, t)
    return ContextValue(ContextType.boolean, let)
  }
}
