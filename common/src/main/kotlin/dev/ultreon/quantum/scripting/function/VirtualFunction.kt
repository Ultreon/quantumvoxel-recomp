package dev.ultreon.quantum.scripting.function

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.scripting.ContextAware
import dev.ultreon.quantum.scripting.ContextParam
import dev.ultreon.quantum.scripting.ContextType
import dev.ultreon.quantum.scripting.ContextValue
import dev.ultreon.quantum.scripting.qfunc.QFuncInterpreter

abstract class VirtualFunction(val params: MutableMap<String, ContextParam<*>> = mutableMapOf()) : ContextAware<VirtualFunction> {
  abstract suspend fun call(context: CallContext): ContextValue<*>?

  override fun contextType(): ContextType<VirtualFunction> {
    return ContextType.function
  }

  suspend fun call(json: JsonValue): ContextValue<*>? {
    val callContext = CallContext()
    for ((name, param) in params) {
      callContext[name] = param.of(json) ?: continue
    }
    return call(callContext)
  }

  suspend fun call(context: ContextAware<*>): ContextValue<*>? {
    val callContext = CallContext()
    callContext[context.contextType()] = context
    return call(callContext)
  }

  suspend fun call(map: Map<String, ContextValue<*>>): ContextValue<*>? {
    val callContext = CallContext()
    map.forEach { (name, value) -> callContext[name] = value }
    return call(callContext)
  }

  suspend fun call(vararg values: ContextValue<*>): ContextValue<*>? {
    val callContext = CallContext()
    values.forEachIndexed { i, it -> callContext[i.toString()] = it }
    return call(callContext)
  }

  companion object {
    fun register(name: String, function: VirtualFunction) {
      VirtualFunctions.register(name, function)
    }

    fun register(name: String, vararg params: ContextParam<*>, function: suspend (CallContext) -> ContextValue<*>?) {
      VirtualFunctions.register(name, object : VirtualFunction(params.associateBy { it.name }.toMutableMap()) {
        override suspend fun call(context: CallContext): ContextValue<*>? {
          return function(context)
        }
      })
    }
  }
}

object VirtualFunctions {
  private val functions = mutableMapOf<String, VirtualFunction>()

  val empty = register("empty") { null }

  val print = register("print") { context ->
    println(context.getString("value"))
    null
  }

  val log = register("log") { context ->
    logger.info(context.getString("value") ?: "null")
    null
  }

  val error = register("error") { context ->
    throw IllegalArgumentException(context.getString("value"))
  }

  val assert = register("assert") { context ->
    val boolean = context.getBoolean("value")
    if (boolean != true) {
      throw AssertionError(context.getString("message"))
    }

    ContextValue(ContextType.boolean, boolean)
  }

  val script = register("script") { context ->
    val name = context.getString("name")
    if (name != null) {
      logger.info("Script started: $name")
      return@register QFuncInterpreter(context.paramValues).interpretAsync(name, context)
    }

    throw IllegalArgumentException("No script name provided")
  }

  fun register(name: String, vararg params: ContextParam<*>, function: suspend (context: CallContext) -> ContextValue<*>?) {
    register(name, object : VirtualFunction() {
      override suspend fun call(context: CallContext): ContextValue<*>? {
        return function(context)
      }
    })
  }

  fun register(name: String, function: VirtualFunction) {
    functions[name] = function
  }

  operator fun get(name: String): VirtualFunction? {
    return functions[name]
  }

  fun parse(json: JsonValue): VirtualFunction {
    val type = json.getString("type")
    return get(type) ?: throw IllegalArgumentException("Function not found: $type")
  }
}

fun function(vararg params: ContextParam<*>, function: suspend (CallContext) -> ContextValue<*>?): VirtualFunction {
  return object : VirtualFunction(params.associateBy { it.name }.toMutableMap()) {
    override suspend fun call(context: CallContext): ContextValue<*>? {
      return function(context)
    }
  }
}
