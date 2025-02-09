package dev.ultreon.quantum.scripting

import com.badlogic.gdx.math.RandomXS128
import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.scripting.function.function
import kotlin.math.*

object MathUtils : ContextAware<MathUtils> {
  val PI = 3.1415926535897932384626433832795
  val E = 2.7182818284590452353602874713527

  val randint = function(
    ContextParam("min", ContextType.int),
    ContextParam("max", ContextType.int),
    function = {
      it.getInt("min")?.let { min ->
        it.getInt("max")?.let { max ->
          if (min > max) {
            logger.warn("randint: min is greater than max")
            return@function ContextValue(ContextType.int, min)
          }
          return@function ContextValue(ContextType.int, RandomXS128().nextInt(max - (min - 1)) + (min - 1))
        }
      }
      null
    }
  )

  val sqrt = function(
    ContextParam("x", ContextType.int),
    function = {
      it.getInt("x")?.let { x ->
        return@function ContextValue(ContextType.int, sqrt(x.toDouble()).toInt())
      }
      null
    }
  )

  val pow = function(
    ContextParam("x", ContextType.int),
    ContextParam("y", ContextType.int),
    function = {
      it.getInt("x")?.let { x ->
        it.getInt("y")?.let { y ->
          return@function ContextValue(ContextType.int, x.toDouble().pow(y.toDouble()).toInt())
        }
      }
      null
    }
  )

  val absInt = function(
    ContextParam("x", ContextType.int),
    function = {
      it.getInt("x")?.let { x ->
        return@function ContextValue(ContextType.int, abs(x.toDouble()).toInt())
      }
      null
    }
  )

  val abs = function(
    ContextParam("x", ContextType.float),
    function = {
      it.getDouble("x")?.let { x ->
        return@function ContextValue(ContextType.float, abs(x))
      }
      null
    }
  )

  val round = function(
    ContextParam("x", ContextType.int),
    function = {
      it.getDouble("x")?.let { x ->
        return@function ContextValue(ContextType.int, x.roundToLong())
      }
      null
    }
  )

  val ceil = function(
    ContextParam("x", ContextType.float),
    function = {
      it.getDouble("x")?.let { x ->
        return@function ContextValue(ContextType.int, ceil(x).toInt())
      }
      null
    }
  )

  val floor = function(
    ContextParam("x", ContextType.float),
    function = {
      it.getDouble("x")?.let { x ->
        return@function ContextValue(ContextType.int, floor(x).toInt())
      }
    }
  )

  val log10 = function(
    ContextParam("x", ContextType.float),
    function = {
      it.getDouble("x")?.let { x ->
        return@function ContextValue(ContextType.int, log(x, 10.0).toInt())
      }
    }
  )

  override fun contextType(): ContextType<MathUtils> {
    return ContextType.math
  }

  override fun fieldOf(name: String, contextJson: JsonValue?): ContextValue<*>? {
    return when (name) {
      "randint" -> ContextValue(ContextType.function, randint)
      "sqrt" -> ContextValue(ContextType.function, sqrt)
      "pow" -> ContextValue(ContextType.function, pow)
      "abs" -> ContextValue(ContextType.function, abs)
      "round" -> ContextValue(ContextType.function, round)
      "ceil" -> ContextValue(ContextType.function, ceil)
      "floor" -> ContextValue(ContextType.function, floor)
      else -> null
    }
  }
}
