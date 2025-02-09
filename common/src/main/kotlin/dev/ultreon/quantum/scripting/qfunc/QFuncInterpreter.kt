@file:Suppress("UNCHECKED_CAST")

package dev.ultreon.quantum.scripting.qfunc

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.scripting.ContextAware
import dev.ultreon.quantum.scripting.ContextType
import dev.ultreon.quantum.scripting.ContextValue
import dev.ultreon.quantum.scripting.CoreUtils
import dev.ultreon.quantum.scripting.function.CallContext
import dev.ultreon.quantum.scripting.function.VirtualFunction
import kotlinx.coroutines.*
import ktx.async.MainDispatcher
import org.intellij.lang.annotations.Language
import java.util.*
import kotlin.coroutines.CoroutineContext

class QFuncInterpreterException(message: String, val filename: String, val line: Int, val column: Int) :
  Exception("$message at $filename:$line:$column")

class QFuncInternalError(message: String, val filename: String, val line: Int, val column: Int) :
  Exception("$message at $filename:$line:$column")

class QFuncInterpreter(private var inputParameters: Map<String, ContextValue<*>?>) {
  private val loopValues: Stack<ContextValue<*>> = Stack()
  private var persistObject: ContextValue<*>? = null
  private lateinit var context: CoroutineContext
  private var persistentGlobals: Map<String, ContextValue<*>?> = mapOf()
  private val stack: Stack<BacktraceElement> = Stack()
  private val globals: MutableMap<String, ContextValue<*>?> = mutableMapOf()
  var checkInput = true
  var checkPersist = false
  var state = S_NONE

  private val errors: MutableList<Throwable> = mutableListOf()

  companion object {
    @JvmStatic
    fun main(@Language("qfunc") code: String, callContext: CallContext) {
      val interpreter = QFuncInterpreter(callContext.paramValues)
      interpreter.interpret(code, callContext)
    }

    private const val S_NONE = -1
    private const val S_EXPRESSION = 0
    private const val S_ASSIGNMENT = 1
  }

  fun interpret(
    code: String,
    callContext: CallContext,
    completion: () -> Unit = {},
    error: (Throwable) -> Unit = {}
  ) {
    val lexer = QFuncLexer(code, "<dynamic>")
    val parser = QFuncParser(lexer)

    this@QFuncInterpreter.inputParameters =
      callContext.paramValues + ("core" to ContextValue(ContextType.core, CoreUtils))
    this@QFuncInterpreter.context = MainDispatcher

    try {
      runBlocking { visit(parser.file ?: return@runBlocking) }
      completion()
    } catch (e: QFuncSyntaxError) {
      logger.error(e.toString())
      error(e)
    }
  }

  suspend fun interpretAsync(
    code: String,
    callContext: CallContext,
    filename: String = "<dynamic>"
  ): ContextValue<*>? {
    try {
      val lexer = QFuncLexer(code, filename)
      val parser = QFuncParser(lexer)
      parser.parse()
      return exec(callContext, parser)
    } catch (e: QFuncSyntaxError) {
      logger.error(e.toString())
      return null
    } catch (e: QFuncParserException) {
      logger.error("Error parsing: " + e.message)
      return null
    } catch (e: Exception) {
      logger.error(e.toString() + "\n" + e.stackTraceToString())
      return null
    }

  }

  private suspend fun QFuncInterpreter.exec(
    callContext: CallContext,
    parser: QFuncParser
  ): ContextValue<*>? {
    this@QFuncInterpreter.inputParameters =
      callContext.paramValues + ("core" to ContextValue(ContextType.core, CoreUtils))
    this@QFuncInterpreter.context = MainDispatcher

    try {
      return visit(parser.file ?: return null) as? ContextValue<*>
    } catch (e: QFuncSyntaxError) {
      e.message?.let { logger.error(it + "\n" + e.stackTraceToString()) } ?: logger.error("An error occurred")
      return null
    } catch (e: QFuncParserException) {
      e.message?.let { logger.error(it + "\n" + e.stackTraceToString()) } ?: logger.error("An error occurred")
      return null
    } catch (e: QFuncInternalError) {
      e.message?.let { logger.error(it + "\n" + e.stackTraceToString()) } ?: logger.error("An error occurred")
      return null
    } catch (e: QFuncInterpreterException) {
      e.message?.let { logger.error(it + "\n" + e.stackTraceToString()) } ?: logger.error("An error occurred")
      return null
    } catch (e: Exception) {
      e.message?.let { logger.error(it + "\n" + e.stackTraceToString()) } ?: logger.error("An error occurred")
      return null
    }
  }

  private suspend fun visit(tree: AST): Any? {
    return when (tree) {
      is BlockAST -> {
        visitBlock(tree)
      }

      is StringAST -> {
        visitString(tree)
      }

      is NumberAST -> {
        visitNumber(tree)
      }

      is BooleanAST -> {
        visitBoolean(tree)
      }

      is GlobalAST -> {
        visitGlobal(tree)
      }

      is InputParamAST -> {
        visitInputParam(tree)
      }

      is MemberAST -> {
        visitMember(tree)
      }

      is AssignmentAST -> {
        visitAssignment(tree)
      }

      is FunctionCallAST -> {
        visitFunctionCall(tree)
      }

      is ArgumentAST -> {
        visitArgument(tree)
      }

      is IdAST -> {
        visitId(tree)
      }

      is TagIdAST -> {
        visitTagId(tree)
      }

      is DirectiveAST -> {
        visitDirective(tree)
      }

      is DirectiveTypeAST -> {
        visitDirectiveType(tree)
      }

      is DirectiveValueAST -> {
        visitDirectiveValue(tree)
      }

      is IfAST -> {
        visitIf(tree)
      }

      is WhileAST -> {
        visitWhile(tree)
      }

      is ForAST -> {
        visitFor(tree)
      }

      is PresentAST -> {
        visitPresent(tree)
      }

      is FileAST -> {
        visitFile(tree)
      }

      is ExpressionStatementAST -> {
        visitExpressionStatement(tree)
      }

      is BinaryOpsAST -> {
        visitBinaryOps(tree)
      }

      else -> {
        throw Exception("Unknown AST type: ${tree::class.simpleName}")
      }
    }
  }

  private suspend fun visitBinaryOps(tree: BinaryOpsAST): Any? {
    val left = visit(tree.left)
    val right = visit(tree.right)
    if (left is ContextValue<*> && right is ContextValue<*>) {
      return when (tree.type) {
        QFuncTokenType.ADD -> left + right
        QFuncTokenType.SUB -> left - right
        QFuncTokenType.MUL -> left * right
        QFuncTokenType.DIV -> left / right
        QFuncTokenType.MOD -> left % right
        QFuncTokenType.EQUALS -> ContextValue(ContextType.boolean, left.value == right.value)
        QFuncTokenType.NOT_EQUAL -> ContextValue(ContextType.boolean, left.value != right.value)
        QFuncTokenType.LESS_THAN -> ContextValue(ContextType.boolean, left < right)
        QFuncTokenType.GREATER_THAN -> ContextValue(ContextType.boolean, left > right)
        QFuncTokenType.LESS_THAN_EQUALS -> ContextValue(ContextType.boolean, left <= right)
        QFuncTokenType.GREATER_THAN_EQUALS -> ContextValue(ContextType.boolean, left >= right)
        QFuncTokenType.AND -> {
          if (left.value is Boolean && right.value is Boolean) {
            return ContextValue(ContextType.boolean, left.value && right.value)
          } else {
            throw QFuncInterpreterException("Cannot AND non-boolean values", tree.filename, tree.line, tree.column)
          }
        }

        QFuncTokenType.OR -> {
          if (left.value is Boolean && right.value is Boolean) {
            return ContextValue(ContextType.boolean, left.value || right.value)
          } else {
            throw QFuncInterpreterException("Cannot OR non-boolean values", tree.filename, tree.line, tree.column)
          }
        }

        else -> throw QFuncInterpreterException("Unknown operator: ${tree.type}", tree.filename, tree.line, tree.column)
      }
    }
    if (left !is ContextValue<*> && right !is ContextValue<*>)
      throw QFuncInterpreterException(
        "Left and right values are not ContextValues",
        tree.filename,
        tree.line,
        tree.column
      )
    if (left !is ContextValue<*>)
      throw QFuncInterpreterException("Left value is not a ContextValue", tree.filename, tree.line, tree.column)
    if (right !is ContextValue<*>)
      throw QFuncInterpreterException("Right value is not a ContextValue", tree.filename, tree.line, tree.column)

    throw unreachable()
  }

  private fun unreachable(): Throwable {
    throw AssertionError("Unreachable code")
  }

  private suspend fun visitExpressionStatement(tree: ExpressionStatementAST): Any? {
    visit(tree.expression)
    return null
  }

  private suspend fun visitFile(tree: FileAST): Any? {
    for (statement in tree.statements) {
      visit(statement)
    }
    return null
  }

  private suspend fun visitPresent(tree: PresentAST): Any? {
    return ContextValue(ContextType.boolean, visit(tree.expression) != null)
  }

  private suspend fun visitBlock(tree: BlockAST): Any? {
    for (statement in tree.statements) {
      visit(statement)
    }
    return null
  }

  private suspend fun visitString(tree: StringAST): Any? {
    return ContextValue(ContextType.string, tree.value)
  }

  private suspend fun visitNumber(tree: NumberAST): Any? {
    return when (tree.value) {
      is Int -> ContextValue(ContextType.int, tree.value)
      is Long -> ContextValue(ContextType.long, tree.value)
      is Float -> ContextValue(ContextType.float, tree.value)
      is Double -> ContextValue(ContextType.double, tree.value)
      else -> throw QFuncInterpreterException(
        "Unknown number type: ${tree.value::class.simpleName}",
        tree.filename,
        tree.line,
        tree.column
      )
    }
  }

  private suspend fun visitBoolean(tree: BooleanAST): Any? {
    return ContextValue(ContextType.boolean, tree.value)
  }

  private suspend fun visitGlobal(tree: GlobalAST): Any? {
    var value: ContextValue<*>? = globals[tree.name] ?: return null
    for (member in tree.members) {
      if (value == null) {
        throw QFuncInterpreterException("Value not present", tree.filename, tree.line, tree.column)
      }
      value = value.fieldOf(member.name, null) ?: throw QFuncInterpreterException(
        "Unknown member: ${member.name}",
        tree.filename,
        tree.line,
        tree.column
      )
      if (member.funcCall != null) {
        val callContext = visit(member.funcCall) as? CallContext ?: run {
          throw QFuncInternalError("Call context not present", tree.filename, tree.line, tree.column)
        }

        val actualValue = value.value
        if (actualValue is VirtualFunction) {
          return actualValue.call(callContext)
        } else {
          throw QFuncInterpreterException("Not a function", tree.filename, tree.line, tree.column)
        }
      }
    }

    return value
  }

  private suspend fun visitInputParam(tree: InputParamAST): Any? {
    return inputParameters[tree.name]?.let {
      var returnValue: ContextValue<*>? = it
      for (member in tree.members) {
        if (returnValue == null) {
          throw QFuncInterpreterException("Value not present", tree.filename, tree.line, tree.column)
        }

        returnValue = returnValue.fieldOf(member.name, null) ?: throw QFuncInterpreterException(
          "Unknown member: ${member.name}",
          tree.filename,
          tree.line,
          tree.column
        )

        if (member.funcCall != null) {
          val callContext = visit(member.funcCall) as? CallContext ?: run {
            throw QFuncInternalError("Call context not present", tree.filename, tree.line, tree.column)
          }

          val actualValue = returnValue.value
          if (actualValue is VirtualFunction) {
            return actualValue.call(callContext)
          } else {
            throw QFuncInterpreterException("Not a function", tree.filename, tree.line, tree.column)
          }
        }
      }

      returnValue
    }
  }

  private suspend fun visitMember(tree: MemberAST): Any? {
    throw Exception("Not supported")
  }

  private suspend fun visitAssignment(tree: AssignmentAST): Any? {
    val value = visit(tree.expression)
    globals[tree.global] = value as? ContextValue<*> ?: run {
      logger.error("Value not present at ${tree.filename}:${tree.line}:${tree.column}")
      return null
    }
    return value
  }

  private suspend fun visitFunctionCall(tree: FunctionCallAST): Any? {
    val callContext = CallContext(JsonValue(JsonValue.ValueType.nullValue))
    for (argument in tree.arguments) {
      callContext.paramValues[argument.parameterName] = visit(argument.expression) as? ContextValue<*> ?: run {
        logger.warn("Value not present at ${tree.filename}:${tree.line}:${tree.column}")
        return null
      }
    }

    return callContext
  }

  private suspend fun visitId(tree: IdAST): Any? {
    return ContextValue(ContextType.id, tree.id)
  }

  private suspend fun visitTagId(tree: TagIdAST): Any? {
    return ContextValue(ContextType.id, tree.id) // TODO
  }

  private suspend fun visitDirective(tree: DirectiveAST): Any? {
    val values = tree.values
    return when (tree.name) {
      "input" -> {
        val map = mutableMapOf<String, ContextValue<*>?>()
        for (value in values) {
          map[value.value] = inputParameters[value.value] ?: run {
            logger.error("Value not present at ${tree.filename}:${tree.line}:${tree.column}")
            return null
          }
        }
        this.inputParameters = map
        null
      }

      "persist" -> {
        val map = mutableMapOf<String, ContextValue<*>?>()
        for (value in values) {
          map[value.value] = globals[value.value]
        }
        this.persistentGlobals = map
        this.persistObject = this.inputParameters[tree.type?.name ?: run {
          throw QFuncInterpreterException("Missing input type", tree.filename, tree.line, tree.column)
        }] ?: run {
          throw QFuncInterpreterException(
            "Unknown input parameter: ${tree.type.name}",
            tree.filename,
            tree.line,
            tree.column
          )
        }
        null
      }

      else -> {
        throw Exception("Unknown directive: ${tree.name}")
      }
    }
  }

  private suspend fun visitIf(tree: IfAST): Any? {
    val condRaw = visit(tree.condition)
    val conditionValue = condRaw as? ContextValue<*> ?: run {
      throw QFuncInterpreterException("Value not present", tree.filename, tree.line, tree.column)
    }

    val condition = conditionValue.value
    if (condition !is Boolean) {
      throw QFuncInterpreterException("Condition is not a boolean", tree.filename, tree.line, tree.column)
    }

    if (condition) {
      return visit(tree.body)
    } else if (tree.elseBody != null) {
      return visit(tree.elseBody)
    } else {
      return null
    }
  }

  private suspend fun visitWhile(tree: WhileAST): Any? {
    while (true) {
      val conditionValue = visit(tree.condition) as? ContextValue<*> ?: run {
        logger.error("Value not present at ${tree.filename}:${tree.line}:${tree.column}")
        return null
      }

      val condition = conditionValue.value
      if (condition !is Boolean) {
        throw QFuncInterpreterException("Condition is not a boolean", tree.filename, tree.line, tree.column)
      }

      if (!condition) {
        break
      }

      try {
        visit(tree.body)
      } catch (e: QFuncInterpreterException) {
        logger.error("Error in while loop at ${tree.filename}:${tree.line}:${tree.column}", e)
        throw e
      } catch (e: Break) {
        break
      } catch (e: Continue) {
        continue
      }
    }

    return null
  }

  private suspend fun visitFor(tree: ForAST): Any? {
    val iterableValue = visit(tree.iterable) as? ContextValue<*> ?: run {
      logger.error("Value not present at ${tree.filename}:${tree.line}:${tree.column}")
      return null
    }

    val iterable = iterableValue.value
    if (iterable !is Iterable<*>) {
      throw QFuncInterpreterException("Iterable is not iterable", tree.filename, tree.line, tree.column)
    }

    for (item in iterable) {
      try {
        this.loopValues.push(ContextValue(ContextType[(item!!)::class] as ContextType<*>, item))
        visit(tree.body)
        this.loopValues.pop()
      } catch (e: QFuncInterpreterException) {
        logger.error("Error in for loop at ${tree.filename}:${tree.line}:${tree.column}", e)
        throw e
      } catch (e: Break) {
        break
      } catch (e: Continue) {
        continue
      }
    }

    return null
  }

  private suspend fun visitBreak(tree: BreakAST): Any? {
    throw Break
  }

  private suspend fun visitContinue(tree: ContinueAST): Any? {
    throw Continue
  }

  private suspend fun visitReturn(tree: ReturnAST): Any? {
    throw Return(visit(tree.expression ?: throw Return(null)) as? ContextValue<*> ?: run {
      throw QFuncInterpreterException("Value not present", tree.filename, tree.line, tree.column)
    })
  }

  private suspend fun visitStop(tree: StopAST): Any? {
    throw Stop
  }

  object Break : Exception() {
    private fun readResolve(): Any = Break
  }

  object Continue : Exception() {
    private fun readResolve(): Any = Continue
  }

  class Return(val value: ContextValue<*>?) : Exception()
  object Stop : Exception() {
    private fun readResolve(): Any = Stop
  }

  private suspend fun visitDirectiveType(tree: DirectiveTypeAST): Any? {
    throw Exception("Not supported")
  }

  private suspend fun visitDirectiveValue(tree: DirectiveValueAST): Any? {
    throw Exception("Not supported")
  }

  private suspend fun visitArgument(tree: ArgumentAST): Any? {
    return tree.parameterName to visit(tree.expression)
  }
}

private fun ContextValue<*>.fieldOf(text: String, contextJson: JsonValue?): ContextValue<*>? {
  return if (value is ContextAware<*>)
    value.fieldOf(text, contextJson)
  else null
}

private operator fun ContextValue<*>.compareTo(other: ContextValue<*>): Int {
  return when (this.value) {
    is Int -> when (other.value) {
      is Int -> this.value.compareTo(other.value)
      is Long -> this.value.compareTo(other.value.toInt())
      is Float -> this.value.compareTo(other.value.toFloat())
      is Double -> this.value.compareTo(other.value.toDouble())
      else -> throw UnsupportedOperationException("Cannot compare ${this.type} to ${other.type}")
    }

    is Long -> when (other.value) {
      is Int -> this.value.compareTo(other.value.toLong())
      is Long -> this.value.compareTo(other.value)
      is Float -> this.value.compareTo(other.value.toFloat())
      is Double -> this.value.compareTo(other.value.toDouble())
      else -> throw UnsupportedOperationException("Cannot compare ${this.type} to ${other.type}")
    }

    is Float -> when (other.value) {
      is Int -> this.value.compareTo(other.value.toFloat())
      is Long -> this.value.compareTo(other.value.toFloat())
      is Float -> this.value.compareTo(other.value)
      is Double -> this.value.compareTo(other.value.toDouble())
      else -> throw UnsupportedOperationException("Cannot compare ${this.type} to ${other.type}")
    }

    is Double -> when (other.value) {
      is Int -> this.value.compareTo(other.value.toDouble())
      is Long -> this.value.compareTo(other.value.toDouble())
      is Float -> this.value.compareTo(other.value.toDouble())
      is Double -> this.value.compareTo(other.value)
      else -> throw UnsupportedOperationException("Cannot compare ${this.type} to ${other.type}")
    }

    is String -> when (other.value) {
      is String -> this.value.toString().compareTo(other.value.toString())
      else -> 0
    }

    else -> 0
  }
}

private operator fun ContextValue<*>.plus(other: ContextValue<*>): ContextValue<*> {
  return when (this.value) {
    is Int -> when (other.value) {
      is Int -> ContextValue(ContextType.int, this.value + other.value)
      is Long -> ContextValue(ContextType.long, this.value + other.value)
      is Float -> ContextValue(ContextType.float, this.value + other.value)
      is Double -> ContextValue(ContextType.double, this.value + other.value)
      else -> throw UnsupportedOperationException("Cannot add ${this.type} to ${other.type}")
    }

    is Long -> when (other.value) {
      is Int -> ContextValue(ContextType.long, this.value + other.value.toLong())
      is Long -> ContextValue(ContextType.long, this.value + other.value)
      is Float -> ContextValue(ContextType.float, this.value + other.value)
      is Double -> ContextValue(ContextType.double, this.value + other.value)
      else -> throw UnsupportedOperationException("Cannot add ${this.type} to ${other.type}")
    }

    is Float -> when (other.value) {
      is Int -> ContextValue(ContextType.float, this.value + other.value.toFloat())
      is Long -> ContextValue(ContextType.float, this.value + other.value.toFloat())
      is Float -> ContextValue(ContextType.float, this.value + other.value)
      is Double -> ContextValue(ContextType.double, this.value + other.value)
      else -> throw UnsupportedOperationException("Cannot add ${this.type} to ${other.type}")
    }

    is Double -> when (other.value) {
      is Int -> ContextValue(ContextType.double, this.value + other.value.toDouble())
      is Long -> ContextValue(ContextType.double, this.value + other.value.toDouble())
      is Float -> ContextValue(ContextType.double, this.value + other.value.toDouble())
      is Double -> ContextValue(ContextType.double, this.value + other.value)
      else -> throw UnsupportedOperationException("Cannot add ${this.type} to ${other.type}")
    }

    is String -> ContextValue(ContextType.string, this.value + other.value)
    else -> throw UnsupportedOperationException("Cannot add ${this.type} to ${other.type}")
  }
}

private operator fun ContextValue<*>.minus(other: ContextValue<*>): ContextValue<*> {
  return when (this.value) {
    is Int -> when (other.value) {
      is Int -> ContextValue(ContextType.int, this.value - other.value)
      is Long -> ContextValue(ContextType.long, this.value - other.value)
      is Float -> ContextValue(ContextType.float, this.value - other.value)
      is Double -> ContextValue(ContextType.double, this.value - other.value)
      else -> throw UnsupportedOperationException("Cannot subtract ${this.type} to ${other.type}")
    }

    is Long -> when (other.value) {
      is Int -> ContextValue(ContextType.long, this.value - other.value.toLong())
      is Long -> ContextValue(ContextType.long, this.value - other.value)
      is Float -> ContextValue(ContextType.float, this.value - other.value)
      is Double -> ContextValue(ContextType.double, this.value - other.value)
      else -> throw UnsupportedOperationException("Cannot subtract ${this.type} to ${other.type}")
    }

    is Float -> when (other.value) {
      is Int -> ContextValue(ContextType.float, this.value - other.value.toFloat())
      is Long -> ContextValue(ContextType.float, this.value - other.value.toFloat())
      is Float -> ContextValue(ContextType.float, this.value - other.value)
      is Double -> ContextValue(ContextType.double, this.value - other.value)
      else -> throw UnsupportedOperationException("Cannot subtract ${this.type} to ${other.type}")
    }

    is Double -> when (other.value) {
      is Int -> ContextValue(ContextType.double, this.value - other.value.toDouble())
      is Long -> ContextValue(ContextType.double, this.value - other.value.toDouble())
      is Float -> ContextValue(ContextType.double, this.value - other.value.toDouble())
      is Double -> ContextValue(ContextType.double, this.value - other.value)
      else -> throw UnsupportedOperationException("Cannot subtract ${this.type} to ${other.type}")
    }

    else -> throw UnsupportedOperationException("Cannot subtract ${this.type} to ${other.type}")
  }
}

private operator fun ContextValue<*>.times(other: ContextValue<*>): ContextValue<*> {
  return when (this.value) {
    is Int -> when (other.value) {
      is Int -> ContextValue(ContextType.int, this.value * other.value)
      is Long -> ContextValue(ContextType.long, this.value * other.value)
      is Float -> ContextValue(ContextType.float, this.value * other.value)
      is Double -> ContextValue(ContextType.double, this.value * other.value)
      else -> throw UnsupportedOperationException("Cannot multiply ${this.type} to ${other.type}")
    }

    is Long -> when (other.value) {
      is Int -> ContextValue(ContextType.long, this.value * other.value.toLong())
      is Long -> ContextValue(ContextType.long, this.value * other.value)
      is Float -> ContextValue(ContextType.float, this.value * other.value)
      is Double -> ContextValue(ContextType.double, this.value * other.value)
      else -> throw UnsupportedOperationException("Cannot multiply ${this.type} to ${other.type}")
    }

    is Float -> when (other.value) {
      is Int -> ContextValue(ContextType.float, this.value * other.value.toFloat())
      is Long -> ContextValue(ContextType.float, this.value * other.value.toFloat())
      is Float -> ContextValue(ContextType.float, this.value * other.value)
      is Double -> ContextValue(ContextType.double, this.value * other.value)
      else -> throw UnsupportedOperationException("Cannot multiply ${this.type} to ${other.type}")
    }

    is Double -> when (other.value) {
      is Int -> ContextValue(ContextType.double, this.value * other.value.toDouble())
      is Long -> ContextValue(ContextType.double, this.value * other.value.toDouble())
      is Float -> ContextValue(ContextType.double, this.value * other.value.toDouble())
      is Double -> ContextValue(ContextType.double, this.value * other.value)
      else -> throw UnsupportedOperationException("Cannot multiply ${this.type} to ${other.type}")
    }

    is String -> when (other.value) {
      is Int -> ContextValue(ContextType.string, this.value.repeat(other.value))
      is Long -> throw UnsupportedOperationException("Too big to repeat")
      else -> throw UnsupportedOperationException("Cannot multiply ${this.type} to ${other.type}")
    }

    else -> throw UnsupportedOperationException("Cannot multiply ${this.type} to ${other.type}")
  }
}

private operator fun ContextValue<*>.div(other: ContextValue<*>): ContextValue<*> {
  return when (this.value) {
    is Int -> when (other.value) {
      is Int -> ContextValue(ContextType.int, this.value / other.value)
      is Long -> ContextValue(ContextType.long, this.value / other.value)
      is Float -> ContextValue(ContextType.float, this.value / other.value)
      is Double -> ContextValue(ContextType.double, this.value / other.value)
      else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
    }

    is Long -> when (other.value) {
      is Int -> ContextValue(ContextType.long, this.value / other.value.toLong())
      is Long -> ContextValue(ContextType.long, this.value / other.value)
      is Float -> ContextValue(ContextType.float, this.value / other.value)
      is Double -> ContextValue(ContextType.double, this.value / other.value)
      else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
    }

    is Float -> when (other.value) {
      is Int -> ContextValue(ContextType.float, this.value / other.value.toFloat())
      is Long -> ContextValue(ContextType.float, this.value / other.value.toFloat())
      is Float -> ContextValue(ContextType.float, this.value / other.value)
      is Double -> ContextValue(ContextType.double, this.value / other.value)
      else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
    }

    is Double -> when (other.value) {
      is Int -> ContextValue(ContextType.double, this.value / other.value.toDouble())
      is Long -> ContextValue(ContextType.double, this.value / other.value.toDouble())
      is Float -> ContextValue(ContextType.double, this.value / other.value.toDouble())
      is Double -> ContextValue(ContextType.double, this.value / other.value)
      else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
    }

    else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
  }
}

private operator fun ContextValue<*>.rem(other: ContextValue<*>): ContextValue<*> {
  return when (this.value) {
    is Int -> when (other.value) {
      is Int -> ContextValue(ContextType.int, this.value % other.value)
      is Long -> ContextValue(ContextType.long, this.value % other.value)
      is Float -> ContextValue(ContextType.float, this.value % other.value)
      is Double -> ContextValue(ContextType.double, this.value % other.value)
      else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
    }

    is Long -> when (other.value) {
      is Int -> ContextValue(ContextType.long, this.value % other.value.toLong())
      is Long -> ContextValue(ContextType.long, this.value % other.value)
      is Float -> ContextValue(ContextType.float, this.value % other.value)
      is Double -> ContextValue(ContextType.double, this.value % other.value)
      else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
    }

    is Float -> when (other.value) {
      is Int -> ContextValue(ContextType.float, this.value % other.value.toFloat())
      is Long -> ContextValue(ContextType.float, this.value % other.value.toFloat())
      is Float -> ContextValue(ContextType.float, this.value % other.value)
      is Double -> ContextValue(ContextType.double, this.value % other.value)
      else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
    }

    is Double -> when (other.value) {
      is Int -> ContextValue(ContextType.double, this.value % other.value.toDouble())
      is Long -> ContextValue(ContextType.double, this.value % other.value.toDouble())
      is Float -> ContextValue(ContextType.double, this.value % other.value.toDouble())
      is Double -> ContextValue(ContextType.double, this.value % other.value)
      else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
    }

    else -> throw UnsupportedOperationException("Cannot divide ${this.type} to ${other.type}")
  }
}
