package dev.ultreon.quantum.client.scripting

data class TSParams(
  val list: List<Pair<String, TSType>> = mutableListOf()
) {
  override fun toString(): String {
    return list.joinToString(", ") { "${it.first}: ${it.second}" }
  }
}
