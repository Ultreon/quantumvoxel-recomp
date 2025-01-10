package dev.ultreon.quantum.client.texture

import dev.ultreon.quantum.resource.Resource
import dev.ultreon.quantum.resource.ResourceCategory
import dev.ultreon.quantum.resource.StaticResource

class GuiSubTextureCategory(override val parent: ResourceCategory, val textureManager: TextureManager, override val name: String) :
  ResourceCategory {
  private val categories: MutableMap<String, ResourceCategory> = mutableMapOf()
  private val resources: MutableMap<String, Resource> = mutableMapOf()

  override fun get(name: String): ResourceCategory? {
    if (name !in categories) {
      val resourceCategory = GuiSubTextureCategory(this, textureManager, name)
      categories[name] = resourceCategory
    }

    return categories[name]
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
