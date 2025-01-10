package dev.ultreon.quantum.client.texture

import dev.ultreon.quantum.client.resource.TextureCategory
import dev.ultreon.quantum.client.resource.TexturesCategory
import dev.ultreon.quantum.resource.Resource
import dev.ultreon.quantum.resource.ResourceCategory
import dev.ultreon.quantum.resource.StaticResource

class GuiTextureCategory(texturesCategory: TexturesCategory, textureManager: TextureManager, name: String) :
  TextureCategory(texturesCategory, textureManager, name) {

  val categories = mutableMapOf<String, ResourceCategory>()
  val resources = mutableMapOf<String, Resource>()

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
}
