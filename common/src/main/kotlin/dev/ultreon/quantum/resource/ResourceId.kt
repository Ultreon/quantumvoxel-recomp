package dev.ultreon.quantum.resource

import dev.ultreon.quantum.registry.Registry
import dev.ultreon.quantum.util.NamespaceID

class ResourceId<T : Any> private constructor(
  val parent: ResourceId<Registry<T>>?,
  val name: NamespaceID
) {
  companion object {
    fun <T : Any> create(name: NamespaceID): ResourceId<T> {
      return ResourceId(null, name)
    }

    fun <T : Any> of(parent: ResourceId<Registry<T>>, name: NamespaceID): ResourceId<T> {
      return ResourceId(parent, name)
    }
  }

  override fun toString(): String {
    return "$name @ ${parent?.toString() ?: "root"}"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is ResourceId<*>) return false

    if (parent != other.parent) return false
    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    var result = parent?.hashCode() ?: 0
    result = 31 * result + name.hashCode()
    return result
  }
}
