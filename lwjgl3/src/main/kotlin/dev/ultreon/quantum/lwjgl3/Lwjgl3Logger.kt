package dev.ultreon.quantum.lwjgl3

import dev.ultreon.quantum.Logger
import kotlin.math.max

class Lwjgl3Logger(inName: String) : Logger {
  private val name = if (inName.length > 15) inName.substring(0, 12) + "..." else inName
  private val nerdName =
    name.padEnd(max(name.length, 15), ' ') // Pad left and right with spaces to make the output look nice and centered

  override fun info(message: String) {
    for (message in message.lines())
      println("${ANSI.RESET}${ANSI.BG_BLUE}${ANSI.FG_BRIGHT_WHITE} INFO  ${ANSI.RESET}${ANSI.FG_BLUE}${ANSI.BG_BRIGHT_WHITE} ${ANSI.FG_BLUE}$nerdName ${ANSI.RESET}${ANSI.FG_WHITE} $message")
  }

  override fun error(message: String) {
    for (message in message.lines())
      println("${ANSI.RESET}${ANSI.BG_RED}${ANSI.FG_BRIGHT_WHITE} ERROR ${ANSI.RESET}${ANSI.FG_RED}${ANSI.BG_BRIGHT_WHITE} ${ANSI.FG_RED}$nerdName ${ANSI.RESET}${ANSI.FG_WHITE} $message")
  }

  override fun warn(message: String) {
    for (message in message.lines())
      println("${ANSI.RESET}${ANSI.BG_BRIGHT_RED}${ANSI.FG_BRIGHT_WHITE} WARN  ${ANSI.RESET}${ANSI.FG_BRIGHT_RED}${ANSI.BG_BRIGHT_WHITE} ${ANSI.FG_BRIGHT_RED}$nerdName ${ANSI.RESET}${ANSI.FG_WHITE} $message")
  }

  override fun debug(message: String) {
    for (message in message.lines())
      println("${ANSI.RESET}${ANSI.BG_CYAN}${ANSI.FG_BRIGHT_BLACK} DEBUG ${ANSI.RESET}${ANSI.FG_CYAN}${ANSI.BG_BRIGHT_BLACK} ${ANSI.FG_CYAN}$nerdName ${ANSI.RESET}${ANSI.FG_WHITE} $message")
  }

  override fun trace(message: String) {
    for (message in message.lines())
      println("${ANSI.RESET}${ANSI.BG_WHITE}${ANSI.FG_BRIGHT_WHITE} TRACE ${ANSI.RESET}${ANSI.FG_WHITE}${ANSI.BG_BRIGHT_WHITE} ${ANSI.FG_WHITE}$nerdName ${ANSI.RESET}${ANSI.FG_WHITE} $message")
  }
}
