package dev.ultreon.quantum.registry

import dev.ultreon.quantum.InternalApi
import dev.ultreon.quantum.resource.ResourceId
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.quantum.util.reverseViewOf

private fun <T : Any> registryId(name: NamespaceID, registry: Registry<T>): ResourceId<Registry<T>> {
  return ResourceId.create(name)
}

class Registry<T : Any>(
  name: NamespaceID
) {
  val registry: MutableMap<ResourceId<T>, T> = mutableMapOf()

  val reverseRegistry: Map<T, ResourceId<T>> = reverseViewOf(registry)

  var frozen = false
    private set

  val id: ResourceId<Registry<T>> = registryId(name, this)

  fun register(name: NamespaceID, value: T): ResourceId<T> {
    check(!frozen) { "Registry is frozen" }
    val resourceId = ResourceId.of(id, name)
    registry[resourceId] = value
    return resourceId
  }

  operator fun get(name: NamespaceID): T? {
    return registry.entries.firstOrNull { (key, _) -> key.name == name }?.value
  }

  @Suppress("USELESS_CAST") // It's not useless!
  operator fun <R : T> get(id: ResourceId<R>, type: Class<R>): R? {
    return type.cast(registry[id as ResourceId<*>])
  }

  inline operator fun <reified R : T> get(id: ResourceId<R>): R? {
    return get(id, R::class.java)
  }

  operator fun contains(name: NamespaceID): Boolean {
    return registry.entries.any { (key, _) -> key.name == name }
  }

  val ids: Set<NamespaceID>
    get() = registry.keys.map { it.name }.toSet()

  val keys: Set<ResourceId<T>>
    get() = registry.keys

  val values: Collection<T>
    get() = registry.values

  fun freeze() {
    frozen = true
  }

  @InternalApi
  fun unfreeze() {
    frozen = false
  }

  operator fun get(obj: T): NamespaceID? {
    return reverseRegistry[obj]?.name
  }

  fun getKey(obj: T): ResourceId<T>? {
    return reverseRegistry[obj]
  }
}
