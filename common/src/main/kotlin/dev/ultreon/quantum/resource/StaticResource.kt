package dev.ultreon.quantum.resource

import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.scripting.ContextType
import dev.ultreon.quantum.scripting.ContextValue
import dev.ultreon.quantum.scripting.PersistentData
import dev.ultreon.quantum.util.NamespaceID
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.Reader
import java.io.StringReader

class StaticResource(override val location: NamespaceID, byteArray: ByteArray) : Resource {
  override val text: String get() = data.toString(Charsets.UTF_8)
  override val data = byteArray

  override val persistentData: PersistentData = PersistentData()
  override fun contextType(): ContextType<Resource> = ContextType.resource

  override fun fieldOf(name: String, contextJson: JsonValue?): ContextValue<*>? {
    return when (name) {
      "text" -> ContextValue(ContextType.string, text)
      "data" -> ContextValue(ContextType.binary, data)
      "json" -> ContextValue(ContextType.json, json())
      "location" -> ContextValue(ContextType.id, location)
      else -> null
    }
  }

  override fun inputStream(): InputStream = ByteArrayInputStream(data)
  override fun reader(): Reader = StringReader(text)
  override fun toString(): String {
    return location.toString()
  }

  override fun hashCode(): Int {
    return location.hashCode()
  }

  override fun equals(other: Any?): Boolean {
    return other is StaticResource && other.location == location
  }
}
