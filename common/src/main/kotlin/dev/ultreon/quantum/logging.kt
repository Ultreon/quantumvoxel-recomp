package dev.ultreon.quantum

import com.badlogic.gdx.Gdx

interface Logger {
  fun info(message: String)
  fun warn(message: String)
  fun error(message: String)
  fun debug(message: String)
  fun trace(message: String)
  fun info(message: String, obj: Any?) {
    info(message + " :: " + obj.toString())
  }
  fun warn(message: String, obj: Any?) {
    warn(message + " :: " + obj.toString())
  }
  fun error(message: String, obj: Any?) {
    error(message + " :: " + obj.toString())
  }
  fun debug(message: String, obj: Any?) {
    debug(message + " :: " + obj.toString())
  }
  fun trace(message: String, obj: Any?) {
    trace(message + " :: " + obj.toString())
  }
}

var factory = LoggerFactory {
  object : Logger {
    override fun info(message: String) {
      Gdx.app.applicationLogger.log(it, "[INFO] $message")
    }

    override fun warn(message: String) {
      Gdx.app.applicationLogger.error(it, "[WARN] $message")
    }

    override fun error(message: String) {
      Gdx.app.applicationLogger.error(it, "[ERROR] $message")
    }

    override fun debug(message: String) {
      Gdx.app.applicationLogger.log(it, "[DEBUG] $message")
    }

    override fun trace(message: String) {
      Gdx.app.applicationLogger.debug(it, "[TRACE] $message")
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
