package dev.ultreon.quantum.resource

import dev.ultreon.quantum.util.NamespaceID
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.Reader
import java.io.StringReader

class StaticResource(override val location: NamespaceID, byteArray: ByteArray) : Resource {
  override val text: String get() = data.toString(Charsets.UTF_8)
  override val data = byteArray

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
