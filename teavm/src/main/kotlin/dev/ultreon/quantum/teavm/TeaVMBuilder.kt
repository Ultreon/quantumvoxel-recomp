package dev.ultreon.quantum.teavm

import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder
import com.github.xpenatan.gdx.backends.teavm.gen.SkipClass
import org.teavm.backend.javascript.JSModuleType
import org.teavm.tooling.TeaVMTargetType
import org.teavm.tooling.TeaVMTool
import org.teavm.vm.TeaVMOptimizationLevel
import java.io.File

/** Builds the TeaVM/HTML application. */
@SkipClass
@Deprecated("TeaVM backend is deprecated")
object TeaVMBuilder {
  @JvmStatic
  fun main(arguments: Array<String>) {
    val teaBuildConfiguration = TeaBuildConfiguration().apply {
      assetsPath.add(AssetFileHandle("build/assets"))
      assetsPath.add(AssetFileHandle("src/main/res"))
      webappPath = File("build/dist").canonicalPath
      // Register any extra classpath assets here:
      // additionalAssetsClasspathFiles += "dev/ultreon/quantum/asset.extension"

      this.showLoadingLogo = true
      this.logoPath = "logo.png"
    }

    // Register any classes or packages that require reflection here:
    // TeaReflectionSupplier.addReflectionClass("dev.ultreon.quantum.reflect")

    val tool: TeaVMTool = TeaBuilder.config(teaBuildConfiguration)
    tool.mainClass = "dev.ultreon.quantum.teavm.TeaVMLauncher"
    tool.optimizationLevel = TeaVMOptimizationLevel.FULL
    tool.targetType = TeaVMTargetType.JAVASCRIPT
    tool.setObfuscated(true)
    TeaBuilder.build(tool)
  }
}
