package dev.ultreon.quantum.resource

import com.badlogic.gdx.files.FileHandle
import dev.ultreon.quantum.LoggerFactory
import dev.ultreon.quantum.util.NamespaceID
import java.util.zip.ZipInputStream

private val logger = LoggerFactory["QV:Resources"]

class ResourceManager(
  private val assetRoot: String,
) {
  private val categories = mutableMapOf<String, ResourceCategory>()

  operator fun get(name: String): ResourceCategory {
    return categories[name] ?: throw NoSuchResourceCategoryException(name)
  }

  fun register(name: String, category: ResourceCategory) {
    categories[name] = category
  }

  @Throws(NoSuchResourceCategoryException::class, NoSuchResourceException::class)
  operator fun get(location: NamespaceID): Resource {
    val path = location.path.split('/')
    var category = categories[path[0]] ?: throw NoSuchResourceCategoryException(path[0])

    for (i in 1 until path.size - 1) {
      val resourceNode = category[path[i]]
      category = resourceNode as ResourceCategory? ?: throw NoSuchResourceException(location)
    }
    
    return category[location.domain, path.subList(1, path.size).joinToString("/")] ?: throw NoSuchResourceException(location)
  }

  fun load(file: FileHandle) {
    when {
      file.isDirectory -> {
        loadDirectory(file)
      }
      file.extension() == "zip" -> {
        loadZip(file)
      }
      else -> {
        logger.error("Unknown file type: ${file.extension()}")
      }
    }
  }

  private fun loadZip(file: FileHandle) {
    val zip = ZipInputStream(file.read())

    while (true) {
      val entry = zip.nextEntry ?: break

      if (!entry.isDirectory) {
        val name = entry.name
        if (name.startsWith("$assetRoot/")) {
          val domain = name.substring(assetRoot.length + 1)
          val domainId = domain.substring(0, domain.indexOf('/'))
          val path = domain.substring(domain.indexOf('/') + 1).split('/')
          val categories = path.dropLast(1)
          val filename = categories.last()
          loadCategory(zip, categories.toMutableList(), filename, domainId)
        }
      }
    }
  }

  private fun loadCategory(zip: ZipInputStream, categories: MutableList<String>, filename: String, domain: String, parent: String = "") {
    logger.debug("Loading resource category: $domain:$parent/$categories[0]/$filename")

    if (this.categories[categories[0]] == null) {
      throw NoSuchResourceCategoryException(categories[0])
    }

    val category = categories.removeAt(0)

    if (categories.isEmpty()) {
      val resourceCategory = this.categories[category] ?: throw NoSuchResourceCategoryException(category)
      resourceCategory[domain, filename] = StaticResource(NamespaceID.of(domain, "$parent/$category/$filename"), zip.readBytes())
    } else {
      loadCategory(zip, categories, filename, domain, "$parent/$category")
    }
  }

  private fun loadDirectory(file: FileHandle) {
    val list = file.child(assetRoot).list()
    if (list.isEmpty()) {
      logger.warn("No files in directory: ${file.path()}")
      return
    }
    list.forEach {
      if (it.isDirectory) {
        val domain = it.name()
        logger.debug("Loading domain: $domain")
        it.list().forEach last@{ category ->
          if (category.isDirectory) {
            val categoryPath = "$assetRoot/$domain/${category.name()}"
            val category1 = categories[category.name()]
            if (category1 == null) {
              logger.warn("Unknown category: $categoryPath" )
              return@last
            }
            loadCategory(category, domain, category1)
          }
        }
      }
    }
  }

  private fun loadCategory(file: FileHandle, domain: String, category: ResourceCategory? = null, path: String = "") {
    file.list().forEach {
      when {
        it.isDirectory -> loadCategory(it, domain, category!![it.name()] ?: throw NoSuchResourceCategoryException(it.name()), path + it.name() + "/")
        category != null -> loadResource(it, category, domain, path + it.name())
        else -> logger.warn("No category for file: $it")
      }
    }
  }

  private fun loadResource(file: FileHandle, category: ResourceCategory, domain: String, path: String) {
    val readBytes = file.readBytes()
    if (readBytes.isEmpty()) {
      logger.warn("Empty file: $file")
    }

    category[domain, path] = StaticResource(NamespaceID.of(domain, path), readBytes)
  }
}
