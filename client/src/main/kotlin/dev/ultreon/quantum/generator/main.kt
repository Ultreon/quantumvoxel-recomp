package dev.ultreon.quantum.generator

import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.client.TypescriptApiManager

fun main(args: Array<String>) {
  QuantumVoxel.registerApis(TypescriptApiManager.register("@ultreon/quantum-voxel"))

  TypescriptGenerator().generate(args[0], args[1])
}
