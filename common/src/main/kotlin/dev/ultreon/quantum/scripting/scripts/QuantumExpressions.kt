package dev.ultreon.quantum.scripting.scripts

import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.scriptic.Registries
import dev.ultreon.scriptic.ScriptException
import dev.ultreon.scriptic.lang.CodeContext
import dev.ultreon.scriptic.lang.obj.Expr
import org.intellij.lang.annotations.Language
import java.util.function.Supplier
import java.util.regex.Matcher

@Language("RegExp")
const val NAMESPACE_ID_PATTERN: String = "^\\[${NamespaceID.PATTERN}]$"

class NamespaceIDExpr : Expr<NamespaceID>(NamespaceID::class.java) {
  override fun load(lineNr: Int, matcher: Matcher) {
    // No-op
  }

  override fun eval(context: CodeContext?): NamespaceID {
    return NamespaceID.parse(code().substring(1, code().length - 1))
  }
}

class BlockExpr : Expr<Block>(Block::class.java) {
  var id: NamespaceID? = null

  override fun load(lineNr: Int, matcher: Matcher) {
    id = NamespaceID.parse(matcher.group("id")!!)
  }

  override fun eval(context: CodeContext?): Block {
    return dev.ultreon.quantum.registry.Registries.blocks[id!!] ?: throw ScriptException("Block with id $id not found")
  }

  companion object {
    @Language("RegExp")
    const val PATTERN: String = "^block \\[(?<id>${NamespaceID.PATTERN})]$"
  }
}

object QuantumExpressions {
  val namespaceID = register<NamespaceIDExpr>(NAMESPACE_ID_PATTERN) { NamespaceIDExpr() }
  val block = register<BlockExpr>(BlockExpr.PATTERN) { BlockExpr() }

  fun <T : Expr<*>> register(name: String?, expr: Supplier<T>?, type: Class<T>) {
    @Suppress("UNCHECKED_CAST")
    Registries.EXPRESSIONS.register(
      name,
      expr as Supplier<Expr<*>?>?,
      type as Class<Expr<*>>
    )
  }

  inline fun <reified T : Expr<*>> register(name: String?, expr: Supplier<T>?) {
    register(name, expr, T::class.java)
  }
}
