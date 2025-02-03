package dev.ultreon.quantum.func

abstract class VirtualFunction {
  abstract fun call(context: CallContext)
  fun call(context: ContextAware) {
    val callContext = CallContext()
    for (param in context.supportedParams()) {
      callContext.add(context, param)
    }
    call(callContext)
  }
}

object VirtualFunctions {
  private val functions = mutableMapOf<String, VirtualFunction>()

  fun register(name: String, function: VirtualFunction) {
    functions[name] = function
  }
}
