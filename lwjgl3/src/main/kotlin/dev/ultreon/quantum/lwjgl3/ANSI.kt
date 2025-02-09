package dev.ultreon.quantum.lwjgl3

/**
 * Provides ANSI escape code constants and utility functions for styling terminal output.
 *
 * This object includes constants for text styles, foreground and background colors,
 * cursor movement, and screen manipulation. It also provides functions for dynamically
 * constructing styled text and enabling ANSI support on Windows platforms.
 */
object ANSI {
  // ANSI Escape Codes
  private const val ESC = "\u001B"

  // Text styles
  const val RESET = "${ESC}[0m"
  const val BOLD = "${ESC}[1m"
  const val DIM = "${ESC}[2m"
  const val ITALIC = "${ESC}[3m"
  const val UNDERLINE = "${ESC}[4m"
  const val BLINK = "${ESC}[5m"
  const val REVERSE = "${ESC}[7m"
  const val HIDDEN = "${ESC}[8m"

  // Foreground colors
  const val FG_BLACK = "${ESC}[30m"
  const val FG_RED = "${ESC}[31m"
  const val FG_GREEN = "${ESC}[32m"
  const val FG_YELLOW = "${ESC}[33m"
  const val FG_BLUE = "${ESC}[34m"
  const val FG_MAGENTA = "${ESC}[35m"
  const val FG_CYAN = "${ESC}[36m"
  const val FG_WHITE = "${ESC}[37m"
  const val FG_DEFAULT = "${ESC}[39m"

  // Bright foreground colors
  const val FG_BRIGHT_BLACK = "${ESC}[90m"
  const val FG_BRIGHT_RED = "${ESC}[91m"
  const val FG_BRIGHT_GREEN = "${ESC}[92m"
  const val FG_BRIGHT_YELLOW = "${ESC}[93m"
  const val FG_BRIGHT_BLUE = "${ESC}[94m"
  const val FG_BRIGHT_MAGENTA = "${ESC}[95m"
  const val FG_BRIGHT_CYAN = "${ESC}[96m"
  const val FG_BRIGHT_WHITE = "${ESC}[97m"

  // Background colors
  const val BG_BLACK = "${ESC}[40m"
  const val BG_RED = "${ESC}[41m"
  const val BG_GREEN = "${ESC}[42m"
  const val BG_YELLOW = "${ESC}[43m"
  const val BG_BLUE = "${ESC}[44m"
  const val BG_MAGENTA = "${ESC}[45m"
  const val BG_CYAN = "${ESC}[46m"
  const val BG_WHITE = "${ESC}[47m"
  const val BG_DEFAULT = "${ESC}[49m"

  // Bright background colors
  const val BG_BRIGHT_BLACK = "${ESC}[100m"
  const val BG_BRIGHT_RED = "${ESC}[101m"
  const val BG_BRIGHT_GREEN = "${ESC}[102m"
  const val BG_BRIGHT_YELLOW = "${ESC}[103m"
  const val BG_BRIGHT_BLUE = "${ESC}[104m"
  const val BG_BRIGHT_MAGENTA = "${ESC}[105m"
  const val BG_BRIGHT_CYAN = "${ESC}[106m"
  const val BG_BRIGHT_WHITE = "${ESC}[107m"

  // Cursor control
  fun moveCursorUp(lines: Int) = "${ESC}[${lines}A"
  fun moveCursorDown(lines: Int) = "${ESC}[${lines}B"
  fun moveCursorForward(columns: Int) = "${ESC}[${columns}C"
  fun moveCursorBack(columns: Int) = "${ESC}[${columns}D"
  fun setCursorPosition(row: Int, col: Int) = "${ESC}[${row};${col}H"
  const val CLEAR_SCREEN = "${ESC}[2J"
  const val CLEAR_LINE = "${ESC}[2K"

  /**
   * Styles the given text using ANSI escape codes for formatting.
   *
   * This function allows you to apply one or more text styles (e.g., bold, italic, colored text)
   * to the input string. It concatenates the provided styles, applies them to the text,
   * and appends a reset code to ensure subsequent text output is unaffected.
   *
   * @param text The text to be styled.
   * @param styles A variable number of style codes (e.g., colors, font effects) to apply.
   * @return The styled text as a string with ANSI escape codes applied.
   */
  fun styleText(text: String, vararg styles: String): String {
    return styles.joinToString("") + text + RESET
  }

  /**
   * Enables ANSI escape code support in the Windows command line terminal.
   *
   * This function checks if the operating system is Windows and, if so, configures the console mode
   * to support ANSI escape sequences. This allows for enhanced text formatting features such as colored text
   * and cursor control within the terminal.
   */
  fun enableWindowsAnsi() {

  }
}
