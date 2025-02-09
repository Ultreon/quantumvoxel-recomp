package dev.ultreon.quantum.scripting.qfunc

import dev.ultreon.quantum.util.NamespaceID

class QFuncParserException(message: String, filename: String, token: QFuncToken) :
  Exception(message + " at $filename:${token.line}:${token.column}")

data class FileAST(
  val statements: List<StatementAST> = emptyList(),
  override val filename: String = "<unknown>",
  override val line: Int = 1,
  override val column: Int = 1
) : AST

interface AST {
  val filename: String
  val line: Int
  val column: Int
}

interface StatementAST : AST

data class DirectiveAST(
  val name: String,
  val type: DirectiveTypeAST?,
  val values: List<DirectiveValueAST>,
  override val filename: String = "<unknown>",
  override val line: Int = -1,
  override val column: Int = -1
) : StatementAST

data class DirectiveTypeAST(val name: String, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : AST

data class DirectiveValueAST(val value: String, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : AST

data class ForAST(val param: String, val iterable: ExpressionAST, val body: BlockAST, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : StatementAST

data class IfAST(val condition: ExpressionAST, val body: BlockAST, val elseBody: BlockAST?, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : StatementAST

data class WhileAST(val condition: ExpressionAST, val body: BlockAST, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : StatementAST

data class BlockAST(val statements: List<StatementAST>, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : StatementAST

data class BreakAST(override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : StatementAST

data class ContinueAST(override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : StatementAST

data class StopAST(override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : StatementAST

data class ReturnAST(val expression: ExpressionAST?, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : StatementAST

interface ExpressionAST : AST {
    override val filename: String get() = "<unknown>"
    override val line: Int get() = -1
    override val column: Int get() = -1
}

data class ExpressionStatementAST(val expression: ExpressionAST, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : StatementAST

data class FunctionCallAST(val arguments: List<ArgumentAST>, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : ExpressionAST

data class ArgumentAST(val parameterName: String, val expression: ExpressionAST, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : AST

data class MemberAST(val name: String, val funcCall: FunctionCallAST?, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : ExpressionAST

data class GlobalAST(val name: String, val members: List<MemberAST> = emptyList(), override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : ExpressionAST

data class InputParamAST(val name: String, val members: List<MemberAST> = emptyList(), override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : ExpressionAST

data class AssignmentAST(val global: String, val expression: ExpressionAST, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : ExpressionAST, StatementAST

interface IdLikeAST : ExpressionAST

data class IdAST(val id: NamespaceID, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : IdLikeAST

data class TagIdAST(val id: NamespaceID, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : IdLikeAST

data class StringAST(val value: String, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : IdLikeAST

data class NumberAST(val value: Number, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : IdLikeAST

data class BooleanAST(val value: Boolean, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : IdLikeAST

data class UnaryOpAST(val type: QFuncTokenType, val expression: ExpressionAST, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : ExpressionAST

data class BinaryOpsAST(val type: QFuncTokenType, val left: ExpressionAST, val right: ExpressionAST, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : ExpressionAST

data class PresentAST(val expression: ExpressionAST, override val filename: String = "<unknown>", override val line: Int = -1, override val column: Int = -1) : ExpressionAST

class QFuncParser(lexer: QFuncLexer) {
  var file: FileAST? = null
  val tokens = lexer.lex()
  var position = 0
  val filename = lexer.filename
  private val cur get() = tokens[position]

  fun parse() {
    val statements = ArrayList<StatementAST>()
    if (tokens.isEmpty()) {
      file = FileAST()
    }

    while (position < tokens.size) {
      statements.add(parseStatement() ?: continue)
    }

    file = FileAST(statements, filename)
  }

  private fun next(): QFuncToken {
    return tokens[position].also {
      position++
    }
  }

  private fun peek(): QFuncToken {
    return tokens[position + 1]
  }

  private fun current(): QFuncToken {
    return tokens[position]
  }

  private fun expect(type: QFuncTokenType): QFuncToken {
    val token = next()
    if (token.type != type) {
      throw QFuncParserException("Expected '${type.value}', got '${token.text}'", filename, token)
    }
    return token
  }

  private fun parseStatement(): StatementAST? {
    val token = next()
    return when (token.type) {
      QFuncTokenType.HASH -> {
        parseDirective()
      }

      QFuncTokenType.DOLLAR -> {
        parseGlobal().let { it: GlobalAST ->
          if (current().type == QFuncTokenType.ASSIGN) {
            next()
            if (it.members.isNotEmpty()) {
              throw QFuncParserException("Cannot assign to global with members", filename, token)
            }
            return@let AssignmentAST(it.name, parseExpression(), filename, cur.line, cur.column).also { expect(QFuncTokenType.SEMICOLON) }
          } else {
            return@let ExpressionStatementAST(it, filename, cur.line, cur.column).also { expect(QFuncTokenType.SEMICOLON) }
          }
        }
      }

      QFuncTokenType.AT -> {
        parseInputParam().let { it: InputParamAST ->
          if (current().type == QFuncTokenType.ASSIGN) {
            throw QFuncParserException("Cannot assign to input parameter", filename, token)
          } else {
            return@let ExpressionStatementAST(it, filename, cur.line, cur.column).also { expect(QFuncTokenType.SEMICOLON) }
          }
        }
      }

      QFuncTokenType.IF -> {
        parseIf()
      }

      QFuncTokenType.WHILE -> {
        parseWhile()
      }

      QFuncTokenType.FOR -> {
        parseFor()
      }

      QFuncTokenType.BREAK -> {
        BreakAST(filename, token.line, token.column).also { expect(QFuncTokenType.SEMICOLON) }
      }

      QFuncTokenType.CONTINUE -> {
        ContinueAST(filename, token.line, token.column).also { expect(QFuncTokenType.SEMICOLON) }
      }

      QFuncTokenType.STOP -> {
        StopAST(filename, token.line, token.column).also { expect(QFuncTokenType.SEMICOLON) }
      }

      QFuncTokenType.SEMICOLON -> {
        null
      }

      QFuncTokenType.RETURN -> {
        ReturnAST(if (peek().type == QFuncTokenType.SEMICOLON) {
          null
        } else {
          parseExpression()
        }, filename, token.line, token.column).also { expect(QFuncTokenType.SEMICOLON) }
      }

      QFuncTokenType.LBRACE -> {
        parseBlockStatement()
      }

      else -> {
        position--
        ExpressionStatementAST(parseExpression()).also { expect(QFuncTokenType.SEMICOLON) }
      }
    }
  }

  private fun parseIf(): IfAST {
    val condition = parseExpression()
    val body = parseBlockStatement()
    val elseBody = if (current().type == QFuncTokenType.ELSE) {
      next()
      parseBlockStatement()
    } else {
      null
    }
    return IfAST(condition, body, elseBody, filename, cur.line, cur.column)
  }

  private fun parseWhile(): WhileAST {
    val condition = parseExpression()
    val body = parseBlockStatement()
    return WhileAST(condition, body, filename, cur.line, cur.column)
  }

  private fun parseFor(): ForAST {
    val param = expect(QFuncTokenType.IDENTIFIER)
    expect(QFuncTokenType.IN)
    val iterable = parseExpression()
    val body = parseBlockStatement()
    return ForAST(param.value as String, iterable, body, filename, cur.line, cur.column)
  }

  private fun parseBlockStatement(): BlockAST {
    val statements = ArrayList<StatementAST>()
    expect(QFuncTokenType.LBRACE)
    while (current().type != QFuncTokenType.RBRACE) {
      statements.add(parseStatement() ?: continue)
    }
    expect(QFuncTokenType.RBRACE)
    return BlockAST(statements, filename, cur.line, cur.column)
  }

  private fun parseExpression(): ExpressionAST {
    val token = next()
    return when (token.type) {
      QFuncTokenType.STRING -> {
        StringAST(token.value as String)
      }

      QFuncTokenType.NUMBER -> {
        NumberAST(token.value as Number)
      }

      QFuncTokenType.TRUE -> {
        BooleanAST(true)
      }

      QFuncTokenType.FALSE -> {
        BooleanAST(false)
      }

      QFuncTokenType.AT -> {
        parseInputParam()
      }

      QFuncTokenType.DOLLAR -> {
        parseGlobal()
      }

      QFuncTokenType.LBRACKET -> {
        parseIdLike()
      }

      QFuncTokenType.PRESENT -> {
        PresentAST(parseExpression(), filename, token.line, token.column)
      }

      QFuncTokenType.NOT -> {
        UnaryOpAST(QFuncTokenType.NOT, parseExpression(), filename, token.line, token.column)
      }

      else -> {
        throw QFuncParserException("Expected expression, got '${token.text}'", filename, token)
      }
    }.let {
      val expression = it
      if (current().type == QFuncTokenType.AND) {
        next()
        BinaryOpsAST(QFuncTokenType.AND, expression, parseBinaryOp(expression), filename, cur.line, cur.column)
      } else if (current().type == QFuncTokenType.OR) {
        next()
        BinaryOpsAST(QFuncTokenType.OR, expression, parseBinaryOp(expression), filename, cur.line, cur.column)
      } else {
        parseBinaryOp(expression)
      }
    }
  }

  private fun parseBinaryOp(expression: ExpressionAST, precedence: Int = 0): ExpressionAST {
    val token = current()
    return if (token.type.isBinaryOp() && token.precedence > precedence) {
      next()
      BinaryOpsAST(token.type, expression, parseBinaryExpression(expression, token.precedence), filename, cur.line, cur.column)
    } else if (token.type == QFuncTokenType.LPAREN) {
      next()
      val expr = parseExpression()
      expect(QFuncTokenType.RPAREN)
      parseBinaryOp(expr)
    } else {
      expression
    }.also {
      if (current().type.isUnaryOp()) {
        next()
        parseBinaryOp(UnaryOpAST(current().type, it), current().precedence)
      } else if (current().type.isBinaryOp()) {
        next()
        parseBinaryOp(it,current().precedence)
      } else {
        it
      }
    }
  }

  private fun parseBinaryExpression(expression: ExpressionAST, precedence: Int): ExpressionAST {
    val right = parseExpression()
    return if (current().type.isBinaryOp() && current().precedence > precedence) {
      parseBinaryExpression(right, current().precedence)
    } else {
      right
    }
  }

  private fun parseIdLike(): IdLikeAST {
    val token = current()
    if (token.type == QFuncTokenType.HASH) {
      next()
      return parseTagId()
    }
    return parseId()
  }

  private fun parseId(): IdAST {
    val namespace = expect(QFuncTokenType.IDENTIFIER)
    if (current().type == QFuncTokenType.COLON) {
      next()
      val path = parsePath()
      return IdAST(NamespaceID(namespace.text, path), filename, cur.line, cur.column)
    } else {
      position--
      return IdAST(NamespaceID("quantum", path = parsePath()), filename, cur.line, cur.column)
    }
  }

  private fun parseTagId(): TagIdAST {
    val namespace = expect(QFuncTokenType.IDENTIFIER)
    if (current().type == QFuncTokenType.COLON) {
      next()
      val path = parsePath()
      return TagIdAST(NamespaceID(namespace.text, path), filename, cur.line, cur.column)
    } else {
      position--
      return TagIdAST(NamespaceID("quantum", parsePath()), filename, cur.line, cur.column)
    }
  }

  private fun parsePath(): String {
    var path = expect(QFuncTokenType.IDENTIFIER).text as String
    while (current().type == QFuncTokenType.DIV) {
      next()
      val nextPath = expect(QFuncTokenType.IDENTIFIER).text as String
      path += "/$nextPath"
    }
    if (current().type == QFuncTokenType.DOT) {
      next()
      val nextPath = expect(QFuncTokenType.IDENTIFIER).text as String
      path += ".${nextPath}"
    }
    expect(QFuncTokenType.RBRACKET)
    return path
  }

  private fun parseDirective(): DirectiveAST {
    val token = next()
    return when (token.type) {
      QFuncTokenType.INPUT_DIRECTIVE -> {
        val name = "input"

        val values = ArrayList<DirectiveValueAST>()
        while (current().type == QFuncTokenType.IDENTIFIER) {
          val nextToken = expect(QFuncTokenType.IDENTIFIER)
          values.add(DirectiveValueAST(nextToken.text, filename, cur.line, cur.column))
        }

        DirectiveAST(name, null, values, filename, cur.line, cur.column)
      }

      QFuncTokenType.PERSIST_DIRECTIVE -> {
        val name = "persist"

        var directiveType: DirectiveTypeAST? = null
        expect(QFuncTokenType.LESS_THAN)
        directiveType = DirectiveTypeAST(expect(QFuncTokenType.IDENTIFIER).text, filename, cur.line, cur.column)
        expect(QFuncTokenType.GREATER_THAN)

        val values = ArrayList<DirectiveValueAST>()
        while (current().type == QFuncTokenType.IDENTIFIER) {
          val nextToken = expect(QFuncTokenType.IDENTIFIER)
          values.add(DirectiveValueAST(nextToken.text, filename, cur.line, cur.column))
        }

        DirectiveAST(name, directiveType, values, filename, cur.line, cur.column)
      }

      QFuncTokenType.IDENTIFIER -> {
        throw QFuncParserException("Expected directive, got ${token.type}", filename, token)
      }

      else -> {
        throw QFuncParserException("Expected directive, got ${token.type}", filename, token)
      }
    }
  }

  private fun parseGlobal(): GlobalAST {
    val token = expect(QFuncTokenType.IDENTIFIER)
    val name = token.text
    val members = ArrayList<MemberAST>()
    while (current().type == QFuncTokenType.COLON) {
      next()
      members.add(parseMember())
    }
    return GlobalAST(name, members, filename, cur.line, cur.column)
  }

  private fun parseInputParam(): InputParamAST {
    val token = expect(QFuncTokenType.IDENTIFIER)
    val name = token.text
    val members = ArrayList<MemberAST>()
    while (current().type == QFuncTokenType.COLON) {
      next()
      members.add(parseMember())
    }
    return InputParamAST(name, members, filename, cur.line, cur.column)
  }

  private fun parseMember(): MemberAST {
    val token = expect(QFuncTokenType.IDENTIFIER)
    val name = token.text as String
    val funcCall = if (current().type == QFuncTokenType.LPAREN) {
      next()
      parseFunctionCall()
    } else {
      null
    }
    return MemberAST(name, funcCall, filename, cur.line, cur.column)
  }

  private fun parseFunctionCall(): FunctionCallAST {
    val arguments = ArrayList<ArgumentAST>()
    arguments.add(parseArgument())
    while (current().type == QFuncTokenType.COMMA) {
      next()
      arguments.add(parseArgument())
    }

    expect(QFuncTokenType.RPAREN)
    return FunctionCallAST(arguments, filename, cur.line, cur.column)
  }

  private fun parseArgument(): ArgumentAST {
    val token = expect(QFuncTokenType.IDENTIFIER)
    val name = token.text
    expect(QFuncTokenType.COLON)
    return ArgumentAST(name, parseExpression(), filename, cur.line, cur.column)
  }
}
