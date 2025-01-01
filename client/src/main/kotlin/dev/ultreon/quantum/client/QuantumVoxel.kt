@file:Suppress("t")

package dev.ultreon.quantum.client

import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.SharedLibraryLoader
import dev.ultreon.quantum.client.model.JsonModelLoader
import dev.ultreon.quantum.client.resource.TexturesCategory
import dev.ultreon.quantum.client.texture.TextureManager
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.resource.ResourceManager
import ktx.app.KtxGame
import ktx.app.KtxScreen
import java.io.FileNotFoundException
import java.util.*
import kotlin.io.path.toPath

val timer = Timer()


object QuantumVoxel : KtxGame<KtxScreen>() {
  val resourceManager: ResourceManager = ResourceManager("client")
  val jsonModelLoader: JsonModelLoader = JsonModelLoader(resourceManager)
  val textureManager: TextureManager = TextureManager(resourceManager)
  private val world = World()

  init {
    resourceManager.register("textures", TexturesCategory(textureManager))
    resourceManager.register("shaders", SimpleCategory("shaders", null))
    resourceManager.register("models", ModelsCategory().apply {
      register("blocks", SimpleCategory("blocks", this))
      register("items", SimpleCategory("items", this))
      register("entities", SimpleCategory("entities", this))
    })
  }

  override fun create() {
    super.create()

    textureManager.init()
    textureManager.registerAtlas("block")

    resourceManager.load(if (SharedLibraryLoader.isAndroid) {
      Gdx.files.internal("quantum.zip")
    } else {
      // Locate resource "._assetroot" and use its parent directory as the root
      val resource = QuantumVoxel::class.java.classLoader.getResource("_assetroot")
      logger.info("Asset root: $resource")
      val path = resource?.toURI()?.toPath()?.parent ?: throw FileNotFoundException("Asset root not found")
      logger.info("Asset root: $path")
      Gdx.files.absolute(path.toString())
    })
    textureManager.pack()

    addScreen(GameScreen(world))
    setScreen<GameScreen>()
  }

  override fun dispose() {
    super.dispose()

    textureManager.disposeSafely()
    world.dispose()
  }
}

