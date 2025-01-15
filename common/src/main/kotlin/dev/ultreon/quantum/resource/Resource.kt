package dev.ultreon.quantum.resource

import dev.ultreon.quantum.util.NamespaceID
import java.io.InputStream
import java.io.Reader
import java.nio.ByteBuffer

interface Resource {
  val location: NamespaceID

  val text: String
  val data: ByteArray

  fun reader(): Reader
  fun inputStream(): InputStream
  fun length(): Long = data.size.toLong()
  fun map(): ByteBuffer = ByteBuffer.wrap(data)
}
