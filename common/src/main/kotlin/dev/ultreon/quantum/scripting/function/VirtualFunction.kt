package dev.ultreon.quantum.scripting.function

abstract class VirtualFunction {
  val params: MutableMap<String, ContextParam<*>> = mutableMapOf()

  abstract fun call(context: CallContext)
  fun call(context: ContextAware) {
    val callContext = CallContext()
    callContext.set(context.contextType(), context)
    call(callContext)
  }
}

object VirtualFunctions {
  private val functions = mutableMapOf<String, VirtualFunction>()

  fun register(name: String, function: VirtualFunction) {
    functions[name] = function
  }
}
