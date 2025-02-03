package dev.ultreon.quantum.client.scripting

import com.caoccao.javet.interop.V8Runtime
import com.caoccao.javet.values.V8Value

data class TSFunction(
  val name: String,
  val params: TSParams? = null,
  val returnType: TSType? = null,
  val static: Boolean = false,
  val method: Boolean = false
) {
  override fun toString(): String {
    if (params == null && returnType == null) {
      return """${if (static) " static" else ""}${if (method) "" else " function"} $name(...args: any): any""".trimIndent()
    }
    return """${if (static) " static" else ""}${if (method) "" else " function"} $name($params): $returnType""".trimIndent()
  }

  override fun hashCode(): Int {
    return toString().hashCode()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    other as TSFunction
    return toString() == other.toString()
  }
}
