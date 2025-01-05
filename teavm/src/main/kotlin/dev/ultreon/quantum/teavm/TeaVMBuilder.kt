package dev.ultreon.quantum.teavm

import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder
import com.github.xpenatan.gdx.backends.teavm.gen.SkipClass
import org.teavm.vm.TeaVMOptimizationLevel
import java.io.File

/** Builds the TeaVM/HTML application. */
@SkipClass
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

    val tool = TeaBuilder.config(teaBuildConfiguration)
    tool.mainClass = "dev.ultreon.quantum.teavm.TeaVMLauncher"
    tool.optimizationLevel = TeaVMOptimizationLevel.FULL
    tool.setObfuscated(false)
    TeaBuilder.build(tool)
  }
}
