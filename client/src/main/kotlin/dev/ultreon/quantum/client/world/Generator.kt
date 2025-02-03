package dev.ultreon.quantum.client.world

import com.badlogic.gdx.math.GridPoint3
import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator
import de.articdive.jnoise.generators.noisegen.perlin.PerlinNoiseGenerator
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.util.id
import dev.ultreon.quantum.world.SIZE
import kotlin.random.Random
import java.util.Random as JavaRandom

const val MAGIC_NUMBER = 0x24D2A6E6
const val MAGIC_NUMBER_L = 0x24D2A6E648C3A8E9L

class Generator(val seed: Long = Random(java.lang.Double.doubleToLongBits(System.nanoTime().toDouble())).nextLong()) {
  // Function to create noise layers using the alternative Fast Simplex implementation
  private fun createNoiseLayer(
    seed: Long,
    scale: Double,
    multiplier: Double,
    offset: Double = 0.0,
  ): (Double, Double) -> Double {
    val r = Random(seed)
    val noise = FastSimplexNoiseGenerator.newBuilder().setSeed(seed).build()
    val offX = r.nextInt(-1000, 1000)
    val offY = r.nextInt(-1000, 1000)
    return { x, y -> (noise.evaluateNoise(x * scale + offX, y * scale + offY) * multiplier) + offset }
  }

  private fun createTerrainNoise(seed: Long, wildness: (Double, Double) -> Double): (Double, Double) -> Double {
    val noiseLayers = listOf(
      createNoiseLayer(seed, 1 / 2048.0, 1.0),
      createNoiseLayer(seed + 1, 1 / 512.0, 64.0),
      createNoiseLayer(seed + 2, 1 / 256.0, 64.0, 64.0),
      createNoiseLayer(seed + 3, 1 / 256.0, 36.0),
      createNoiseLayer(seed + 4, 1 / 1024.0, 12.0),
      createNoiseLayer(seed + 5, 1 / 128.0, 8.0),
      createNoiseLayer(seed + 6, 1 / 64.0, 12.0),
      createNoiseLayer(seed + 7, 1 / 16.0, -14.0),
      createNoiseLayer(seed + 8, 1 / 8.0, -16.0),
      createNoiseLayer(seed + 9, 1 / 8.0, 16.0),
      createNoiseLayer(seed + 10, 1 / 4.0, 8.0)
    )

    return { x, y ->
      val combinedNoise = noiseLayers.sumOf { it(x, y) } * wildness(x, y)

      val adjusted = combinedNoise + 16
      val finalResult = if (adjusted < 68) (adjusted - 68) / 1.5 + 68 else adjusted

      (if (finalResult < 32) (finalResult - 32) / 8 + 32 else finalResult)
    }
  }

  private fun createWildnessNoise(seed: Long): (Double, Double) -> Double {
    val noiseLayers = listOf(
      createNoiseLayer(seed - 6, 1 / 64.0, 0.2),
      createNoiseLayer(seed - 9, 1 / 32.0, 0.5),
      createNoiseLayer(seed - 7, 1 / 56.0, 0.3),
    )

    return { x, y -> (noiseLayers.sumOf { it(x, y) } + 3) / 4 }
  }

  // Function to create noise layers using the alternative Fast Simplex implementation
  private fun createBiomeNoise(seed: Long): (Double, Double) -> Double {
    val noise = PerlinNoiseGenerator.newBuilder().setSeed(seed).build()
    val r = Random(seed)
    val offX = r.nextInt(-1000, 1000)
    val offY = r.nextInt(-1000, 1000)

    return { x, y -> noise.evaluateNoise(x + offX, y + offY) }
  }

  val temperatureNoise = createBiomeNoise(seed - 1)
  val humidityNoise = createBiomeNoise(seed - 2)
  val wildnessNoise = createWildnessNoise(seed - 3)
  val terrainNoise = createTerrainNoise(seed, wildnessNoise)

  // Generating combined noise using the alternative implementation
  private fun generateCombinedNoise(x: Double, y: Double): Double {
    val sx = x / 16
    val sy = y / 16

    return terrainNoise(sx, sy)
  }

  fun evaluateNoise(x: Int, z: Int): Double {
    return generateCombinedNoise(x.toDouble(), z.toDouble())
  }

  val random = JavaRandom(System.nanoTime() xor MAGIC_NUMBER_L)

  fun generate(chunk: ClientChunk) {
    generateAsync(chunk)
  }

  fun generateAsync(chunk: ClientChunk) {
    val start = chunk.start
    val size = SIZE

    for (xo in 0 until size) {
      for (zo in 0 until size) {
        if (temperatureNoise(xo.toDouble(), zo.toDouble()) > 0.5) {
          Blocks.sandstone?.let { generateDesert(size, start, chunk, it, xo, zo) }
            ?: generatePlains(size, start, chunk, xo, zo)
        } else if (temperatureNoise(xo.toDouble(), zo.toDouble()) < -0.5) {
          Blocks.snowyGrass?.let { generateTundra(size, start, chunk, it, xo, zo) }
            ?: generatePlains(size, start, chunk, xo, zo)
        } else {
          generatePlains(size, start, chunk, xo, zo)
        }
      }
    }
  }

  private fun generatePlains(
    size: Int,
    start: GridPoint3,
    chunk: ClientChunk,
    xo: Int,
    zo: Int
  ) {
    for (yo in 0 until size) {
      val x = (start.x + xo)
      val y = (start.y + yo)
      val z = (start.z + zo)
      var height = evaluateNoise(x, z)
      if (height < -64) height = -60.0

      for (dy in start.y until (start.y + size)) {
//          if (height < 56) biomeSetter.setBiome(x, dy, z, Biome.OCEAN)
//          if (height < 50) biomeSetter.setBiome(x, dy, z, Biome.DEEP_OCEAN)
        if (height < 64.0) {
          when {
            dy < 64.0 && dy > height -> chunk[x, dy, z] = Blocks.water
            dy < 64.0 && dy > height - 3 -> chunk[x, dy, z] = Blocks.sand
            dy < 64.0 -> chunk[x, dy, z] = Blocks.stone
          }
          continue
        }
        when {
          dy < height && height > 64.0 && height < 64.5 -> chunk[x, dy, z] = Blocks.sand
          dy == height.toInt() -> chunk[x, dy, z] = Blocks.grass
          dy < height && dy > height - 4 -> chunk[x, dy, z] = Blocks.soil
          dy <= height - 4 -> chunk[x, dy, z] = Blocks.stone
        }
      }

      val blockSeed = (x + java.lang.Double.doubleToLongBits(start.x.toDouble())) * MAGIC_NUMBER xor
        (z + java.lang.Double.doubleToLongBits(start.z.toDouble())) * MAGIC_NUMBER xor
        seed - 200
      random.setSeed(blockSeed)

      if (height in 65.5..160.0) {
        when {
          random.nextInt(2) == 0 && height > 64.5 ->
            if (y == height.toInt() + 1) {
              val shortGrass = Blocks.shortGrass
              if (shortGrass != null) {
                chunk[x, y, z] = shortGrass
              }
            }

          random.nextInt(50) == 0 && height > 64.5 && y == height.toInt() + 1 -> {
            Blocks[id(path = "clover_patch")]?.let { chunk[x, height.toInt() + 1, z] = it }
          }

          random.nextInt(50) == 0 && height > 64.5 -> {
            Blocks[id(path = "dandelion")]?.let { chunk[x, height.toInt() + 1, z] = it }
          }

          random.nextInt(50) == 0 && height > 64.5 -> {
//            generateTree(unit, x, height.toInt() + 1, z, random)
          }
        }
      } else if (height >= 160.0) {
        if (y > height - 6 && y < height) {
          val i = random.nextInt(4)
          when (i) {
            0 -> chunk[x, y, z] = Blocks.stone
            1 -> chunk[x, y, z] = if (y == height.toInt())
              if (random.nextInt(2) == 0) Blocks.grass
              else Blocks.soil
            else Blocks.soil

            else -> {
              val cobblestone = Blocks.cobblestone
              if (cobblestone != null) {
                chunk[x, y, z] = cobblestone
              } else {
                chunk[x, y, z] = Blocks.stone
              }
            }
//            3 -> chunk.set(x, dy, z, Blocks.mossyCobblestone)
//            else -> chunk.set(x, dy, z, Block.cobblestone)
          }
        }
        val snowBlock = Blocks.snowBlock
        if (height.toInt() >= 192 && y.toDouble() == height) {
          if (snowBlock != null) {
            chunk[x, y, z] = snowBlock
          }
        }
      }
    }
  }

  private fun generateDesert(
    size: Int,
    start: GridPoint3,
    chunk: ClientChunk,
    sandstone: Block,
    xo: Int,
    zo: Int
  ) {
    for (yo in 0 until size) {
      val x = (start.x + xo)
      val y = (start.y + yo)
      val z = (start.z + zo)

      var height = evaluateNoise(x, z)
      if (height < -64) height = -60.0

      for (dy in start.y until (start.y + size)) {
//        if (height < 56) biomeSetter.setBiome(x, dy, z, Biome.OCEAN)
//        if (height < 45) biomeSetter.setBiome(x, dy, z, Biome.DEEP_OCEAN)
        if (height < 64.0) {
          when {
            dy < 64.0 && dy > height -> chunk[x, dy, z] = Blocks.water
            dy < 64.0 && dy > height - 3 -> chunk[x, dy, z] = Blocks.sand
            dy < 64.0 -> chunk[x, dy, z] = Blocks.stone
          }
          continue
        }
        when {
          dy == height.toInt() -> chunk[x, dy, z] = Blocks.sand
          dy < height && dy > height - 6 -> chunk[x, dy, z] = sandstone
          dy <= height - 6 -> chunk[x, dy, z] = Blocks.stone
        }
      }

      val blockSeed = (x + java.lang.Double.doubleToLongBits(start.x.toDouble())) * MAGIC_NUMBER xor
        (z + java.lang.Double.doubleToLongBits(start.z.toDouble())) * MAGIC_NUMBER xor
        seed - 200
      random.setSeed(blockSeed)

      if (height in 65.5..160.0) {
        when {
          // TODO: Generating cacti
        }
      } else if (height >= 160.0) {
        if (y > height - 6 && y < height) {
          val i = random.nextInt(4)
          when (i) {
            0 -> chunk[x, y, z] = Blocks.stone
            1 -> chunk[x, y, z] = if (y == height.toInt())
              if (random.nextInt(2) == 0) Blocks.grass
              else Blocks.soil
            else Blocks.soil

            else -> {
              val cobblestone = Blocks.cobblestone
              if (cobblestone != null) {
                chunk[x, y, z] = cobblestone
              } else {
                chunk[x, y, z] = Blocks.stone
              }
            }
//                3 -> chunk.set(x, dy, z, Blocks.mossyCobblestone)
//                else -> chunk.set(x, dy, z, Block.cobblestone)
          }
        }
        val snowBlock = Blocks.snowBlock
        if (height.toInt() >= 192 && y.toDouble() == height) {
          if (snowBlock != null) {
            chunk[x, y, z] = snowBlock
          }
        }
      }
    }
  }

  private fun generateTundra(
    size: Int,
    start: GridPoint3,
    chunk: ClientChunk,
    snowyGrass: Block,
    xo: Int,
    zo: Int
  ) {
    for (yo in 0 until size) {
      val x = (start.x + xo)
      val y = (start.y + yo)
      val z = (start.z + zo)

      var height = evaluateNoise(x, z)
      if (height < -64) height = -60.0

      for (dy in start.y until (start.y + size)) {
//          if (height < 56) biomeSetter.setBiome(x, dy, z, Biome.OCEAN)
//          if (height < 50) biomeSetter.setBiome(x, dy, z, Biome.DEEP_OCEAN)
        if (height < 64.0) {
          when {
            dy < 64.0 && dy > height -> chunk[x, dy, z] = Blocks.water
            dy < 64.0 && dy > height - 3 -> chunk[x, dy, z] = Blocks.sand
            dy < 64.0 -> chunk[x, dy, z] = Blocks.stone
          }
          continue
        }
        when {
          dy < height && height > 64.0 && height < 64.5 -> chunk[x, dy, z] = Blocks.sand
          dy == height.toInt() -> chunk[x, dy, z] = snowyGrass
          dy < height && dy > height - 4 -> chunk[x, dy, z] = Blocks.soil
          dy <= height - 4 -> chunk[x, dy, z] = Blocks.stone
        }
      }

      val blockSeed = (x + java.lang.Double.doubleToLongBits(start.x.toDouble())) * MAGIC_NUMBER xor
        (z + java.lang.Double.doubleToLongBits(start.z.toDouble())) * MAGIC_NUMBER xor
        seed - 200
      random.setSeed(blockSeed)

      if (height in 65.5..160.0) {
        when {
          random.nextInt(2) == 0 && height > 64.5 ->
            if (y == height.toInt() + 1) {
              val shortGrass = Blocks.shortGrass
              if (shortGrass != null) {
                chunk[x, y, z] = shortGrass
              }
            }

          random.nextInt(50) == 0 && height > 64.5 -> {
//                chunk.set(x, height.toInt() + 1, z, Block.POPPY)
          }

          random.nextInt(50) == 0 && height > 64.5 -> {
//                chunk.set(x, height.toInt() + 1, z, Block.DANDELION)
          }

          random.nextInt(50) == 0 && height > 64.5 -> {
//                generateTree(unit, x, height.toInt() + 1, z, random)
          }
        }
      } else if (height >= 160.0) {
        if (y > height - 6 && y < height) {
          val i = random.nextInt(4)
          when (i) {
            0 -> chunk[x, y, z] = Blocks.stone
            1 -> chunk[x, y, z] = if (y == height.toInt())
              if (random.nextInt(2) == 0) Blocks.grass
              else Blocks.soil
            else Blocks.soil

            else -> {
              val cobblestone = Blocks.cobblestone
              if (cobblestone != null) {
                chunk[x, y, z] = cobblestone
              } else {
                chunk[x, y, z] = Blocks.stone
              }
            }
//            3 -> chunk.set(x, dy, z, Blocks.mossyCobblestone)
//            else -> chunk.set(x, dy, z, Block.cobblestone)
          }
        }
        val snowBlock = Blocks.snowBlock
        if (height.toInt() >= 192 && y.toDouble() == height) {
          if (snowBlock != null) {
            chunk[x, y, z] = snowBlock
          }
        }
      }
    }
  }
}
