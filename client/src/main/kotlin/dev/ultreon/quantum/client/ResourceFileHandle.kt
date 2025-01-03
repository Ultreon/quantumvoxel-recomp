package dev.ultreon.quantum.client

import com.badlogic.gdx.Files
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.GdxRuntimeException
import dev.ultreon.quantum.resource.Resource
import dev.ultreon.quantum.resource.ResourceCategory
import dev.ultreon.quantum.util.NamespaceID
import java.io.*

class ResourceFileHandle(val resource: Resource) : FileHandle(resource.location.toString()) {
  override fun exists(): Boolean = true
  override fun name(): String = resource.location.toString().substringAfterLast('/')
  override fun path(): String = resource.location.toString()
  override fun pathWithoutExtension(): String = resource.location.toString().substringBeforeLast('.')
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

  override fun parent(): FileHandle = ResourceVfsFileHandle(resource.location.parent())
  override fun file(): File = throw GdxRuntimeException("Can't access resource as java.io.File!")
  override fun child(name: String?): FileHandle = throw GdxRuntimeException("Resource is a file!")
  override fun isDirectory(): Boolean = false
  override fun read(): InputStream = resource.inputStream()
  override fun reader(): Reader = resource.reader()
  override fun type(): Files.FileType = Files.FileType.Internal
}

class ResourceVfsFileHandle(val id: NamespaceID) : FileHandle(id.toString()) {
  constructor(resource: Resource) : this(resource.location)
  override fun exists(): Boolean = QuantumVoxel.resourceManager.getNodeOrNull(id) != null

  override fun name(): String = id.toString().substringAfterLast('/')
  override fun path(): String = id.toString()
  override fun pathWithoutExtension(): String = id.toString().substringBeforeLast('.')
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

  override fun parent(): FileHandle = ResourceVfsFileHandle(id.parent())
  override fun file(): File = throw GdxRuntimeException("Can't access resource as java.io.File!")
  override fun child(name: String?): FileHandle {
    val childId = id.mapPath { "$it/$name" }
    val node = QuantumVoxel.resourceManager.getNodeOrNull(childId) ?: return ResourceVfsFileHandle(childId)
    if (node.isCategory()) return ResourceVfsFileHandle(childId)
    return ResourceFileHandle(QuantumVoxel.resourceManager[childId])
  }

  override fun isDirectory(): Boolean {
    return QuantumVoxel.resourceManager.getNodeOrNull(id)?.isCategory() ?: throw GdxRuntimeException("Resource not found: $id")
  }
  override fun read(): InputStream {
    QuantumVoxel.resourceManager.getNodeOrNull(id)?.let {
      return@read when (it) {
        is ResourceCategory -> throw GdxRuntimeException("Can't read from category!")
        is Resource -> it.inputStream()
        else -> throw AssertionError("Not a resource or resource category!")
      }
    } ?: throw GdxRuntimeException("Resource not found: $id")
  }

  override fun reader(): Reader {
    QuantumVoxel.resourceManager.getNodeOrNull(id)?.let {
      return@reader when (it) {
        is ResourceCategory -> throw GdxRuntimeException("Can't read from category!")
        is Resource -> it.reader()
        else -> throw AssertionError("Not a resource or resource category!")
      }
    } ?: throw GdxRuntimeException("Resource not found: $id")
  }

  override fun write(append: Boolean): OutputStream {
    throw GdxRuntimeException("Can't write to resource!")
  }

  override fun writer(append: Boolean, charset: String?): Writer {
    throw GdxRuntimeException("Can't write to resource!")
  }

  override fun length(): Long {
    QuantumVoxel.resourceManager.getNodeOrNull(id)?.let {
      return@length when (it) {
        is ResourceCategory -> throw GdxRuntimeException("Can't get length of category!")
        is Resource -> it.length()
        else -> throw AssertionError("Not a resource or resource category!")
      }
    } ?: throw GdxRuntimeException("Resource not found $id")
  }

  override fun type(): Files.FileType = Files.FileType.Internal
}
