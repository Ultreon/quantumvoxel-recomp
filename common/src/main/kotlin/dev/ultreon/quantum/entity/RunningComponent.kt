package dev.ultreon.quantum.entity

class RunningComponent(var runSpeedModifier: Float = 1F, var running: Boolean = false) : Component<RunningComponent>() {
  override val componentType = ComponentType.running
}
