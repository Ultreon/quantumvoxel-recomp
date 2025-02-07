@file:Suppress("UNCHECKED_CAST")

package dev.ultreon.quantum.scripting.qfunc

import dev.ultreon.quantum.scripting.ContextType
import dev.ultreon.quantum.scripting.ContextValue
import dev.ultreon.quantum.scripting.function.CallContext
import dev.ultreon.quantum.scripting.function.VirtualFunction
import dev.ultreon.quantum.util.NamespaceID
import kotlinx.coroutines.*
import ktx.async.KtxAsync
import ktx.async.MainDispatcher
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode
import org.intellij.lang.annotations.Language
import java.util.Stack
import kotlin.coroutines.CoroutineContext

class QFuncInterpreter(private var inputParameters: Map<String, ContextValue<*>?>) : QuantumParserBaseVisitor<Job>() {
  private lateinit var context: CoroutineContext
  private lateinit var persistentGlobals: Map<String, ContextValue<*>?>
  private val stack: Stack<BacktraceElement> = Stack()
  private val globals: MutableMap<String, ContextValue<*>?> = mutableMapOf()
  var checkInput = true
  var checkPersist = false
  var state = S_NONE

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
    val lexer = QuantumLexer(CharStreams.fromString(code))
    val parser = QuantumParser(CommonTokenStream(lexer))

    this@QFuncInterpreter.inputParameters = callContext.paramValues
    this@QFuncInterpreter.context = MainDispatcher

    val visitAsync = KtxAsync.async { visitAsync(parser.file()) }
    visitAsync.invokeOnCompletion {
      if (it != null) {
        error(it)
        return@invokeOnCompletion
      }
      completion()
    }
  }

  suspend fun interpretAsync(
    code: String,
    callContext: CallContext
  ): ContextValue<*>? {
    val lexer = QuantumLexer(CharStreams.fromString(code))
    val parser = QuantumParser(CommonTokenStream(lexer))

    this@QFuncInterpreter.inputParameters = callContext.paramValues
    this@QFuncInterpreter.context = MainDispatcher

    return visitAsync(parser.file()) as? ContextValue<*>
  }

  override fun visit(tree: ParseTree): Job = KtxAsync.async {
    val result = super.visit(tree)

    if (result != null) {
      return@async result
    }
    throw UnsupportedOperationException("Unknown tree: ${tree::class.simpleName}")
  }

  suspend fun visitAsync(tree: ParseTree): Any {
    val result = visit(tree)

    yield()

    if (result is Deferred<*>) {
      return result.await() ?: run {
        throw UnsupportedOperationException("Unknown tree: ${tree::class.simpleName}")
      }
    }

    return result.join()
  }

  override fun visitFile(ctx: QuantumParser.FileContext): Job = KtxAsync.async {
    if (ctx.statement().isEmpty()) {
      return@async Unit
    }

    if (ctx.statement().size == 1) {
      this@QFuncInterpreter.visitAsync(ctx.statement(0)) as Job
      return@async Unit
    }

    return@async ctx.statement().map { this@QFuncInterpreter.visitAsync(it) as Job }.reduce { a, b ->
      a.also {
        return@async KtxAsync.async {
          a.join()
          b.join()
        }
      }
    }
  }

  override fun visitStatement(ctx: QuantumParser.StatementContext): Job = KtxAsync.async {
    if (ctx.inputStatement() == null) {
      checkInput = false
      checkPersist = true
    } else if
             (checkInput) return@async this@QFuncInterpreter.visitAsync(ctx.inputStatement())
    else
      throw QFuncSyntaxError("#input statements should be at the top of the script")

    if (ctx.persistStatement() != null) {
      checkPersist = false
    } else if (checkPersist)
      return@async this@QFuncInterpreter.visitAsync(ctx.persistStatement())
    else
      throw QFuncSyntaxError("#persist statements should be at the top of the script directly after the #input statement")

    if (ctx.ifStatement() != null) return@async visitAsync(ctx.ifStatement())
    if (ctx.forStatement() != null) return@async visitAsync(ctx.forStatement())
    if (ctx.whileStatement() != null) return@async visitAsync(ctx.whileStatement())
    if (ctx.functionCall() != null) return@async visitAsync(ctx.functionCall())
    if (ctx.returnStatement() != null) return@async visitAsync(ctx.returnStatement())
    if (ctx.stopStatement() != null) return@async visitAsync(ctx.stopStatement())
    if (ctx.expressionStatement() != null) return@async visitAsync(ctx.expressionStatement())
    if (ctx.loopStatement() != null) return@async visitAsync(ctx.loopStatement())
    if (ctx.stopStatement() != null) return@async visitAsync(ctx.stopStatement())
    if (ctx.continueStatement() != null) return@async visitAsync(ctx.continueStatement())
    if (ctx.breakStatement() != null) return@async visitAsync(ctx.breakStatement())
    if (ctx.assignment() != null) return@async visitAsync(ctx.assignment())
    if (ctx.lineComment() != null) return@async Unit

    throw UnsupportedOperationException("Unknown statement: ${ctx.children[0].text}")
  }

  override fun visitIfStatement(ctx: QuantumParser.IfStatementContext): Job = KtxAsync.async {
    val visit = this@QFuncInterpreter.visitAsync(ctx.condition().expression())
    if (visit is Boolean) {
      if (visit) this@QFuncInterpreter.visitAsync(ctx.statement(0)) else if (ctx.statement().size > 1) this@QFuncInterpreter.visitAsync(
        ctx.statement(1)
      )
      return@async Unit
    }
    throw QFuncSyntaxError("Expected a boolean condition")
  }

  override fun visitForStatement(ctx: QuantumParser.ForStatementContext): Job = KtxAsync.async {
    TODO("Not yet implemented")
  }

  override fun visitExpression(ctx: QuantumParser.ExpressionContext): Job = KtxAsync.async {
    this@QFuncInterpreter.state = S_EXPRESSION
    try {
      val group = ctx.group()
      if (group != null) return@async this@QFuncInterpreter.visitAsync(group)
      throw UnsupportedOperationException("Unknown expression: ${ctx.children[0].text}")
    } finally {
      this@QFuncInterpreter.state = S_NONE
    }
  }

  override fun visitGroup(ctx: QuantumParser.GroupContext): Job = KtxAsync.async {
    return@async this@QFuncInterpreter.visitAsync(ctx.andExpr())
  }

  override fun visitAndExpr(ctx: QuantumParser.AndExprContext): Job = KtxAsync.async {
    val orExpr = this@QFuncInterpreter.visitAsync(ctx.orExpr())
    if (orExpr is Boolean) {
      if (!orExpr) return@async false

      val andExpr = this@QFuncInterpreter.visitAsync(ctx.andExpr())
      if (andExpr is Boolean) {
        return@async andExpr
      }
      throw QFuncSyntaxError("Expected a boolean condition")
    }

    if (ctx.andExpr() != null) {
      throw QFuncSyntaxError("Expected a boolean condition", ctx)
    }

    return@async orExpr
  }

  override fun visitOrExpr(ctx: QuantumParser.OrExprContext): Job = KtxAsync.async {
    val andExpr = this@QFuncInterpreter.visitAsync(ctx.negationExpr())
    if (andExpr is Boolean) {
      if (andExpr) return@async true

      val orExpr = this@QFuncInterpreter.visitAsync(ctx.orExpr())
      if (orExpr is Boolean) {
        return@async orExpr
      }
      throw QFuncSyntaxError("Expected a boolean condition")
    }

    if (ctx.orExpr() != null) {
      throw QFuncSyntaxError("Expected a boolean condition", ctx)
    }

    return@async andExpr
  }

  override fun visitNegationExpr(ctx: QuantumParser.NegationExprContext): Job = KtxAsync.async {
    val negationExpr = ctx.negationExpr()
    if (negationExpr != null) {
      val visit = this@QFuncInterpreter.visitAsync(negationExpr)
      if (visit is Boolean) {
        return@async !visit
      }
      throw QFuncSyntaxError("Expected a boolean condition")
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.equalityExpr())
  }

  override fun visitEqualityExpr(ctx: QuantumParser.EqualityExprContext): Job = KtxAsync.async {
    val equalityExpr = ctx.equalityExpr()
    if (equalityExpr != null) {
      val visit = this@QFuncInterpreter.visitAsync(equalityExpr)
      return@async visit == this@QFuncInterpreter.visitAsync(ctx.relationalExpr())
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.relationalExpr())
  }

  override fun visitRelationalExpr(ctx: QuantumParser.RelationalExprContext): Job = KtxAsync.async {
    val relationalExpr = ctx.relationalExpr()
    if (relationalExpr != null) {
      val visit = this@QFuncInterpreter.visitAsync(relationalExpr)
      if (visit is Comparable<*>) {
        val visit2 = this@QFuncInterpreter.visitAsync(ctx.bitwiseAndExpr())
        if (visit2 is Comparable<*>) {
          if (visit.javaClass == visit2.javaClass) {
            val comparison = (visit as Comparable<Any>).compareTo(visit2 as Comparable<Any>)

            return@async if (ctx.LESS_THAN() != null) comparison < 0
            else if (ctx.GREATER_THAN() != null) comparison > 0
            else if (ctx.LESS_THAN_EQUAL() != null) comparison <= 0
            else if (ctx.GREATER_THAN_EQUAL() != null) comparison >= 0
            else throw QFuncSyntaxError("Expected a comparison operator")
          } else {
            throw QFuncSyntaxError("Cannot compare ${visit.javaClass.simpleName} with ${visit2.javaClass.simpleName}")
          }
        } else {
          throw QFuncSyntaxError("Expected a comparable")
        }
      } else {
        throw QFuncSyntaxError("Expected a comparable")
      }
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.bitwiseAndExpr())
  }

  override fun visitBitwiseAndExpr(ctx: QuantumParser.BitwiseAndExprContext): Job = KtxAsync.async {
    val bitwiseAndExpr = ctx.bitwiseAndExpr()
    if (bitwiseAndExpr != null) {
      val visit = this@QFuncInterpreter.visitAsync(bitwiseAndExpr)
      if (visit is Int) {
        val and = this@QFuncInterpreter.visitAsync(ctx.bitwiseOrExpr())
        if (and is Int) {
          return@async visit and and
        }
        throw QFuncSyntaxError("Expected an integer")
      }
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.bitwiseOrExpr())
  }

  override fun visitBitwiseOrExpr(ctx: QuantumParser.BitwiseOrExprContext): Job = KtxAsync.async {
    val bitwiseOrExpr = ctx.bitwiseOrExpr()
    if (bitwiseOrExpr != null) {
      val visit = this@QFuncInterpreter.visitAsync(bitwiseOrExpr)
      if (visit is Int) {
        val or = this@QFuncInterpreter.visitAsync(ctx.bitwiseXorExpr())
        if (or is Int) {
          return@async visit or or
        }
        throw QFuncSyntaxError("Expected an integer")
      }
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.bitwiseXorExpr())
  }

  override fun visitBitwiseXorExpr(ctx: QuantumParser.BitwiseXorExprContext): Job = KtxAsync.async {
    val bitwiseXorExpr = ctx.bitwiseXorExpr()
    if (bitwiseXorExpr != null) {
      val visit = this@QFuncInterpreter.visitAsync(bitwiseXorExpr)
      if (visit is Int) {
        val xor = this@QFuncInterpreter.visitAsync(ctx.bitwiseNotExpr())
        if (xor is Int) {
          return@async visit xor xor
        }
        throw QFuncSyntaxError("Expected an integer")
      }
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.bitwiseNotExpr())
  }

  override fun visitBitwiseNotExpr(ctx: QuantumParser.BitwiseNotExprContext): Job = KtxAsync.async {
    val bitwiseNotExpr = ctx.bitwiseNotExpr()
    if (bitwiseNotExpr != null) {
      val visit = this@QFuncInterpreter.visitAsync(bitwiseNotExpr)
      if (visit is Int) {
        return@async visit.inv()
      }
      throw QFuncSyntaxError("Expected an integer")
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.shiftExpr())
  }

  override fun visitShiftExpr(ctx: QuantumParser.ShiftExprContext): Job = KtxAsync.async {
    val shiftExpr: MutableList<QuantumParser.ShiftExprContext> = ctx.shiftExpr()
    val multExpr = this@QFuncInterpreter.visitAsync(ctx.multExpr())
    if (shiftExpr.size > 1) {
      var value = this@QFuncInterpreter.visitAsync(shiftExpr[0])
      if (value !is Int) {
        throw QFuncSyntaxError("Expected an integer", shiftExpr[0])
      }
      for (i in 1 until ctx.children.size step 2) {
        val operator = ctx.children[i] as TerminalNode
        if (shiftExpr.size <= i + 1) {
          throw QFuncSyntaxError("Expected an integer after ${operator.text}", shiftExpr[i])
        }
        val visit = this@QFuncInterpreter.visitAsync(shiftExpr[i + 1])
        if (visit !is Int) {
          throw QFuncSyntaxError("Expected an integer", shiftExpr[i + 1])
        }
        value = if (operator.text == ">>") {
          (value as Int) shr visit
        } else {
          (value as Int) shl visit
        }
      }

      if (value !is Int) throw QFuncSyntaxError("Expected an integer", shiftExpr[ctx.children.size - 1])
      if (multExpr !is Int) throw QFuncSyntaxError("Expected an integer", ctx)
      return@async multExpr * value
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.multExpr())
  }

  override fun visitMultExpr(ctx: QuantumParser.MultExprContext): Job = KtxAsync.async {
    val multExpr: MutableList<QuantumParser.MultExprContext> = ctx.multExpr()
    val unaryExpr = this@QFuncInterpreter.visitAsync(ctx.addExpr())
    if (multExpr.size > 1) {
      var value = this@QFuncInterpreter.visitAsync(multExpr[0])
      if (value !is Int) {
        throw QFuncSyntaxError("Expected an integer", multExpr[0])
      }
      for (i in 1 until ctx.children.size step 2) {
        val operator = ctx.children[i] as TerminalNode
        if (multExpr.size <= i + 1) {
          throw QFuncSyntaxError("Expected an integer after ${operator.text}", multExpr[i])
        }
        val visit = this@QFuncInterpreter.visitAsync(multExpr[i + 1])
        if (visit !is Int) {
          throw QFuncSyntaxError("Expected an integer", multExpr[i + 1])
        }
        value = when (operator.text) {
          "*" -> (value as Int) * visit
          "/" -> (value as Int) / visit
          "%" -> (value as Int) % visit
          else -> throw QFuncSyntaxError("Unexpected operator ${operator.text}", ctx)
        }
      }

      if (value !is Int) throw QFuncSyntaxError("Expected an integer", multExpr[ctx.children.size - 1])
      if (unaryExpr !is Int) throw QFuncSyntaxError("Expected an integer", ctx)
      return@async unaryExpr * value
    }

    return@async this@QFuncInterpreter.visitAsync(ctx.addExpr())
  }

  override fun visitAddExpr(ctx: QuantumParser.AddExprContext): Job = KtxAsync.async {
    val addExpr: MutableList<QuantumParser.AddExprContext> = ctx.addExpr()
    val unaryExpr = this@QFuncInterpreter.visitAsync(ctx.primary())
    if (addExpr.size > 1) {
      var value = this@QFuncInterpreter.visitAsync(addExpr[0])
      if (value !is Int) {
        throw QFuncSyntaxError("Expected an integer", addExpr[0])
      }
      for (i in 1 until ctx.children.size step 2) {
        val operator = ctx.children[i] as TerminalNode
        if (addExpr.size <= i + 1) {
          throw QFuncSyntaxError("Expected an integer after ${operator.text}", addExpr[i])
        }
        val visit = this@QFuncInterpreter.visitAsync(addExpr[i + 1])
        if (visit !is Int) {
          throw QFuncSyntaxError("Expected an integer", addExpr[i + 1])
        }
        value = when (operator.text) {
          "+" -> (value as Int) + visit
          "-" -> (value as Int) - visit
          else -> throw QFuncSyntaxError("Unexpected operator ${operator.text}", ctx)
        }
      }

      if (value !is Int) throw QFuncSyntaxError("Expected an integer", addExpr[ctx.children.size - 1])
      if (unaryExpr !is Int) throw QFuncSyntaxError("Expected an integer", ctx)
      return@async unaryExpr * value
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.primary())
  }

  override fun visitPrimary(ctx: QuantumParser.PrimaryContext): Job = KtxAsync.async {
    return@async this@QFuncInterpreter.visitAsync(ctx.atom())
  }

  override fun visitAtom(ctx: QuantumParser.AtomContext): Job = KtxAsync.async {
    val id = ctx.id()
    if (id != null) {
      val visit = this@QFuncInterpreter.visitAsync(id)
      if (visit is NamespaceID) {
        return@async visit
      }
      throw QFuncSyntaxError("Expected a namespaced id", id)
    }

    val literal = ctx.NUMBER()
    if (literal != null) {
      return@async literal.text.toIntOrNull() ?: literal.text.toLongOrNull() ?: literal.text.toFloatOrNull()
      ?: literal.text.toDoubleOrNull()
    }

    val string = ctx.STRING()
    if (string != null) {
      return@async string.text
    }

    val boolean = ctx.BOOLEAN()
    if (boolean != null) {
      return@async boolean.text.toBoolean()
    }

    val floatingPoint = ctx.FLOATING_POINT()
    if (floatingPoint != null) {
      return@async floatingPoint.text.toFloatOrNull() ?: floatingPoint.text.toDoubleOrNull()
    }

    throw QFuncSyntaxError("Expected an atom", ctx)
  }

  override fun visitId(ctx: QuantumParser.IdContext): Job = KtxAsync.async {
    val hash = ctx.HASH()
    val lbracket = ctx.LBRACKET()
    val rbracket = ctx.RBRACKET()

    if (lbracket != null && rbracket != null) {
      if (hash != null) {
        val namespace = ctx.namespace()
        val path = ctx.path()
        if (namespace != null && path != null) {
          return@async TagID(namespace.text, path.text)
        }

        throw QFuncSyntaxError("Expected a domain and path in the form domain:path", ctx)
      }

      val namespace = ctx.namespace()
      val path = ctx.path()
      if (namespace != null && path != null) {
        return@async NamespaceID(namespace.text, path.text)
      }

      throw QFuncSyntaxError("Expected a domain and path in the form domain:path", ctx)
    }

    throw QFuncSyntaxError("Expected an id", ctx)
  }

  override fun visitNamespace(ctx: QuantumParser.NamespaceContext): Job = KtxAsync.async {
    return@async ctx.text
  }

  override fun visitPath(ctx: QuantumParser.PathContext): Job = KtxAsync.async {
    return@async ctx.text
  }

  override fun visitNamedAtom(ctx: QuantumParser.NamedAtomContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    val variableName = ctx.variableName()
    if (variableName != null) {
      TODO("Not yet implemented")
    }

    val globalRef = ctx.globalRef()
    if (globalRef.PRESENT() != null) {
      val name = globalRef.globalExpr().globalName().text
      return@async ContextValue(ContextType.boolean, name in globals)
    } else if (globalRef != null) {
      val name = globalRef.globalExpr().globalName().text

      if (this@QFuncInterpreter.state == S_ASSIGNMENT) {
        if (ctx.member().isNotEmpty()) {
          throw QFuncSyntaxError("Cannot assign to a member of a global", ctx)
        }

        return@async { value: ContextValue<*>? ->
          this@QFuncInterpreter.globals[name] = value
        }
      }

      var value = this@QFuncInterpreter.globals[name]
      for (member in ctx.member()) {
        val functionCall = member.functionCall()
        if (functionCall != null) {
          val map = this@QFuncInterpreter.visitAsync(functionCall.argumentList()) as? Map<String, ContextValue<*>?>
          value = (value?.type?.fieldOf(
            functionCall.funcName().text,
            null
          ) as? (Map<String, ContextValue<*>?>) -> ContextValue<*>?)?.invoke(map ?: emptyMap())
          continue
        }
        value = value?.type?.fieldOf(member.variableName().text, null)
      }

      return@async value
    }

    val paramName = ctx.parameterExpr()
    if (paramName != null) {
      val name = paramName.paramName().text

      if (this@QFuncInterpreter.state == S_ASSIGNMENT) {
        throw QFuncSyntaxError("Cannot assign to a parameter", ctx)
      }

      var value = this@QFuncInterpreter.inputParameters[name]
      for (member in ctx.member()) {
        val functionCall = member.functionCall()
        if (functionCall != null) {
          val map = this@QFuncInterpreter.visitAsync(functionCall.argumentList()) as? Map<String, ContextValue<*>>
          value = ((value?.type?.fieldOf(
            functionCall.funcName().text,
            null
          ))?.value as? VirtualFunction)?.call(CallContext().also {
            it.paramValues.putAll(map ?: emptyMap())
          })
          continue
        }
        value = value?.type?.fieldOf(member.variableName().text, null)
      }

      return@async value
    }

    throw QFuncSyntaxError("Expected a global name or parameter name", ctx)
  }

  override fun visitArgumentList(ctx: QuantumParser.ArgumentListContext?): Job = KtxAsync.async {
    val map = HashMap<String, ContextValue<*>?>()
    for (argument in ctx!!.argumentExpr()) {
      val name = argument.argumentName().text
      val value = this@QFuncInterpreter.visitAsync(argument.expression())
      map[name] = value as? ContextValue<*>
    }
    return@async map
  }

  override fun visitFunctionCall(ctx: QuantumParser.FunctionCallContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async this@QFuncInterpreter.visitAsync(ctx.funcName())
  }

  override fun visitFuncName(ctx: QuantumParser.FuncNameContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async ctx.text
  }

  override fun visitBlockStatement(ctx: QuantumParser.BlockStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    for (statement in ctx.statement()) {
      this@QFuncInterpreter.visitAsync(statement)
    }
    return@async Unit
  }

  override fun visitMember(ctx: QuantumParser.MemberContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    if (ctx.variableName() != null) {
      return@async ctx.variableName().text
    }
    return@async this@QFuncInterpreter.visitAsync(ctx.functionCall())
  }

  override fun visitInputStatement(ctx: QuantumParser.InputStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    val map = HashMap<String, ContextValue<*>?>()
    ctx.globalName().forEach {
      if (!inputParameters.containsKey(it.text))
        throw QFuncSyntaxError("Input parameter $it cannot be referred in this context!", ctx)
      map[it.text] = inputParameters[it.text]
    }
    this@QFuncInterpreter.inputParameters = map
    return@async Unit
  }

  override fun visitPersistStatement(ctx: QuantumParser.PersistStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    val map = HashMap<String, ContextValue<*>?>()
    ctx.globalName().forEach { map[it.text] = globals[it.text] }
    this@QFuncInterpreter.persistentGlobals = map
    return@async Unit
  }

  @Suppress("UNCHECKED_CAST")
  override fun visitAssignment(ctx: QuantumParser.AssignmentContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    this@QFuncInterpreter.state = S_EXPRESSION
    try {
      val value = this@QFuncInterpreter.visitAsync(ctx.expression()) as? (ContextValue<*>?) -> Unit
      this@QFuncInterpreter.state = S_ASSIGNMENT

      val globalName = ctx.globalExpr().globalName()
      if (globalName != null) {
        val name = globalName.text
        value?.invoke(this@QFuncInterpreter.globals[name])
        return@async Unit
      }
      return@async Unit
    } finally {
      this@QFuncInterpreter.state = S_NONE
    }
  }

  override fun visitExpressionStatement(ctx: QuantumParser.ExpressionStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async this@QFuncInterpreter.visitAsync(ctx.expression())
  }

  override fun visitReturnStatement(ctx: QuantumParser.ReturnStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async Return(this@QFuncInterpreter.visitAsync(ctx.expression()) as? ContextValue<*>?)
  }

  override fun visitIsCond(ctx: QuantumParser.IsCondContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async (this@QFuncInterpreter.visitAsync(ctx.expression(0)) as ContextValue<*>?)?.isSame(
      this@QFuncInterpreter.visitAsync(
        ctx.expression(1)
      ) as ContextValue<*>?
    )
  }

  override fun visitLineComment(ctx: QuantumParser.LineCommentContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async Unit
  }

  override fun visitWhileStatement(ctx: QuantumParser.WhileStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    val condition = ctx.condition()
    return@async KtxAsync.async {
      while (visitAsync(condition.expression()) as? Boolean == true) {
        try {
          this@QFuncInterpreter.visitAsync(ctx.statement())
        } catch (e: Break) {
          break
        } catch (e: Continue) {
          continue
        }
      }
    }
  }

  override fun visitLoopStatement(ctx: QuantumParser.LoopStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async KtxAsync.async {
      while (true) {
        try {
          visitAsync(ctx.blockStatement())
        } catch (e: Break) {
          break
        } catch (e: Continue) {
          continue
        }
      }
    }
  }

  override fun visitStopStatement(ctx: QuantumParser.StopStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    throw Stop()
  }

  override fun visitBreakStatement(ctx: QuantumParser.BreakStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    throw Break()
  }

  override fun visitContinueStatement(ctx: QuantumParser.ContinueStatementContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    throw Continue()
  }

  override fun visitDirectiveType(ctx: QuantumParser.DirectiveTypeContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async ctx.text
  }

  override fun visitArgumentName(ctx: QuantumParser.ArgumentNameContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async ctx.text
  }

  override fun visitCondition(ctx: QuantumParser.ConditionContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async this@QFuncInterpreter.visitAsync(ctx.expression())
  }

  override fun visitTerminal(node: TerminalNode?): Job = KtxAsync.async {
    if (node == null) return@async null
    return@async node.text
  }

  override fun visitArgumentExpr(ctx: QuantumParser.ArgumentExprContext?): Job = KtxAsync.async {
    if (ctx == null) return@async null
    return@async ctx.argumentName().text to this@QFuncInterpreter.visitAsync(ctx.expression())
  }

  class Stop : Exception()
  class Break : Exception()
  class Continue : Exception()

  data class Return(val value: ContextValue<*>?)

  override fun visitVariableName(ctx: QuantumParser.VariableNameContext): Job = KtxAsync.async {
    return@async ctx.text
  }

  override fun visitParamName(ctx: QuantumParser.ParamNameContext): Job = KtxAsync.async {
    return@async ctx.text
  }

  override fun visitParameterExpr(ctx: QuantumParser.ParameterExprContext): Job = KtxAsync.async {
    return@async ctx.paramName().text
  }

  override fun visitGlobalName(ctx: QuantumParser.GlobalNameContext): Job = KtxAsync.async {
    return@async ctx.text
  }

  override fun visitGlobalExpr(ctx: QuantumParser.GlobalExprContext): Job = KtxAsync.async {
    return@async ctx.globalName().text
  }
}
