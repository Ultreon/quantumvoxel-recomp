package dev.ultreon.quantum.client.model

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder
import com.badlogic.gdx.math.Vector3
import dev.ultreon.quantum.util.NamespaceID
import ktx.assets.disposeSafely

@Suppress("unused")
interface BlockModel : LoadableResource {
  val isCustom: Boolean

  fun loadInto(builder: MeshPartBuilder, faceCull: FaceCull) {
    // Do nothing
  }

  val model: Model?

  fun dispose() {
    model?.disposeSafely()
  }

  fun resourceId(): NamespaceID?

  val itemScale: Vector3
    get() = Vector3(0.0625f, 0.0625f, 0.0625f)

  val itemOffset: Vector3
    get() = Vector3(0f, -20f, 0f)

  val buriedTexture: TextureRegion?
    get() = null

  companion object {
    val DEFAULT_ITEM_SCALE: Vector3 = Vector3(1f, 1f, 1f)
  }
}
