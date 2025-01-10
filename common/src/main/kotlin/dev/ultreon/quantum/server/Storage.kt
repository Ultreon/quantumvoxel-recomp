package dev.ultreon.quantum.server

import java.util.function.Function
import java.util.function.IntFunction

interface Storage<D : Any> {
  operator fun set(idx: Int, value: D): Boolean

  operator fun get(idx: Int): D

  fun <R : Any> map(defaultValue: R, generator: IntFunction<Array<R>>, mapper: Function<D, R>): Storage<R>

  val isUniform: Boolean
}
