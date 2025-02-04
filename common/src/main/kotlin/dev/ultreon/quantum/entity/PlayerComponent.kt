package dev.ultreon.quantum.entity

abstract class PlayerComponent<T : PlayerComponent<T>>(val name: String = "Player") : Component<T>() {

}
