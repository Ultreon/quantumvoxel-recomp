package dev.ultreon.quantum.entity

import com.badlogic.gdx.graphics.g3d.model.Animation
import com.badlogic.gdx.graphics.g3d.utils.AnimationController

class AnimationComponent() : Component<AnimationComponent>() {
  var animation: Animation? = null
  var animationController: AnimationController? = null

  override val componentType = ComponentType.animation

  fun update(delta: Float) {
    animationController?.update(delta)
  }
}
