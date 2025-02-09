package dev.ultreon.quantum.scripting.qfunc

enum class QFuncTokenCategory {
  KEYWORD,
  IDENTIFIER,
  NUMBER,
  STRING,
  OPERATOR,
  EOF
}

enum class QFuncTokenType(val value: String, val category: QFuncTokenCategory) {
  COMMA(",", QFuncTokenCategory.OPERATOR),
  COLON(":", QFuncTokenCategory.OPERATOR),
  SEMICOLON(";", QFuncTokenCategory.OPERATOR),
  LPAREN("(", QFuncTokenCategory.OPERATOR),
  RPAREN(")", QFuncTokenCategory.OPERATOR),
  LBRACE("{", QFuncTokenCategory.OPERATOR),
  RBRACE("}", QFuncTokenCategory.OPERATOR),
  LBRACKET("[", QFuncTokenCategory.OPERATOR),
  RBRACKET("]", QFuncTokenCategory.OPERATOR),
  DOT(".", QFuncTokenCategory.OPERATOR),
  ASSIGN("=", QFuncTokenCategory.OPERATOR),
  ARROW("->", QFuncTokenCategory.OPERATOR),
  HASH("#", QFuncTokenCategory.OPERATOR),
  ADD("+", QFuncTokenCategory.OPERATOR),
  SUB("-", QFuncTokenCategory.OPERATOR),
  MUL("*", QFuncTokenCategory.OPERATOR),
  DIV("/", QFuncTokenCategory.OPERATOR),
  MOD("%", QFuncTokenCategory.OPERATOR),
  BITWISE_XOR("^", QFuncTokenCategory.OPERATOR),
  BITWISE_AND("&", QFuncTokenCategory.OPERATOR),
  BITWISE_OR("|", QFuncTokenCategory.OPERATOR),
  BITWISE_NOT("~", QFuncTokenCategory.OPERATOR),
  QUESTION("?", QFuncTokenCategory.OPERATOR),
  AT("@", QFuncTokenCategory.OPERATOR),
  DOLLAR("$", QFuncTokenCategory.OPERATOR),
  BACKSLASH("\\", QFuncTokenCategory.OPERATOR),
  QUOTE("'", QFuncTokenCategory.OPERATOR),
  COLON_EQUALS(":=", QFuncTokenCategory.OPERATOR),
  EQUALS("=", QFuncTokenCategory.OPERATOR),
  NOT_EQUAL("!=", QFuncTokenCategory.OPERATOR),
  LESS_THAN("<", QFuncTokenCategory.OPERATOR),
  GREATER_THAN(">", QFuncTokenCategory.OPERATOR),
  LESS_THAN_EQUALS("<=", QFuncTokenCategory.OPERATOR),
  GREATER_THAN_EQUALS(">=", QFuncTokenCategory.OPERATOR),
  AND("&&", QFuncTokenCategory.OPERATOR),
  OR("||", QFuncTokenCategory.OPERATOR),
  NOT("!", QFuncTokenCategory.OPERATOR),
  SHL("<<", QFuncTokenCategory.OPERATOR),
  SHR(">>", QFuncTokenCategory.OPERATOR),
  SHL_ASSIGN("<<=", QFuncTokenCategory.OPERATOR),
  SHR_ASSIGN(">>=", QFuncTokenCategory.OPERATOR),
  AND_ASSIGN("&=", QFuncTokenCategory.OPERATOR),
  OR_ASSIGN("|=", QFuncTokenCategory.OPERATOR),
  XOR_ASSIGN("^=", QFuncTokenCategory.OPERATOR),
  ADD_ASSIGN("+=", QFuncTokenCategory.OPERATOR),
  SUB_ASSIGN("-=", QFuncTokenCategory.OPERATOR),
  MUL_ASSIGN("*=", QFuncTokenCategory.OPERATOR),
  DIV_ASSIGN("/=", QFuncTokenCategory.OPERATOR),
  MOD_ASSIGN("%=", QFuncTokenCategory.OPERATOR),
  INCREMENT("++", QFuncTokenCategory.OPERATOR),
  DECREMENT("--", QFuncTokenCategory.OPERATOR),
  NULL("null", QFuncTokenCategory.KEYWORD),
  THIS("this", QFuncTokenCategory.KEYWORD),
  TRUE("true", QFuncTokenCategory.KEYWORD),
  FALSE("false", QFuncTokenCategory.KEYWORD),
  RETURN("return", QFuncTokenCategory.KEYWORD),
  IF("if", QFuncTokenCategory.KEYWORD),
  IS("is", QFuncTokenCategory.KEYWORD),
  ELSE("else", QFuncTokenCategory.KEYWORD),
  WHILE("while", QFuncTokenCategory.KEYWORD),
  FOR("for", QFuncTokenCategory.KEYWORD),
  LOOP("loop", QFuncTokenCategory.KEYWORD),
  BREAK("break", QFuncTokenCategory.KEYWORD),
  CONTINUE("continue", QFuncTokenCategory.KEYWORD),
  STOP("stop", QFuncTokenCategory.KEYWORD),
  INPUT_DIRECTIVE("input", QFuncTokenCategory.KEYWORD),
  PERSIST_DIRECTIVE("persist", QFuncTokenCategory.KEYWORD),
  PRESENT("present", QFuncTokenCategory.KEYWORD),
  IN("in", QFuncTokenCategory.KEYWORD),
  IDENTIFIER("<identifier>", QFuncTokenCategory.IDENTIFIER),
  NUMBER("<number>", QFuncTokenCategory.NUMBER),
  STRING("<string>", QFuncTokenCategory.STRING),
  EOF("<<eof>>", QFuncTokenCategory.EOF),
  ;
  val precedence: Int
    get() = when (this) {
      ADD -> 1
      SUB -> 1
      MUL -> 2
      DIV -> 2
      MOD -> 2
      BITWISE_XOR -> 3
      BITWISE_AND -> 4
      BITWISE_OR -> 5
      NOT -> 6
      QUESTION -> 7
      AND -> 8
      OR -> 9
      LESS_THAN -> 10
      GREATER_THAN -> 10
      LESS_THAN_EQUALS -> 10
      GREATER_THAN_EQUALS -> 10
      EQUALS -> 11
      NOT_EQUAL -> 11
      else -> 0
    }
  fun isBinaryOp(): Boolean {
    return when (this) {
      ADD, SUB, MUL, DIV, MOD, BITWISE_XOR, BITWISE_AND, BITWISE_OR, AND, OR, LESS_THAN, GREATER_THAN, LESS_THAN_EQUALS, GREATER_THAN_EQUALS, EQUALS, NOT_EQUAL -> true
      else -> false
    }
  }

  fun isUnaryOp(): Boolean {
    return when (this) {
      NOT -> true
      INCREMENT, DECREMENT -> true
      else -> false
    }
  }

  companion object {
    fun fromKeyword(keyword: String): QFuncTokenType? {
      return entries.filter { it.category == QFuncTokenCategory.KEYWORD }.firstOrNull { it.value == keyword }
    }
  }
}

data class QFuncToken(val type: QFuncTokenType, val text: String, val value: Any?, val position: Int, val line: Int, val column: Int) {

  val precedence: Int
    get() = type.precedence
}

@Suppress("MemberVisibilityCanBePrivate")
class QFuncLexer(val input: String, val filename: String) {
  var position = 0
  var line = 1
  var column = 1
  var char: Char? = input[position]

  var markedPosition = 0
  var markedLine = 1
  var markedColumn = 1

  fun advance(): Char? {
    if (position + 1 < input.length) {
      position++
      column++
      if (input[position] == '\n') {
        line++
        column = 1
      }
      char = input[position]
      return input[position]
    } else {
      char = null
      return null
    }
  }

  fun peek(): Char? {
    return if (position + 1 < input.length) {
      input[position + 1]
    } else {
      null
    }
  }

  fun current(): Char? {
    return if (position < input.length) {
      input[position]
    } else {
      null
    }
  }

  fun mark() {
    this.markedPosition = position
    this.markedLine = line
    this.markedColumn = column
  }

  fun reset() {
    this.position = this.markedPosition
    this.line = this.markedLine
    this.column = this.markedColumn
  }

  fun next(): QFuncToken {
    return when (char) {
      in '0'..'9' -> {
        val number = StringBuilder()
        var pos: MarkedPos
        var decimal = false
        do {
          number.append(char)
          pos = MarkedPos(position, line, column)
          advance()
          if (char == '.') {
            if (!decimal) {
              decimal = true
            } else {
              throw QFuncSyntaxError("Expected a digit or end of number", position, line, column)
            }
          }
        } while (char in '0'..'9' || char == '.')

        pos.let { this.reset(it) }
        QFuncToken(QFuncTokenType.NUMBER, number.toString(), number.toString().toIntOrNull() ?: number.toString().toLongOrNull() ?: number.toString().toFloatOrNull() ?: number.toString().toDoubleOrNull() ?: throw QFuncSyntaxError("Expected a number", position, line, column), position, line, column)
      }
      in 'A'..'Z', in 'a'..'z' -> {
        val identifier = StringBuilder()
        var pos: MarkedPos
        do {
          identifier.append(char)
          pos = MarkedPos(position, line, column)
          advance()
        } while (char in 'A'..'Z' || char in 'a'..'z' || char in '0'..'9' || char == '_' || char == '-')

        pos.let { this.reset(it) }
        QFuncToken(QFuncTokenType.fromKeyword(identifier.toString()) ?: QFuncTokenType.IDENTIFIER, identifier.toString(), null, position, line, column)
      }
      '\'' -> {
        val string = StringBuilder()
        var pos: MarkedPos
        advance()
        do {
          string.append(char)
          pos = MarkedPos(position, line, column)
          advance()
          if (char == '\\') {
            string.append(advance())
          }
        } while (char != '\'' && char != null)

        pos.let { this.reset(it) }
        if (char == null) {
          throw QFuncSyntaxError("Unterminated string literal", position, line, column)
        }
        advance()
        QFuncToken(QFuncTokenType.STRING, string.toString(), string.toString(), position, line, column)
      }
      ' ', '\t', '\r', '\n' -> {
        throw IllegalStateException("Unexpected whitespace at position $position ($line:$column)")
      }
      ',' -> QFuncToken(QFuncTokenType.COMMA, char.toString(), null, position, line, column)
      ':' -> QFuncToken(QFuncTokenType.COLON, char.toString(), null, position, line, column)
      ';' -> QFuncToken(QFuncTokenType.SEMICOLON, char.toString(), null, position, line, column)
      '(' -> QFuncToken(QFuncTokenType.LPAREN, char.toString(), null, position, line, column)
      ')' -> QFuncToken(QFuncTokenType.RPAREN, char.toString(), null, position, line, column)
      '{' -> QFuncToken(QFuncTokenType.LBRACE, char.toString(), null, position, line, column)
      '}' -> QFuncToken(QFuncTokenType.RBRACE, char.toString(), null, position, line, column)
      '[' -> QFuncToken(QFuncTokenType.LBRACKET, char.toString(), null, position, line, column)
      ']' -> QFuncToken(QFuncTokenType.RBRACKET, char.toString(), null, position, line, column)
      '.' -> QFuncToken(QFuncTokenType.DOT, char.toString(), null, position, line, column)
      '=' -> when (peek()) {
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.EQUALS, "==", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.ASSIGN, char.toString(), null, position, line, column)
      }
      '+' -> when (peek()) {
        '+' -> {
          advance()
          QFuncToken(QFuncTokenType.INCREMENT, "++", null, position, line, column)
        }
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.ADD_ASSIGN, "+=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.ADD, char.toString(), null, position, line, column)
      }
      '-' -> when (peek()) {
        '-' -> {
          advance()
          QFuncToken(QFuncTokenType.DECREMENT, "--", null, position, line, column)
        }
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.SUB_ASSIGN, "-=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.SUB, char.toString(), null, position, line, column)
      }
      '*' -> when (peek()) {
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.MUL_ASSIGN, "*=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.MUL, char.toString(), null, position, line, column)
      }
      '/' -> when (peek()) {
        '/' -> {
          val comment = StringBuilder()
          do {
            comment.append(char)
            advance()
          } while (char != '\n' && char != null)
          if (char == null) {
            throw QFuncSyntaxError("Unterminated comment", position, line, column)
          }
          advance()
          next()
        }
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.DIV_ASSIGN, "/=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.DIV, char.toString(), null, position, line, column)
      }
      '%' -> when (peek()) {
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.MOD_ASSIGN, "%=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.MOD, char.toString(), null, position, line, column)
      }
      '<' -> when (peek()) {
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.LESS_THAN_EQUALS, "<=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.LESS_THAN, char.toString(), null, position, line, column)
      }
      '>' -> when (peek()) {
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.GREATER_THAN_EQUALS, ">=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.GREATER_THAN, char.toString(), null, position, line, column)
      }
      '!' -> when (peek()) {
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.NOT_EQUAL, "!=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.NOT, char.toString(), null, position, line, column)
      }
      '|' -> when (peek()) {
        '|' -> {
          advance()
          QFuncToken(QFuncTokenType.OR, "||", null, position, line, column)
        }
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.OR_ASSIGN, "|=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.BITWISE_OR, char.toString(), null, position, line, column)
      }
      '&' -> when (peek()) {
        '&' -> {
          advance()
          QFuncToken(QFuncTokenType.AND, "&&", null, position, line, column)
        }
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.AND_ASSIGN, "&=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.BITWISE_AND, char.toString(), null, position, line, column)
      }
      '^' -> when (peek()) {
        '=' -> {
          advance()
          QFuncToken(QFuncTokenType.XOR_ASSIGN, "^=", null, position, line, column)
        }
        else -> QFuncToken(QFuncTokenType.BITWISE_XOR, char.toString(), null, position, line, column)
      }
      '~' -> QFuncToken(QFuncTokenType.BITWISE_NOT, char.toString(), null, position, line, column)
      '@' -> QFuncToken(QFuncTokenType.AT, char.toString(), null, position, line, column)
      '$' -> QFuncToken(QFuncTokenType.DOLLAR, char.toString(), null, position, line, column)
      '#' -> QFuncToken(QFuncTokenType.HASH, char.toString(), null, position, line, column)
      null -> QFuncToken(QFuncTokenType.EOF, "", null, position, line, column)
      else -> throw QFuncSyntaxError("Unexpected character", position, line, column)
    }.also { advance() }
  }

  private fun reset(position: MarkedPos) {
    this.position = position.position
    this.line = position.line
    this.column = position.column
  }

  fun lex(): List<QFuncToken> {
    val tokens = ArrayList<QFuncToken>()

    var pos: MarkedPos? = null
    while (char != null) {
      val char = current()
      if (char == ' ' || char == '\t' || char == '\r' || char == '\n') {
        pos = MarkedPos(position, line, column)
        advance()
        continue
      }

      tokens.add(next())
    }

    return tokens
  }
}

data class MarkedPos(val position: Int, val line: Int, val column: Int)
