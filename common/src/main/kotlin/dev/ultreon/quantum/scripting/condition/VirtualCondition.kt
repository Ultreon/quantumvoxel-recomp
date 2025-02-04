package dev.ultreon.quantum.scripting.condition

import dev.ultreon.quantum.scripting.function.CallContext
import dev.ultreon.quantum.scripting.function.ContextAware
import dev.ultreon.quantum.scripting.function.ContextParam
import dev.ultreon.quantum.scripting.function.ContextType

fun interface VirtualCondition : ContextAware<VirtualCondition> {
  fun test(callContext: CallContext): Boolean

  override fun contextType(): ContextType<VirtualCondition> {
    return ContextType.condition
  }

  fun register(name: String): VirtualCondition {
    registry[name] = this
    return this
  }

  companion object {
    private val registry = mutableMapOf<String, VirtualCondition>()

    val TRUE = VirtualCondition { true }.register("true")
    val FALSE = VirtualCondition { false }.register("false")

    val AND = VirtualCondition { callContext ->
      val conditions = callContext.getList<VirtualCondition>("conditions")
      conditions?.all { it.test(callContext) } ?: false
    }.register("and")

    val OR = VirtualCondition { callContext ->
      val conditions = callContext.getList<VirtualCondition>("conditions")
      conditions?.any { it.test(callContext) } ?: false
    }.register("or")

    val NOT = VirtualCondition { callContext ->
      val condition = callContext.get<VirtualCondition>("condition")
      !(condition?.test(callContext) ?: false)
    }.register("not")

    val IF = VirtualCondition { callContext ->
      val condition = callContext.get<VirtualCondition>("condition")
      val then = callContext.get<VirtualCondition>("then")
      val `else` = callContext.get<VirtualCondition>("else")
      (condition?.test(callContext) ?: false).let {
        if (it) then?.test(callContext) ?: false else `else`?.test(callContext) ?: false
      }
    }.register("if")

    val IS_PRESENT = VirtualCondition { callContext ->
      callContext.getRaw("value") != null
    }.register("is-present")

    val IS_NOT_PRESENT = VirtualCondition { callContext ->
      callContext.getRaw("value") == null
    }.register("is-not-present")

    val IS_OF_TYPE = VirtualCondition { callContext ->
      val type = callContext.get(ContextType.type)
      val value = callContext.getRaw("value") ?: return@VirtualCondition false
      ContextType[value::class] == type
    }.register("is-of-type")

    fun and(vararg conditions: VirtualCondition): VirtualCondition {
      return VirtualCondition { callContext ->
        conditions.all { it.test(callContext) }
      }
    }

    fun or(vararg conditions: VirtualCondition): VirtualCondition {
      return VirtualCondition { callContext ->
        conditions.any { it.test(callContext) }
      }
    }

    operator fun get(name: String): VirtualCondition? {
      return registry[name]
    }
  }
}
