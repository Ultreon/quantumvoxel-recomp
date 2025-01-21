package dev.ultreon.quantum.lwjgl3

object ANSIConsole {
  private val stdin = System.`in`

  fun print(message: String) {
    kotlin.io.print(message)
  }

  fun println(message: String) {
    kotlin.io.println(message)
  }

  fun moveCursor(x: Int = 0, y: Int = 0) {
    println("\u001B[${y + 1};${x + 1}H")
  }

  fun moveCursorUp(count: Int = 1) {
    println("\u001B[${count}A")
  }

  fun moveCursorDown(count: Int = 1) {
    println("\u001B[${count}B")
  }

  fun moveCursorRight(count: Int = 1) {
    println("\u001B[${count}C")
  }

  fun moveCursorLeft(count: Int = 1) {
    println("\u001B[${count}D")
  }

  fun clear() {
    println(ANSI.CLEAR_SCREEN)
  }

  fun clearLine() {
    println(ANSI.CLEAR_LINE)
  }

  fun hideCursor() {
    println("\u001B[?25l")
  }

  fun showCursor() {
    println("\u001B[?25h")
  }

  fun reset() {
    println(ANSI.RESET)
  }

  fun bold() {
    println(ANSI.BOLD)
  }

  fun dim() {
    println(ANSI.DIM)
  }

  fun italic() {
    println(ANSI.ITALIC)
  }

  fun underline() {
    println(ANSI.UNDERLINE)
  }

  fun blink() {
    println(ANSI.BLINK)
  }

  fun reverse() {
    println(ANSI.REVERSE)
  }

  fun hidden() {
    println(ANSI.HIDDEN)
  }

  fun color(r: Int, g: Int, b: Int) {
    println("\u001B[38;2;${r};${g};${b}m")
  }

  fun resetFormat() {
    println("\u001B[0m")
  }

  fun resetColor() {
    println("\u001B[39m")
  }

  fun resetBackground() {
    println("\u001B[49m")
  }

  fun background(r: Int, g: Int, b: Int) {
    println("\u001B[4${r};${g};${b}m")
  }

  @JvmStatic
  fun main(args: Array<String>) {
    clear()

    print("Welcome to the ANSI Console!")
    readln()
    clear()

    color(255, 0, 0)
    println("Red")
    resetColor()

    color(0, 255, 0)
    println("Green")
    resetColor()

    color(0, 0, 255)
    println("Blue")
    resetColor()

    color(255, 255, 0)
    println("Yellow")
    resetColor()

    color(255, 0, 255)
    println("Magenta")
    resetColor()

    color(0, 255, 255)
    println("Cyan")
    resetColor()

    color(255, 255, 255)
    println("White")
    resetColor()

    color(0, 0, 0)
    println("Black")
    resetColor()

    // More...

    readln()
    clear()

    blink()
    println("Blink")
    resetFormat()

    readln()
    clear()

    underline()
    println("Underline")
    resetFormat()

    readln()
    clear()

    bold()
    println("Bold")
    resetFormat()

    readln()
    clear()

    dim()
    println("Dim")
    resetFormat()

    readln()
    clear()

    italic()
    println("Italic")
    resetFormat()

    readln()
    clear()

    reverse()
    println("Reverse")
    resetFormat()

    readln()
    clear()

    hidden()
    println("Hidden")
    resetFormat()

    readln()
    clear()

    background(255, 0, 0)
    println("Background Red")
    resetBackground()

    readln()
    clear()

    background(0, 255, 0)
    println("Background Green")
    resetBackground()

    readln()
    clear()

    background(0, 0, 255)
    println("Background Blue")
    resetBackground()

    readln()
    clear()

    background(255, 255, 0)
    println("Background Yellow")
    resetBackground()

    readln()
    clear()

    background(255, 0, 255)
    println("Background Magenta")
    resetBackground()

    readln()
    clear()

    background(0, 255, 255)
    println("Background Cyan")
    resetBackground()

    readln()
    clear()

    background(255, 255, 255)
    println("Background White")
    resetBackground()

    readln()
    clear()

    background(0, 0, 0)
    println("Background Black")
    resetBackground()

    readln()
    clear()

    // More...

    readln()
    clear()

    color(255, 0, 0)
    underline()
    blink()
    println("Underline and Blink")
    resetFormat()

    readln()
    clear()

    // Progress bar

    for (i in 0..100) {
      print("█".repeat(i))
      print("▒".repeat(100 - i))
      print(" (${i}%)")
      Thread.sleep(50)
      clearLine()
    }
    println()
  }

  @JvmStatic
  fun install() {
    if (System.getProperty("os.name").lowercase().contains("win")) ANSI.enableWindowsAnsi()
  }
}
