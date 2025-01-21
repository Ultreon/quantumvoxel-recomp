package dev.ultreon.quantum.event

interface EventListener {
  fun onEvent(event: Event)
}
