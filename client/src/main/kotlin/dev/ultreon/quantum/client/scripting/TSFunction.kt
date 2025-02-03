package dev.ultreon.quantum.client.scripting

import com.caoccao.javet.values.V8Value

data class TSFunction(val name: String, val params: TSParams, val returnType: TSType, private val invoke: (args: Array<out V8Value>) -> V8Value) {
  operator fun invoke(vararg args: V8Value): V8Value = this.invoke.invoke(args)

  override fun toString(): String {
    return """
      declare function $name($params): $returnType
    """.trimIndent()
  }
}
