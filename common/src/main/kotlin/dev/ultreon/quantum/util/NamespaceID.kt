package dev.ultreon.quantum.util

import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.resource.ResourceManager
import dev.ultreon.quantum.scripting.ContextAware
import dev.ultreon.quantum.scripting.ContextType
import dev.ultreon.quantum.scripting.ContextValue
import dev.ultreon.quantum.scripting.PersistentData
import org.intellij.lang.annotations.Language

data class NamespaceID(
  val domain: String,
  val path: String
) : Comparable<NamespaceID>, ContextAware<NamespaceID> {
  fun mapPath(mapper: (String) -> String): NamespaceID = NamespaceID(domain, mapper(path))
  fun mapDomain(mapper: (String) -> String): NamespaceID = NamespaceID(mapper(domain), path)

  override val persistentData: PersistentData = PersistentData()
  override fun contextType(): ContextType<NamespaceID> = ContextType.id

  override fun fieldOf(name: String, contextJson: JsonValue?): ContextValue<*>? {
    return when (name) {
      "domain" -> ContextValue(ContextType.string, domain)
      "path" -> ContextValue(ContextType.string, path)
      else -> null
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as NamespaceID
    if (domain != other.domain) return false
    if (path != other.path) return false
    return true
  }

  override fun hashCode(): Int {
    var result = domain.hashCode()
    result = 31 * result + path.hashCode()
    return result
  }

  override fun compareTo(other: NamespaceID): Int {
    val domainComparison = domain.compareTo(other.domain)
    return if (domainComparison == 0) path.compareTo(other.path) else domainComparison
  }

  companion object {
    @Language("RegExp")
    const val PATTERN: String = "([a-z0-9_\\-]+):([a-z0-9_\\-/.]+)"

    fun parse(string: String): NamespaceID {
      val index = string.indexOf(':')
      if (index == -1) return NamespaceID("quantum", validatePath(string))
      return NamespaceID(validateDomain(string.substring(0, index)), validatePath(string.substring(index + 1)))
    }

    fun validateDomain(domain: String): String {
      require(domain.isNotBlank()) { "Domain must not be blank" }
      require(domain.length <= 16) { "Domain must be less than 16 characters" }
      require(domain.matches(Regex("[a-z0-9_\\-]+"))) { "Domain must only contain lowercase letters, numbers, underscores and dashes, $domain is not valid" }
      require(domain[0].isLetter()) { "Domain must start with a letter" }
      return domain
    }

    fun validatePath(path: String): String {
      require(path.isNotBlank()) { "Path must not be blank" }
      require(path.length <= 256) { "Path must be less than 256 characters" }
      require(path.matches(Regex("[a-z0-9_\\-/.]+"))) { "Path must only contain lowercase letters, numbers, underscores and dashes, $path is not valid" }
      return path
    }

    fun validateDomainOrNull(domain: String?): String? {
      return if (domain == null) null else {
        if (domain.isBlank()) return null
        if (domain.length > 16) return null
        if (!domain.matches(Regex("[a-z0-9_\\-]+"))) return null
        if (!domain[0].isLetter()) return null
        domain
      }
    }

    fun validatePathOrNull(path: String?): String? {
      return if (path == null) null else {
        if (path.isBlank()) return null
        if (path.length > 32) return null
        if (!path.matches(Regex("[a-z0-9_\\-/.]+"))) return null
        path
      }
    }

    fun parseOrNull(string: String): NamespaceID? {
      val index = string.indexOf(':')
      if (index == -1) {
        val path = validatePathOrNull(string)
        return if (path == null) null else NamespaceID("quantum", path)
      }
      val domain = validateDomainOrNull(string.substring(0, index))
      val path = validatePathOrNull(string.substring(index + 1))
      return if (domain == null || path == null) null else NamespaceID(domain, path)
    }

    fun of(domain: String = "quantum", path: String): NamespaceID {
      return NamespaceID(validateDomain(domain), validatePath(path))
    }
  }

  override fun toString(): String = "$domain:$path"
  fun parent(): NamespaceID {
    return mapPath {
      val index = it.lastIndexOf('/')
      if (index == -1) throw GdxRuntimeException("Already at root!") else it.substring(0, index)
    }
  }
}

fun id(domain: String = "quantum", path: String): NamespaceID = NamespaceID.of(domain, path)

fun String.asId(): NamespaceID = NamespaceID.parse(this)

fun String.asIdOrNull(): NamespaceID? = NamespaceID.parseOrNull(this)
