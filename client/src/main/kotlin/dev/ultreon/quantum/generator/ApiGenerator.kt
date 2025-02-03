package dev.ultreon.quantum.generator

import dev.ultreon.quantum.client.scripting.TypescriptApi
import java.nio.file.Path
import kotlin.io.path.createParentDirectories
import kotlin.io.path.notExists
import kotlin.io.path.writeText

class ApiGenerator(val path: Path, val api: TypescriptApi) {
  fun generate() {
    if (path.parent.notExists()) {
      path.createParentDirectories()
    }

    val content = api.compile()
    path.writeText(content)
  }
}
