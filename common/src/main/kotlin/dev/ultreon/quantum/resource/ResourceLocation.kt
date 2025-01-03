package dev.ultreon.quantum.resource

import dev.ultreon.quantum.util.NamespaceID

class ResourceLocation(val location: NamespaceID, val category: ResourceCategory) : ResourceNode {
  override fun isCategory(): Boolean = true
  override fun toString(): String = location.toString()
  override fun hashCode(): Int = location.hashCode()
  override fun equals(other: Any?): Boolean = other is ResourceLocation && other.location == location
  fun get(key: String): ResourceNode? {
    return category[location.path + "/" + key]?.let { ResourceLocation(location.mapPath { "$it/$key" }, category) }
      ?: category[location.domain, location.path + "/" + key]
      ?: throw NoSuchResourceException(location.mapPath { "$it/$key" })
  }
}