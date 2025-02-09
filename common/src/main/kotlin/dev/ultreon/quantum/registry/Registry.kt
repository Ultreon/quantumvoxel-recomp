package dev.ultreon.quantum.registry

import dev.ultreon.quantum.InternalApi
import dev.ultreon.quantum.resource.ResourceId
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.quantum.util.id
import dev.ultreon.quantum.util.reverseViewOf

private fun <T : Any> registryId(name: NamespaceID, registry: Registry<T>): ResourceId<Registry<T>> {
  return ResourceId.create(name)
}

val registries = Registry<Registry<Any>>(id(path = "regstries")) as Registry<Registry<Any>>

inline fun <reified T : Any> registryFor(name: NamespaceID): Registry<T> {
  return registries[name]?.cast() ?: throw NoSuchElementException("Registry not found: $name")
}

fun <T : Any> registryFor(name: NamespaceID, clazz: Class<T>): Registry<T> {
  return registries[name]?.cast(clazz) ?: throw NoSuchElementException("Registry not found: $name")
}

class Registry<T : Any>(
  name: NamespaceID,
  val type: Class<T>
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

  inline fun <reified R : Any> cast(): Registry<R> {
    return cast(R::class.java)
  }

  fun <R : Any> cast(clazz: Class<R>): Registry<R> {
    if (this.type != clazz) {
      throw ClassCastException()
    }

    @Suppress("UNCHECKED_CAST")
    return this as Registry<R>
  }

  companion object {
    inline operator fun <reified T : Any> invoke(name: NamespaceID): Registry<T> {
      return Registry(name, T::class.java)
    }
  }
}
