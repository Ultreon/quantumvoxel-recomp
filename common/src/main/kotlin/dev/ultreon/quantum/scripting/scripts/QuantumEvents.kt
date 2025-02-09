package dev.ultreon.quantum.scripting.scripts

import dev.ultreon.scriptic.ScripticLang
import dev.ultreon.scriptic.lang.obj.Event

object QuantumEvents {
  val whenCalledEvt = ScripticLang.registerEvent("^when called:$", Event())
}
