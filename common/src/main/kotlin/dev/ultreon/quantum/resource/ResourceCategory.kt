package dev.ultreon.quantum.resource

import dev.ultreon.quantum.resource.Resource
import dev.ultreon.quantum.resource.StaticResource

interface ResourceCategory : ResourceNode, Iterable<Resource> {
  val name: String
  val parent: ResourceCategory?

  operator fun get(name: String): ResourceCategory?
  operator fun get(domain: String, name: String): Resource?
  override fun isCategory(): Boolean = true
  operator fun set(domain: String, filename: String, value: StaticResource) {
    this[domain, filename] = value
  }

  override operator fun iterator(): Iterator<Resource>
}
