package dev.ultreon.quantum.resource

import java.io.IOException

class NoSuchResourceCategoryException(name: String)
  : IOException("No such resource category: $name")
