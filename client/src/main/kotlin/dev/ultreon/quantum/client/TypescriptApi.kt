@file:OptIn(ExperimentalContracts::class)

package dev.ultreon.quantum.client

import com.caoccao.javet.values.V8Value
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class TypescriptApi(val name: String) {
  private val functions: MutableMap<String, TSFunction> = HashMap()

  fun createFunction(name: String, params: TSParams, returnType: TSType, invoke: (args: Array<out V8Value>) -> V8Value): TypescriptApi {
    contract {
      callsInPlace(invoke)
      returnsNotNull()
    }

    this.functions[name] = TSFunction(name, params, returnType, invoke)
    return this
  }

  fun createFunction(name: String, returnType: TSType, invoke: (args: Array<out V8Value>) -> V8Value): TypescriptApi {
    contract {
      callsInPlace(invoke)
      returnsNotNull()
    }

    return createFunction(name, TSParams(), returnType, invoke)
  }

  fun createFunction(name: String, invoke: (args: Array<out V8Value>) -> V8Value): TypescriptApi {
    contract {
      callsInPlace(invoke)
      returnsNotNull()
    }

    return createFunction(name, TSParams(), TSType.VOID, invoke)
  }

  fun createType(name: String, register: TSType.() -> Unit): TypescriptApi {
    contract {
      callsInPlace(register)
      returnsNotNull()
    }

    TSType.fromName(name).register()
    return this
  }

  fun getFunction(name: String): TSFunction? {
    contract {
      returns()
    }

    return functions[name]
  }

  fun getFunctionNames(): Set<String> {
    return functions.keys.toSet()
  }

  fun compile(): String {
    return """
      declare namespace $name {
        ${functions.values.joinToString("\n\n")}
      }
    """.trimIndent()
  }
}
