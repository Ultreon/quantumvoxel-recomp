package dev.ultreon.quantum.resource

import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.scripting.ContextAware
import dev.ultreon.quantum.util.NamespaceID
import java.io.InputStream
import java.io.Reader
import java.nio.ByteBuffer

private val jsonReader = JsonReader()

interface Resource : ContextAware<Resource> {
  val location: NamespaceID

  val text: String
  val data: ByteArray

  fun reader(): Reader
  fun inputStream(): InputStream
  fun length(): Long = data.size.toLong()
  fun map(): ByteBuffer = ByteBuffer.wrap(data)
  fun json(): JsonValue {
    return jsonReader.parse(reader())
  }
}
