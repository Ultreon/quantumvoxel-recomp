package dev.ultreon.quantum

interface Logger {
  fun info(message: String)
  fun warn(message: String)
  fun error(message: String)
  fun debug(message: String)
  fun trace(message: String)
}

var factory = LoggerFactory {
  object : Logger {
    override fun info(message: String) {
      // No-op
    }

    override fun warn(message: String) {
      // No-op
    }

    override fun error(message: String) {
      // No-op
    }

    override fun debug(message: String) {
      // No-op
    }

    override fun trace(message: String) {
      // No-op
    }
  }
}

fun interface LoggerFactory {
  fun getLogger(name: String): Logger

  companion object {
    operator fun get(name: String): Logger {
      return factory.getLogger(name)
    }
  }
}

fun main() {
  val logger = LoggerFactory["Quantum"]
  logger.info("Quantum is running!")
  logger.warn("Quantum is running!")
  logger.error("Quantum is running!")
  logger.debug("Quantum is running!")
  logger.trace("Quantum is running!")

  val logger2 = LoggerFactory["Proton"]
  logger2.info("Proton is running!")

  val logger3 = LoggerFactory["Electron"]
  logger3.info("Electron is running!")

  val logger5 = LoggerFactory["Really long name that doesn't fit"]
  logger5.info("Really long name that doesn't fit is running!")

  val logger6 = LoggerFactory["ExtraLongNameWithoutSpaces"]
  logger6.info("ExtraLongNameWithoutSpaces is running!")
}
