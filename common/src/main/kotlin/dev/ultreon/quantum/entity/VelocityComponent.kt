package dev.ultreon.quantum.entity

class VelocityComponent : Component<VelocityComponent>() {
  var x = 0f
  var y = 0f
  var z = 0f

  override val componentType = ComponentType.velocity
}
