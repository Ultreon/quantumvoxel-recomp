package dev.ultreon.quantum.resource

interface ResourceCategory : Iterable<Resource> {
  val name: String
  val parent: ResourceCategory?

  operator fun get(name: String): ResourceCategory?
  operator fun get(domain: String, name: String): Resource?
  operator fun set(domain: String, filename: String, value: StaticResource) {
    this[domain, filename] = value
  }
  override operator fun iterator(): Iterator<Resource>
}
