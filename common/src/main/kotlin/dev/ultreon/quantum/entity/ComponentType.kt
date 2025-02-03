package dev.ultreon.quantum.entity

import dev.ultreon.quantum.func.CallContext

class ComponentType<T>(
  val name: String,
  val constructor: (context: CallContext) -> T
) {
  operator fun invoke(context: CallContext): T {
    return constructor(context)
  }
}
