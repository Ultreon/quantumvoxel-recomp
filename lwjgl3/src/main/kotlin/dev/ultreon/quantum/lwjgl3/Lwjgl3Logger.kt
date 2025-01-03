package dev.ultreon.quantum.lwjgl3

import dev.ultreon.quantum.Logger
import dev.ultreon.quantum.lwjgl3.ANSI.BG_BLUE
import dev.ultreon.quantum.lwjgl3.ANSI.BG_BRIGHT_BLACK
import dev.ultreon.quantum.lwjgl3.ANSI.BG_BRIGHT_BLUE
import dev.ultreon.quantum.lwjgl3.ANSI.BG_RED
import dev.ultreon.quantum.lwjgl3.ANSI.BG_WHITE
import dev.ultreon.quantum.lwjgl3.ANSI.BG_YELLOW
import dev.ultreon.quantum.lwjgl3.ANSI.FG_BLUE
import dev.ultreon.quantum.lwjgl3.ANSI.FG_CYAN
import dev.ultreon.quantum.lwjgl3.ANSI.FG_BRIGHT_BLACK
import dev.ultreon.quantum.lwjgl3.ANSI.FG_BRIGHT_WHITE
import dev.ultreon.quantum.lwjgl3.ANSI.FG_RED
import dev.ultreon.quantum.lwjgl3.ANSI.FG_WHITE
import dev.ultreon.quantum.lwjgl3.ANSI.FG_YELLOW
import dev.ultreon.quantum.lwjgl3.ANSI.RESET
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.max


private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

class Lwjgl3Logger(inName: String) : Logger {
  private val name = if (inName.length > 15) inName.substring(0, 12) + "..." else inName
  private val nerdName =
    name.padEnd(max(name.length, 15), ' ') // Pad left and right with spaces to make the output look nice and centered

  val time: String
    get() = formatter.format(LocalTime.now()).padEnd(8, ' ')

  override fun info(message: String) {
    for (message in message.lines())
      println("$BG_BRIGHT_BLUE$FG_BRIGHT_BLACK $time $RESET$BG_BLUE$FG_BRIGHT_WHITE INFO  $RESET$FG_BLUE$BG_BRIGHT_BLACK $FG_BLUE$nerdName $RESET$FG_WHITE $message$RESET")
  }

  override fun error(message: String) {
    for (message in message.lines())
      println("$BG_BRIGHT_BLUE$FG_BRIGHT_BLACK $time $RESET$BG_RED$FG_BRIGHT_WHITE ERROR $RESET$FG_RED$BG_BRIGHT_BLACK $FG_RED$nerdName $RESET$FG_WHITE $message$RESET")
  }

  override fun warn(message: String) {
    for (message in message.lines())
      println("$BG_BRIGHT_BLUE$FG_BRIGHT_BLACK $time $RESET$BG_YELLOW$FG_BRIGHT_WHITE WARN  $RESET$FG_YELLOW$BG_BRIGHT_BLACK $FG_YELLOW$nerdName $RESET$FG_WHITE $message$RESET")
  }

  override fun debug(message: String) {
    for (message in message.lines())
      println("$BG_BRIGHT_BLUE$FG_BRIGHT_BLACK $time $RESET${ANSI.BG_CYAN}$FG_BRIGHT_BLACK DEBUG $RESET$FG_CYAN$BG_BRIGHT_BLACK $FG_CYAN$nerdName $RESET$FG_WHITE $message$RESET")
  }

  override fun trace(message: String) {
    for (message in message.lines())
      println("$BG_BRIGHT_BLUE$FG_BRIGHT_BLACK $time $RESET$BG_WHITE$FG_BRIGHT_WHITE TRACE $RESET$FG_WHITE$BG_BRIGHT_BLACK $FG_WHITE$nerdName $RESET$FG_WHITE $message$RESET")
  }
}
