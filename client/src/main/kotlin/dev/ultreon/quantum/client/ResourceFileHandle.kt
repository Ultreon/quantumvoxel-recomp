package dev.ultreon.quantum.client

import com.badlogic.gdx.Files
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Queue
import dev.ultreon.quantum.resource.*
import dev.ultreon.quantum.util.NamespaceID
import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset

class ResourceFileHandle(val location: NamespaceID) : FileHandle(location.toString()) {
  var resource: Resource? = QuantumVoxel.resourceManager[location]
  var node: ResourceNode? = QuantumVoxel.resourceManager.getNodeOrNull(location.path)

  override fun exists(): Boolean = resource != null
  override fun name(): String = location.toString().substringAfterLast('/')
  override fun path(): String = location.toString()
  override fun pathWithoutExtension(): String = location.toString().substringBeforeLast('.')

  override fun extension(): String {
    val name = name()
    val dotIndex = name.lastIndexOf('.')
    return if (dotIndex == -1) "" else name.substring(dotIndex + 1)
  }

  override fun nameWithoutExtension(): String {
    val name = name()
    val dotIndex = name.lastIndexOf('.')
    return if (dotIndex == -1) name else name.substring(0, dotIndex)
  }

  override fun parent(): ResourceFileHandle = ResourceFileHandle(location.parent())
  override fun file(): File = throw GdxRuntimeException("Can't access resource as java.io.File!")
  override fun child(name: String): FileHandle = ResourceFileHandle(location.mapPath { "$it/$name" })
  override fun isDirectory(): Boolean = node is ResourceDirectory
  override fun read(): InputStream =
    resource?.inputStream() ?: throw GdxRuntimeException("Resource not found: $location")

  override fun reader(): Reader = resource?.reader() ?: throw GdxRuntimeException("Resource not found: $location")
  override fun type(): Files.FileType = Files.FileType.Internal
  override fun list(): Array<FileHandle> {
    val node = node ?: throw GdxRuntimeException("Resource not found: $location")
    val directory = node.asDirOrNull()
    if (directory != null) {
      return directory.map { ResourceFileHandle(location.mapPath { path -> "$path/${it.name}" }) }.toTypedArray()
    }

    return emptyArray()
  }

  override fun list(filter: FileFilter?): Array<FileHandle> {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun list(filter: FilenameFilter?): Array<FileHandle> {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun list(suffix: String): Array<FileHandle> {
    val node = node ?: throw GdxRuntimeException("Resource not found: $location")
    val directory = node.asDirOrNull()
    if (directory != null) {
      return directory.filter { it.name.endsWith(suffix) }.map { ResourceFileHandle(location.mapPath { path -> "$path/${it.name}" }) }.toTypedArray()
    }

    return emptyArray()
  }

  override fun sibling(name: String): FileHandle {
    return parent().child(name)
  }

  override fun toString(): String = location.toString()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    other as ResourceFileHandle
    return location == other.location
  }

  override fun hashCode(): Int {
    return location.hashCode()
  }

  override fun map(): ByteBuffer {
    val resource = resource ?: throw GdxRuntimeException("Resource not found: $location")
    return resource.map()
  }

  override fun length(): Long {
    val resource = resource ?: throw GdxRuntimeException("Resource not found: $location")
    return resource.length()
  }

  override fun delete(): Boolean {
    if (isDirectory) throw GdxRuntimeException("Can't delete directory resource: $location")
    val node = parent().node
    if (node is ResourceDirectory) {
      return node.remove(location)
    }
    return false
  }

  override fun deleteDirectory(): Boolean {
    if (!isDirectory) throw GdxRuntimeException("Can't delete file resource: $location")
    val node = node ?: throw GdxRuntimeException("Resource not found: $location")
    if (node is ResourceDirectory) {
      return node.remove(location)
    }
    reload()
    return false
  }

  fun reload() {
    resource = QuantumVoxel.resourceManager[location]
    node = QuantumVoxel.resourceManager.getNodeOrNull(location.toString())
  }

  fun close() {
    resource = null
    node = null
  }

  override fun map(mode: FileChannel.MapMode?): ByteBuffer {
    val resource = resource ?: throw GdxRuntimeException("Resource not found: $location")
    return resource.map()
  }

  override fun mkdirs() {
    val queue = Queue<ResourceFileHandle>()
    var cur = this
    while (!cur.exists()) {
      queue.addFirst(cur)
      cur = cur.parent()
    }

    while (!queue.isEmpty) {
      val removeFirst = queue.removeFirst()
      removeFirst.parent().node!!.asDir().mkdir(removeFirst.name())
    }
  }

  override fun write(append: Boolean, bufferSize: Int): OutputStream {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun write(append: Boolean): OutputStream {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun write(input: InputStream?, append: Boolean) {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun writer(append: Boolean): Writer {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun writer(append: Boolean, charset: String?): Writer {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun writeBytes(bytes: ByteArray?, append: Boolean) {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun writeString(string: String?, append: Boolean) {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun writeBytes(bytes: ByteArray?, offset: Int, length: Int, append: Boolean) {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun writeString(string: String?, append: Boolean, charset: String?) {
    throw GdxRuntimeException("This is a virtual resource file handle!")
  }

  override fun read(bufferSize: Int): BufferedInputStream {
    return read().buffered(bufferSize)
  }

  override fun reader(bufferSize: Int): BufferedReader {
    return reader().buffered(bufferSize)
  }

  override fun readBytes(): ByteArray {
    val resource = resource ?: throw GdxRuntimeException("Resource not found: $location")
    return resource.data.copyOf()
  }

  override fun readString(): String {
    val resource = resource ?: throw GdxRuntimeException("Resource not found: $location")
    return String(resource.data)
  }

  override fun readString(charset: String?): String {
    val resource = resource ?: throw GdxRuntimeException("Resource not found: $location")
    return String(resource.data, Charset.forName(charset))
  }

  override fun readBytes(bytes: ByteArray, offset: Int, size: Int): Int {
    val resource = resource ?: throw GdxRuntimeException("Resource not found: $location")
    System.arraycopy(resource.data, 0, bytes, offset, size)
    return size
  }

  override fun reader(charset: String?): Reader {
    val resource = resource ?: throw GdxRuntimeException("Resource not found: $location")
    return StringReader(String(resource.data, Charset.forName(charset)))
  }

  override fun reader(bufferSize: Int, charset: String?): BufferedReader {
    return reader(charset).buffered(bufferSize)
  }

  override fun emptyDirectory() {
    val node = node ?: throw GdxRuntimeException("Resource not found: $location")
    if (node is ResourceDirectory) {
      node.empty()
    }
  }

  override fun emptyDirectory(preserveTree: Boolean) {
    val node = node ?: throw GdxRuntimeException("Resource not found: $location")
    if (node is ResourceDirectory) {
      node.empty(preserveTree)
    }
  }

  override fun copyTo(dest: FileHandle?) {
    val resource = resource ?: throw GdxRuntimeException("Resource not found: $location")
    dest?.writeBytes(resource.data, false)
  }

  override fun moveTo(dest: FileHandle?) {
    throw GdxRuntimeException("Can't move a resource!")
  }

  override fun lastModified(): Long = 0 // TODO Implement last modified time for resources
}

