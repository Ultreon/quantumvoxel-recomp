package dev.ultreon.quantum.resource

import dev.ultreon.quantum.util.NamespaceID

@Deprecated("Replaced by resource trees")
class ResourceLocation(val location: NamespaceID, val category: ResourceCategory) : ResourceNode {
  override val isRoot: Boolean
    get() = TODO("Not yet implemented")
  override val name: String
    get() = TODO("Not yet implemented")

  override fun isDirectory(): Boolean = true
  override fun toString(): String = location.toString()
  override fun hashCode(): Int = location.hashCode()
  override fun equals(other: Any?): Boolean = other is ResourceLocation && other.location == location
  fun get(key: String): ResourceNode? {
    return null
  }
}
