package dev.ultreon.quantum.generator

import dev.ultreon.quantum.client.scripting.TypescriptApiManager
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

class TypescriptGenerator {
  fun generate(output: String, module: String) {
    val outputPath = Path(output)
    if (outputPath.notExists()) outputPath.createDirectories()

    val typescriptModule = TypescriptApiManager[module]

    typescriptModule.list().forEach { api ->
      val generator = ApiGenerator(outputPath.resolve("${api.name}.d.ts"), api)
      generator.generate()
    }

//    val commandTgz = when (val osName = System.getProperty("os.name")) {
//      "Mac OS X" -> "tar -tzvf ${module.substringAfterLast("/")}.tgz $module --transform 's,^,package/,'"
//      "Linux" -> "tar -tzvf ${module.substringAfterLast("/")}.tgz $module --transform 's,^,package/,'"
//      "Windows" -> when {
//        // Check for archiving utilities
//        Path("C:/Program Files/7-Zip/7z.exe").exists() -> "C:/Program Files/7-Zip/7z.exe a ${module.substringAfterLast("/")}.tgz $module --transform 's,^,package/,'"
//        Path("C:/Program Files/7-Zip/7za.exe").exists() -> "C:/Program Files/7-Zip/7za.exe a ${module.substringAfterLast("/")}.tgz $module --transform 's,^,package/,'"
//
//        Path("C:/Program Files (x86)/7-Zip/7z.exe").exists() -> "C:/Program Files (x86)/7-Zip/7z.exe a ${module.substringAfterLast("/")}.tgz $module --transform 's,^,package/,'"
//
//        // Fallback to powershell for Windows
//        Path("C:/Program Files (x86)/PowerShell/7/pwsh.exe").exists() -> "C:/Program Files (x86)/PowerShell/7/pwsh.exe -Command \"tar -tzvf ${module.substringAfterLast("/")}.tgz $module --transform 's,^,package/,'\""
//        Path("C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe").exists() -> "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe -Command \"tar -tzvf ${module.substringAfterLast("/")}.tgz $module --transform 's,^,package/,'\""
//        Path("C:\\Program Files\\PowerShell\\6\\pwsh.exe").exists() -> "C:\\Program Files\\PowerShell\\6\\pwsh.exe -Command \"tar -tzvf ${module.substringAfterLast("/")}.tgz $module --transform 's,^,package/,'\""
//        else -> {
//          throw Exception("No archiving utility found")
//        }
//      }
//
//      else -> throw UnsupportedOperationException("Unknown OS: $osName")
//    }
//
//    Runtime.getRuntime().exec(commandTgz).waitFor()
  }
}
