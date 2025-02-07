package dev.ultreon.quantum.scripting.qfunc

data class BacktraceElement(
  val codeSource: String,
  val file: String,
  val line: Int,
  val charPositionInLine: Int,
  val function: String
) {
  override fun toString(): String {
    return """
      |$file:$line:$charPositionInLine in function $function:
      |${codeSource.prependIndent("  ")}
    """.trimMargin()
  }
}
