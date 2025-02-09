package dev.ultreon.quantum.scripting.function

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.commonResources
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.resource.asDir
import dev.ultreon.quantum.resource.asLeaf
import dev.ultreon.quantum.scripting.*
import dev.ultreon.quantum.scripting.qfunc.QFuncInterpreter

abstract class VirtualFunction(
  val params: MutableMap<String, ContextParam<*>> = mutableMapOf(),
  val contextJson: JsonValue
) :
  ContextAware<VirtualFunction> {

  abstract suspend fun call(context: CallContext): ContextValue<*>?

  override fun contextType(): ContextType<VirtualFunction> {
    return ContextType.function
  }

  suspend fun call(json: JsonValue): ContextValue<*>? {
    val callContext = CallContext(json)
    for ((name, param) in params) {
      callContext[name] = param.of(json) ?: continue
    }
    return call(callContext)
  }

  suspend fun call(json: JsonValue, context: ContextAware<*>): ContextValue<*>? {
    val callContext = CallContext(json)
    callContext[context.contextType()] = context
    return call(callContext)
  }

  suspend fun call(json: JsonValue, map: Map<String, ContextValue<*>>): ContextValue<*>? {
    val callContext = CallContext(json)
    map.forEach { (name, value) -> callContext[name] = value }
    return call(callContext)
  }

  suspend fun call(json: JsonValue, vararg values: ContextValue<*>): ContextValue<*>? {
    val callContext = CallContext(json)
    values.forEachIndexed { i, it -> callContext[i.toString()] = it }
    return call(callContext)
  }

  companion object {
    fun register(name: String, function: (JsonValue) -> VirtualFunction) {
      VirtualFunctions.register0(name, function)
    }

    fun register(name: String, vararg params: ContextParam<*>, function: suspend (CallContext) -> ContextValue<*>?) {
      VirtualFunctions.register0(name, { json ->
        object : VirtualFunction(params.associateBy { it.name }.toMutableMap(), contextJson = json) {
          override suspend fun call(context: CallContext): ContextValue<*>? {
            return function(context)
          }

          override val persistentData: PersistentData = PersistentData()
        }
      })
    }
  }
}

object VirtualFunctions {
  private val functions = mutableMapOf<String, (JsonValue) -> VirtualFunction>()

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
    val name = context.originJson.get("name")?.asString() ?: run {
      logger.error("No script name provided")
      return@register null
    }
    val last = commonResources["scripts"]?.asDir()?.get(name)?.asLeaf()?.last()
    val text = last?.text
    return@register QFuncInterpreter(context.paramValues)
      .interpretAsync(
        text ?: run {
          logger.error("Script not found: $name")
          return@register null
        }, context, last.location.toString()
      )
  }

  fun register(
    name: String,
    vararg params: ContextParam<*>,
    function: suspend (context: CallContext) -> ContextValue<*>?
  ) {
    register0(name, { json ->
      object : VirtualFunction(contextJson = json) {
        override suspend fun call(context: CallContext): ContextValue<*>? {
          return function(context)
        }

        override val persistentData: PersistentData = PersistentData()
      }
    })
  }

  fun register0(name: String, function: (JsonValue) -> VirtualFunction) {
    functions[name] = function
  }

  operator fun get(name: String): ((JsonValue) -> VirtualFunction)? {
    return functions[name]
  }

  fun parse(json: JsonValue): VirtualFunction {
    val type = json.getString("type")
    return (get(type) ?: throw IllegalArgumentException("Function not found: $type"))(json)
  }

}

fun function(vararg params: ContextParam<*>, function: suspend (CallContext) -> ContextValue<*>?): VirtualFunction {
  return object : VirtualFunction(
    params.associateBy { it.name }.toMutableMap(),
    contextJson = JsonValue(JsonValue.ValueType.nullValue)
  ) {
    override suspend fun call(context: CallContext): ContextValue<*>? {
      val errors = ArrayList<String>()
      for (param in params) {
        if (context.paramValues[param.name] == null) {
          errors += "Missing parameter '${param.name}'"
        }
      }

      for (paramValue in context.paramValues.keys) {
        if (!params.any { it.name == paramValue }) {
          errors += "Unknown parameter: '$paramValue'"
        }
      }

      if (errors.isNotEmpty()) {
        throw IllegalArgumentException("\n" + errors.joinToString("\n") { it.prependIndent("  ")} + "\n")
      }

      return function(context)
    }

    override val persistentData: PersistentData = PersistentData()
  }
}
