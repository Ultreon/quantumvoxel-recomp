package dev.ultreon.quantum.client.texture

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.PixmapPacker
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import dev.ultreon.quantum.client.resource.TextureCategory
import dev.ultreon.quantum.client.resource.TexturesCategory
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.resource.ResourceManager
import dev.ultreon.quantum.resource.StaticResource
import dev.ultreon.quantum.util.NamespaceID
import ktx.assets.disposeSafely

class TextureManager(val resourceManager: ResourceManager) {
  private val atlases: MutableMap<String, TextureAtlas> = HashMap()
  private lateinit var texturesCategory: TexturesCategory
  private val packers: MutableMap<String, PixmapPacker> = HashMap()

  private lateinit var fallbackTexture: TextureRegion

  fun init() {
    texturesCategory = resourceManager["textures"] as TexturesCategory
    fallbackTexture = TextureRegion(Pixmap(2, 2, Format.RGB888).let { pixmap ->
      pixmap.setColor(1f, 0.5f, 0.0f, 1f)
      pixmap.drawPixel(0, 0)
      pixmap.drawPixel(1, 1)
      Texture(pixmap).also { _ ->
        pixmap.disposeSafely()
      }
    }, 0, 0, 2, 2)
  }

  fun registerAtlas(name: String) {
    val skylineStrategy = PixmapPacker.SkylineStrategy()
    packers[name] = PixmapPacker(4096, 4096, Format.RGBA8888, 0, false, skylineStrategy)

    texturesCategory.register(name, TextureCategory(texturesCategory, this, name))
  }

  fun pack() {
    packers.forEach { (name, packer) ->
      logger.debug("Packing atlas: $name")
      texturesCategory[name]?.forEach { resource ->
        if (resource is StaticResource) {
          val location = resource.location
          logger.debug("Packing texture: $location")
          packer.pack("$location", Pixmap(resource.data, 0, resource.data.size))
        }
      } ?: logger.error("Atlas not found: $name")

      val generateTextureAtlas =
        packer.generateTextureAtlas(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, false)

      atlases[name] = generateTextureAtlas
      packer.dispose()
    }

    packers.clear()
  }

  operator fun get(texture: NamespaceID): TextureRegion {
    val atlas = atlases[texture.path.split("/")[0]] ?: run {
      logger.warn("Atlas not found: ${texture.path.split("/")[0]}")
      return fallbackTexture
    }
    return atlas.findRegion("$texture") ?: run {
      logger.warn("Texture not found: $texture")
      fallbackTexture
    }
  }

  fun disposeSafely() {
    atlases.forEach { (_, atlas) ->
      atlas.disposeSafely()
    }

    fallbackTexture.texture.disposeSafely()

    packers.forEach { (_, packer) ->
      packer.disposeSafely()
    }
  }
}
