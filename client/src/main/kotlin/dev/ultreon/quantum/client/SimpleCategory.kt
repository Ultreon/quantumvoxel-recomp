package dev.ultreon.quantum.client

import dev.ultreon.quantum.resource.Resource
import dev.ultreon.quantum.resource.ResourceCategory
import dev.ultreon.quantum.resource.StaticResource

class SimpleCategory(override val name: String, override val parent: ResourceCategory?) : ResourceCategory {
  val resources: MutableMap<String, Resource> = mutableMapOf()

  override fun get(name: String): ResourceCategory? {
    return null
  }

  override fun get(domain: String, name: String): Resource? {
    return resources["$domain:$name"]
  }

  override fun set(domain: String, filename: String, value: StaticResource) {
    resources["$domain:$filename"] = value
  }

  override fun iterator(): Iterator<Resource> {
    return resources.values.iterator()
  }
}
