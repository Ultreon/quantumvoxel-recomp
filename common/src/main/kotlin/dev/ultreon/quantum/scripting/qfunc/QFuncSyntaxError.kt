package dev.ultreon.quantum.scripting.qfunc

import org.antlr.v4.runtime.ParserRuleContext

class QFuncSyntaxError(message: String) : RuntimeException(message) {
  var position: Int? = null
    private set

  constructor(message: String, context: ParserRuleContext) : this("$message at ${context.start.line}:${context.start.charPositionInLine}") {
    this.position = context.start.startIndex
  }
  constructor(message: String, position: Int, line: Int, column: Int) : this("$message at $line:$column") {
    this.position = position
  }
}
