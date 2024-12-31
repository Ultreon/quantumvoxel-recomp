package dev.ultreon.quantum.util

fun <K, V> reverseViewOf(registry: Map<K, V>): Map<V, K> {
  return ReverseView(registry)
}

private class ReverseView<K, V>(private val normal: Map<K, V>) : Map<V, K> {
  override fun get(key: V): K? {
    return normal.entries.firstOrNull { (_, value) -> value == key }?.key
  }

  override val size: Int
    get() = normal.size

  override fun containsKey(key: V): Boolean {
    return normal.containsValue(key)
  }

  override fun containsValue(value: K): Boolean {
    return normal.containsKey(value)
  }

  override fun isEmpty(): Boolean {
    return normal.isEmpty()
  }

  override val entries: Set<Map.Entry<V, K>>
    get() = ReverseEntrySet(normal)

  override val keys: Set<V>
    get() = normal.values.toSet()

  override val values: Collection<K>
    get() = normal.keys

  private class ReverseEntry<K, V>(val entry: Map.Entry<K, V>) : Map.Entry<V, K> {
    override val key: V
      get() = entry.value
    override val value: K
      get() = entry.key
  }

  private class ReverseEntrySet<K, V>(private val map: Map<K, V>) : Set<Map.Entry<V, K>> {
    override val size: Int = map.size

    override fun contains(element: Map.Entry<V, K>): Boolean {
      return map[element.value] == element.key
    }

    override fun containsAll(elements: Collection<Map.Entry<V, K>>): Boolean {
      return elements.all { map[it.value] == it.key }
    }

    override fun isEmpty(): Boolean {
      return map.isEmpty()
    }

    override fun iterator(): Iterator<Map.Entry<V, K>> {
      return map.entries.asSequence().map { ReverseEntry(it) }.iterator()
    }
  }
}
