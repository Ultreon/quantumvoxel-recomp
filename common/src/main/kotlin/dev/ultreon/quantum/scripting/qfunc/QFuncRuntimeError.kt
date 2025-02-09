package dev.ultreon.quantum.scripting.qfunc

import org.antlr.v4.runtime.ParserRuleContext
import java.io.PrintStream
import java.io.PrintWriter

@Suppress("MemberVisibilityCanBePrivate")
class QFuncRuntimeError(override val message: String, val backtrace: List<BacktraceElement>) : RuntimeException() {
  constructor(message: String, context: ParserRuleContext, backtrace: List<BacktraceElement>) : this("$message at ${context.start.line}:${context.start.charPositionInLine}", backtrace)

  override fun printStackTrace(s: PrintStream?) {
    s?.println("QFunc Backtrace:" + backtrace.joinToString("\n").prependIndent("  ") + "\nRuntime error: $message")
  }

  override fun printStackTrace(s: PrintWriter?) {
    s?.println("QFunc Backtrace:" + backtrace.joinToString("\n").prependIndent("  ") + "\nRuntime error: $message")
  }
}
