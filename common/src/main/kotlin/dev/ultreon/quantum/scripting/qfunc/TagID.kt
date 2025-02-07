package dev.ultreon.quantum.scripting.qfunc

import dev.ultreon.quantum.util.NamespaceID

class TagID(domain: String, path: String) {
  val namespace: NamespaceID = NamespaceID.of(domain, path)
}
