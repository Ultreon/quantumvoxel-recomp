package dev.ultreon.quantum.resource

import dev.ultreon.quantum.logger
import dev.ultreon.quantum.util.NamespaceID

class ResourceRoot(
  override val name: String,
  val children: MutableMap<String, ResourceNode> = mutableMapOf()
) : Iterable<ResourceNode>, ResourceDir {
  override fun iterator(): Iterator<ResourceNode> = children.values.iterator()
  override fun isDirectory(): Boolean = true

  override operator fun get(name: String): ResourceNode? {
    return children[name]
  }

  override operator fun set(name: String, value: ResourceNode) {
    children[name] = value
  }

  override operator fun contains(name: String): Boolean {
    return children.containsKey(name)
  }

  override fun mkdir(name: String) {
    children[name] = ResourceDirectory(name, "", this)
  }

  override fun remove(name: String) {
    children.remove(name)
  }

  override fun toString(): String {
    return "[$name]"
  }

  override val isRoot: Boolean
    get() = true

  fun retrieve(path: String): ResourceNode? {
    val parts = path.split('/')
    var node: ResourceDir = this
    for (i in 0 until parts.size - 1) {
      node = node[parts[i]] as? ResourceDir ?: throw NoSuchResourceException("No such resource: ${parts[i]}")
    }
    return node[parts[parts.size - 1]]
  }
}

class ResourceDirectory(
  override val name: String,
  val domain: String,
  val parent: ResourceDir,
  val children: MutableMap<String, ResourceNode> = mutableMapOf()
) : Iterable<ResourceNode>, ResourceDir {
  override val isRoot: Boolean get() = false

  override fun iterator(): Iterator<ResourceNode> = children.values.iterator()
  override fun isDirectory(): Boolean = true

  override fun toString(): String = "$domain:$parent/$name"

  override operator fun get(name: String): ResourceNode? {
    return children[name]
  }

  override operator fun set(name: String, value: ResourceNode) {
    children[name] = value
  }

  override operator fun contains(name: String): Boolean {
    return children.containsKey(name)
  }

  fun remove(location: NamespaceID): Boolean {
    val parts = location.path.split('/')
    val name = parts[parts.size - 1]
    return children.remove(name) != null
  }

  override fun mkdir(name: String) {
    children[name] = ResourceDirectory(name, domain, this)
  }

  override fun remove(name: String) {
    children.remove(name)
  }

  fun empty(preserveTree: Boolean = false) {
    if (!preserveTree) {
      children.clear()
    } else {
      children.values.forEach {
        if (it is ResourceDirectory) {
          it.empty(true)
        }
      }
    }
  }
}

class ResourceLeaf(val parent: ResourceDir, override val name: String) : ResourceNode, Iterable<Resource> {
  private val resources = mutableMapOf<String, MutableList<Resource>>()

  override val isRoot: Boolean get() = false

  override fun isDirectory(): Boolean = false
  override fun iterator(): Iterator<Resource> {
    return resources.values.flatten().iterator()
  }

  override fun toString(): String = "$parent/$name"
  operator fun get(domain: String): List<Resource> {
    logger.debug("Getting resources for domain $domain in resource leaf: $this")
    return resources[domain] ?: run {
      logger.error("No resources found for domain $domain in resource leaf: $this")
      emptyList()
    }
  }
  fun addResource(resource: Resource) {
    val domain = resource.location.domain
    val list = resources[domain] ?: mutableListOf<Resource>().also { resources[domain] = it }
    list.add(resource)
  }

  fun isEmpty(): Boolean {
    return resources.values.isEmpty()
  }
}

interface ResourceDir : ResourceNode, Iterable<ResourceNode> {
  operator fun get(name: String): ResourceNode?
  operator fun set(name: String, value: ResourceNode)
  operator fun contains(name: String): Boolean

  fun mkdir(name: String)
  fun remove(name: String)
}

interface ResourceNode {
  val isRoot: Boolean
  val name: String

  fun isDirectory(): Boolean
  fun isResource(): Boolean = !isDirectory()
}

fun ResourceNode.asDir(): ResourceDir = this as ResourceDir

fun ResourceNode.asDirOrNull(): ResourceDir? = this as? ResourceDir

fun ResourceNode.asLeaf(): ResourceLeaf = this as ResourceLeaf

fun ResourceNode.asLeafOrNull(): ResourceLeaf? = this as? ResourceLeaf

fun ResourceDir.asDirectory(): ResourceDirectory = this as ResourceDirectory

fun ResourceDir.asDirectoryOrNull(): ResourceDirectory? = this as? ResourceDirectory

fun ResourceDir.asRoot(): ResourceRoot = this as ResourceRoot

fun ResourceDir.asRootOrNull(): ResourceRoot? = this as? ResourceRoot
