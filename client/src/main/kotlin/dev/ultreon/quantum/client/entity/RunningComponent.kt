package dev.ultreon.quantum.client.entity

import com.artemis.Component
import dev.ultreon.quantum.math.Vector3D

class RunningComponent(var runSpeedModifier: Float = 1F, var running: Boolean = false) : Component()
