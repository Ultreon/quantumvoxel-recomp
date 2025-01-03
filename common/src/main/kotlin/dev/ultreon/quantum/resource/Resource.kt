package dev.ultreon.quantum.resource

import dev.ultreon.quantum.resource.ResourceNode
import dev.ultreon.quantum.util.NamespaceID
import java.io.InputStream
import java.io.Reader

interface Resource : ResourceNode {
  val location: NamespaceID

  val text: String
  val data: ByteArray

  fun reader(): Reader
  fun inputStream(): InputStream

  override fun isCategory(): Boolean = false
  fun length(): Long {
    return data.size.toLong()
  }
}
