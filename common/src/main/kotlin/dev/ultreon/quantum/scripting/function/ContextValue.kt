package dev.ultreon.quantum.scripting.function

data class ContextValue<T : Any>(val type: ContextType<out T>, val value: T)
