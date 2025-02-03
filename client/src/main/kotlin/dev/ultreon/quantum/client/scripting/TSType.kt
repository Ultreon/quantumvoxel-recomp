package dev.ultreon.quantum.client.scripting

import com.caoccao.javet.values.V8Value

data class TSType(
  /**
   * The name of the type
   */
  val name: String,

  /**
   * Where this type comes from
   */
  val from: String? = null,

  /**
   * Which types to import with this type
   */
  val inclusions: List<String> = emptyList(),
) {
  override fun toString(): String = name

  private val properties: MutableMap<String, TSType> = mutableMapOf()
  private val functions = mutableMapOf<String, TSFunction>()
  private val importedTypes = mutableSetOf<TSType>()

  fun createFunction(
    name: String,
    params: TSParams,
    returnType: TSType,
    invoke: (args: Array<out V8Value>) -> V8Value,
  ): TSFunction {
    this.importedTypes += returnType
    this.importedTypes += params.list

    return TSFunction(name, params, returnType, invoke).also {
      functions[name] = it
    }
  }

  fun compile(): String {
    return buildString {
      append(importedTypes.filter { it.from != null }
        .joinToString("\n") { "import {${it.name}} from \"${it.from}\"" })
      append("\n\n")

      val content = """
${properties.entries.joinToString("\n") { "${it.key}: ${it.value.name}" }}

${functions.values.joinToString("\n\n") { it.toString() }}
""".trim()
      append(
        """
class $name {
${content.prependIndent("  ")}
}
"""
      )
    }
  }

  fun property(name: String, type: TSType) {
    this.properties[name] = type
  }

  companion object {
    val VOID: TSType = TSType("void")
    val NUMBER: TSType = TSType("number")
    val STRING: TSType = TSType("string")
    val BOOLEAN: TSType = TSType("boolean")
    val ANY: TSType = TSType("any")
    val UNKNOWN: TSType = TSType("unknown")

    fun fromName(name: String): TSType = when (name) {
      "void" -> VOID
      "number" -> NUMBER
      "string" -> STRING
      "boolean" -> BOOLEAN
      "any" -> ANY
      else -> TSType(name)
    }
  }
}
