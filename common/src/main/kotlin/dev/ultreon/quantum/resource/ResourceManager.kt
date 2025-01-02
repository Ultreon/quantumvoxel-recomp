package dev.ultreon.quantum.resource

import com.badlogic.gdx.files.FileHandle
import dev.ultreon.quantum.LoggerFactory
import dev.ultreon.quantum.util.NamespaceID
import java.util.zip.ZipInputStream
import kotlin.math.log

private val logger = LoggerFactory["QV:Resources"]

/**
 * The `ResourceManager` class is responsible for managing and organizing resources within a specified asset root.
 * It provides functionality to register, load, and retrieve resources and resource categories.
 *
 * @constructor Initializes the `ResourceManager` with the specified root for asset management.
 * @param assetRoot The root directory for managing assets.
 */
class ResourceManager(
  private val assetRoot: String,
) {
  private val categories = mutableMapOf<String, ResourceCategory>()

  /**
   * Retrieves a `ResourceCategory` by its name.
   *
   * @param name The name of the resource category to retrieve.
   * @return The `ResourceCategory` associated with the given name.
   * @throws NoSuchResourceCategoryException If no category with the specified name is found.
   */
  operator fun get(name: String): ResourceCategory {
    return categories[name] ?: throw NoSuchResourceCategoryException(name)
  }

  /**
   * Registers a new `ResourceCategory` in the resources with the specified name.
   *
   * @param name The unique name identifying the resource category being registered.
   * @param category The `ResourceCategory` object to associate with the given name.
   */
  fun register(name: String, category: ResourceCategory) {
    categories[name] = category
  }

  /**
   * Retrieves a `Resource` from the resources using the given `NamespaceID`.
   *
   * @param location The `NamespaceID` representing the hierarchical location of the resource
   * to retrieve, including its domain and path.
   * @return The `Resource` corresponding to the specified `NamespaceID`.
   * @throws NoSuchResourceCategoryException If a required resource category is not found
   * while navigating the hierarchy.
   * @throws NoSuchResourceException If the desired resource is not found in the specified
   * category or subcategory.
   */
  @Throws(NoSuchResourceCategoryException::class, NoSuchResourceException::class)
  operator fun get(location: NamespaceID): Resource {
    val path = location.path.split('/')
    var category = categories[path[0]] ?: throw NoSuchResourceCategoryException(path[0])

    for (i in 1 until path.size - 1) {
      val resourceNode = category[path[i]]
      category = resourceNode ?: throw NoSuchResourceException(location)
    }
    
    return category[location.domain, path.subList(1, path.size).joinToString("/")] ?: throw NoSuchResourceException(location)
  }

  /**
   * Loads resources from the specified zip file or directory.
   *
   * @param file The `FileHandle` representing the file or directory to load resources from.
   */
  fun load(file: FileHandle) {
    when {
      file.isDirectory -> loadDirectory(file)
      file.extension() == "zip" -> ZipInputStream(file.read()).use { loadZip(it) }
      else -> logger.error("Unknown file type: ${file.extension()}")
    }
  }

  /**
   * Loads and processes resources from the provided `ZipInputStream`. The method iterates through
   * all entries in the zip stream, extracting and categorizing resources based on their file paths.
   * 
   * Only files within a designated asset root are loaded. Files are categorized based on their 
   * hierarchical paths and associated with specific resource domains. Resource categories and 
   * filenames are processed to organize resources appropriately.
   * 
   * If an entry represents a directory, it is skipped. For valid file entries, the method determines 
   * the domain, category path, and filename to load the resource correctly.
   * 
   * Resource categories referenced in the hierarchy must exist; otherwise, an error is logged.
   * 
   * @param zip The `ZipInputStream` to read and extract resources from.
   * @throws NoSuchResourceCategoryException If an unknown resource category is encountered.
   */
  fun loadZip(zip: ZipInputStream) {
    while (true) {
      val entry = zip.nextEntry ?: break

      if (!entry.isDirectory) {
        val name = entry.name
        if (name.startsWith("$assetRoot/")) {
          val domain = name.substring(assetRoot.length + 1)
          val domainId = domain.substring(0, domain.indexOf('/'))
          logger.debug("Loading domain: $domainId")
          val path = domain.substring(domain.indexOf('/') + 1).split('/')
          logger.debug("Loading resource category: $path")
          val categories = path.dropLast(1)
          logger.debug("Category path: $categories")
          val filename = path.last()
          logger.debug("Category filename: $filename")
          loadCategory(zip, categories.toMutableList(), null, filename, domainId)
        }
      }
    }
  }

  private fun loadCategory(zip: ZipInputStream, categories: MutableList<String>, category: ResourceCategory? = null, filename: String, domain: String, parent: String = "") {
    val path = if (parent == "") "${categories[0]}/$filename" else "$parent/${categories[0]}/$filename"
    logger.debug("Loading resource category: $domain:$path")
    logger.debug("Remaining path: ${categories.joinToString("/")}")
    logger.debug("Parent Category: ${category?.name}")
    logger.debug("Parent Category Path: $parent")
    logger.debug("Category Name: ${categories[0]}")

    val resourceCategory = (if (category != null) category[categories[0]] else this.categories[categories[0]])
    if (resourceCategory == null) {
      logger.error("Unknown category: ${categories[0]}")
      throw NoSuchResourceCategoryException(categories[0])
    }

    val categoryName = categories.removeAt(0)

    if (categories.isEmpty()) {
      resourceCategory ?: throw NoSuchResourceCategoryException(path)
      resourceCategory[domain, filename] = StaticResource(NamespaceID.of(domain, path), zip.readBytes())
    } else {
      loadCategory(zip, categories, category?.get(categoryName) ?: this.categories[categoryName], filename, domain, if (parent == "") categoryName else "$parent/$categoryName")
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
