package dev.ultreon.quantum.scripting.function

data class ContextValue<T : Any>(val type: ContextType<T>, val value: T)
