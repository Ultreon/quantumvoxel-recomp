package dev.ultreon.quantum.scripting.function

import com.badlogic.gdx.utils.JsonValue

abstract class VirtualFunction(val params: MutableMap<String, ContextParam<*>> = mutableMapOf()) {
  abstract fun call(context: CallContext)

  fun call(json: JsonValue) {
    val callContext = CallContext()
    for ((name, param) in params) {
      callContext[name] = param.of(json) ?: continue
    }
    call(callContext)
  }

  fun call(context: ContextAware<*>) {
    val callContext = CallContext()
    callContext[context.contextType()] = context
    call(callContext)
  }

  fun call(map: Map<String, ContextValue<*>>) {
    val callContext = CallContext()
    map.forEach { (name, value) -> callContext[name] = value }
    call(callContext)
  }

  fun call(vararg values: ContextValue<*>) {
    val callContext = CallContext()
    values.forEachIndexed { i, it -> callContext[i.toString()] = it }
    call(callContext)
  }

  companion object {
    fun register(name: String, function: VirtualFunction) {
      VirtualFunctions.register(name, function)
    }

    fun register(name: String, vararg params: ContextParam<*>, function: (CallContext) -> Unit) {
      VirtualFunctions.register(name, object : VirtualFunction(params.associateBy { it.name }.toMutableMap()) {
        override fun call(context: CallContext) {
          function(context)
        }
      })
    }
  }
}

object VirtualFunctions {
  private val functions = mutableMapOf<String, VirtualFunction>()

  fun register(name: String, function: VirtualFunction) {
    functions[name] = function
  }

  fun get(name: String): VirtualFunction? {
    return functions[name]
  }

  fun parse(json: JsonValue): VirtualFunction {
    val type = json.getString("type")
    return get(type) ?: throw IllegalArgumentException("Function not found: $type")
  }
}
