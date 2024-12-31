package dev.ultreon.quantum.resource

import dev.ultreon.quantum.util.NamespaceID
import java.io.IOException

class NoSuchResourceException(path: NamespaceID) : IOException("No such resource: $path")
