package dev.ultreon.quantum.client.scripting

data class TSProperty(
  val name: String,
  val type: TSType,
  val getter: Boolean = true,
  val setter: Boolean = true,
  val static: Boolean = false
) {
  override fun toString(): String {
    if (static) {
      if (getter && setter) {
        return "static $name: $type"
      }
      if (getter) {
        return "static get $name(): $type"
      }
      if (setter) {
        return "static set $name(value): $type"
      }
      return ""
    }

    if (getter && setter) {
      return "$name: $type"
    }
    if (getter) {
      return "set $name(value): $type"
    }
    if (setter) {
      return "get $name(): $type"
    }
    return ""
  }
}
