package dev.ultreon.quantum.scripting.qfunc

import org.antlr.v4.runtime.ParserRuleContext

class QFuncSyntaxError(message: String) : RuntimeException(message) {
  constructor(message: String, context: ParserRuleContext) : this("$message at ${context.start.line}:${context.start.charPositionInLine}")
}
