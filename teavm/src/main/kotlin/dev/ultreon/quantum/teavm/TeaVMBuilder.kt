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
      assetsPath.add(AssetFileHandle("../assets"))
      webappPath = File("build/dist").canonicalPath
      // Register any extra classpath assets here:
      // additionalAssetsClasspathFiles += "dev/ultreon/quantum/asset.extension"
    }

    // Register any classes or packages that require reflection here:
    // TeaReflectionSupplier.addReflectionClass("dev.ultreon.quantum.reflect")

    val tool = TeaBuilder.config(teaBuildConfiguration)
    tool.mainClass = "dev.ultreon.quantum.teavm.TeaVMLauncher"
    tool.optimizationLevel = TeaVMOptimizationLevel.FULL
    tool.setObfuscated(true)
    TeaBuilder.build(tool)
  }
}
