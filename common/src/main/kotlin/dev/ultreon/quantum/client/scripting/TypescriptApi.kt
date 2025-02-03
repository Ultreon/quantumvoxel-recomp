package dev.ultreon.quantum.client.scripting

import com.caoccao.javet.interop.V8Runtime
import com.caoccao.javet.interop.proxy.IJavetProxyHandler
import com.caoccao.javet.interop.proxy.JavetReflectionProxyClassHandler
import com.caoccao.javet.interop.proxy.JavetReflectionProxyObjectHandler
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance

class TypescriptApi(val module: TypescriptModule, val name: String) {
  val functions: MutableMap<String, TSFunction> = HashMap()
  val types: MutableMap<String, TSType> = HashMap()
  val properties: MutableMap<String, TSProperty> = HashMap()

  inline fun <reified T : Any> createType(name: String): Pair<String?, (runtime: V8Runtime) -> IJavetProxyHandler<*, Exception>> {
    if (T::class.isCompanion) {
      types["${name}_KtCompanion"] = TSType.fromReflectionObject(T::class.companionObject!!.java).also {
        if (it == TSType.UNKNOWN) {
          throw Exception("Unknown class: ${T::class.companionObject!!.java}")
        }
      }
      properties[name] = TSProperty(name, TSType.fromReflectionObject(T::class.companionObject!!.java))

      return "${name}_KtCompanion" to {
        JavetReflectionProxyObjectHandler<Any?, Exception>(it, T::class.companionObjectInstance)
      }
    }

    types[name] = TSType.fromReflectionClass(T::class.java).also {
      if (it == TSType.UNKNOWN) {
        throw Exception("Unknown class: ${T::class.java}")
      }
    }
    return null to {
      JavetReflectionProxyClassHandler(it, T::class.java)
    }
  }

  inline fun <reified T : TSApi> createProperty(
    name: String,
    noinline value: () -> T?,
  ): (runtime: V8Runtime) -> IJavetProxyHandler<T, Exception> {
    types[name] = TSType.fromReflectionObject(T::class.java)
    return {
      JavetReflectionProxyObjectHandler(it, value())
    }
  }

  fun getFunction(name: String): TSFunction? {
    return functions[name]
  }

  fun getFunctionNames(): Set<String> {
    return functions.keys.toSet()
  }

  fun compile(): String {
    val content = """
${functions.values.sortedBy { it.toString() }.joinToString("\n\n") { it.toString().trim() }}

${types.values.sortedBy { it.toString() }.joinToString("\n\n") { it.compile().trim() }}
""".trim()
    return """declare module "${module.module}/${name}" {
${content.prependIndent("  ")}
}
"""
  }
}
