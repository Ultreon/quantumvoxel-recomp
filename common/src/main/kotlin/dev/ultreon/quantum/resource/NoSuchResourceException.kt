package dev.ultreon.quantum.resource

import dev.ultreon.quantum.util.NamespaceID
import java.io.IOException

class NoSuchResourceException(message: String) : IOException(message) {
  constructor(path: NamespaceID) : this("No such resource: $path")
}
