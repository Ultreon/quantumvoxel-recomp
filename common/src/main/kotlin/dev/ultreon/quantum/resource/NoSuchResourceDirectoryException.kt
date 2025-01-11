package dev.ultreon.quantum.resource

import java.io.IOException

class NoSuchResourceDirectoryException(name: String)
  : IOException("No such resource directory: $name")
