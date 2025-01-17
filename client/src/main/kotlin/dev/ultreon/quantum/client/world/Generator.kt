package dev.ultreon.quantum.client.world

import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.world.SIZE
import java.util.Random as JavaRandom

const val MAGIC_NUMBER = 0x24D2A6E6;
const val MAGIC_NUMBER_L = 0x24D2A6E648C3A8E9L;

private var seed = MAGIC_NUMBER_L xor java.lang.Double.doubleToLongBits(System.nanoTime().toDouble())

class Generator {
  // Function to create noise layers using the alternative Fast Simplex implementation
  private fun createNoiseLayer(
    seed: Long,
    scale: Double,
    multiplier: Double,
    offset: Double = 0.0,
    x: Double,
    y: Double
  ): Double {
    val noise = FastSimplexNoiseGenerator.newBuilder().setSeed(seed).build()
    return (noise.evaluateNoise(x * scale, y * scale) * multiplier) + offset
  }

  // Generating combined noise using the alternative implementation
  private fun generateCombinedNoise(x: Double, y: Double): Double {
    val x = x / 16
    val y = y / 16
    val noiseLayers = listOf(
      createNoiseLayer(seed, 1 / 2048.0, 1.0, x = x, y = y),
      createNoiseLayer(seed + 1, 1 / 512.0, 64.0, x = x, y = y),
      createNoiseLayer(seed + 2, 1 / 256.0, 64.0, 64.0, x = x, y = y),
      createNoiseLayer(seed + 3, 1 / 256.0, 36.0, x = x, y = y),
      createNoiseLayer(seed + 4, 1 / 1024.0, 12.0, x = x, y = y),
      createNoiseLayer(seed + 5, 1 / 128.0, 8.0, x = x, y = y),
      createNoiseLayer(seed + 6, 1 / 64.0, 12.0, x = x, y = y),
      createNoiseLayer(seed + 7, 1 / 16.0, -14.0, x = x, y = y),
      createNoiseLayer(seed + 8, 1 / 8.0, -16.0, x = x, y = y),
      createNoiseLayer(seed + 9, 1 / 8.0, 16.0, x = x, y = y),
      createNoiseLayer(seed + 10, 1 / 4.0, 8.0, x = x, y = y)
    )

    val combinedNoise = noiseLayers.sum()

    val adjusted = combinedNoise + 16
    val finalResult = if (adjusted < 68) (adjusted - 68) / 1.5 + 68 else adjusted
    return if (finalResult < 32) (finalResult - 32) / 8 + 32 else finalResult
  }

  fun evaluateNoise(x: Int, z: Int): Double {
    return generateCombinedNoise(x.toDouble(), z.toDouble())
  }

  val random = JavaRandom(System.nanoTime() xor MAGIC_NUMBER_L)

  fun generate(chunk: ClientChunk) {
    val start = chunk.start
    val size = SIZE

    for (xo in 0 until size) {
      for (yo in 0 until size) {
        for (zo in 0 until size) {
          val x = (start.x + xo)
          val y = (start.y + yo)
          val z = (start.z + zo)

          var height = evaluateNoise(x, z)
          if (height < -64) height = -60.0

          for (y in start.y until (start.y + size)) {
//          if (height < 56) biomeSetter.setBiome(x, y, z, Biome.OCEAN)
//          if (height < 50) biomeSetter.setBiome(x, y, z, Biome.DEEP_OCEAN)
            if (height < 64.0) {
              when {
                y < 64.0 && y > height -> chunk[x, y, z] = Blocks.water
                y < 64.0 && y > height - 3 -> chunk[x, y, z] = Blocks.sand
                y < 64.0 -> chunk[x, y, z] = Blocks.stone
              }
              continue
            }
            when {
              y < height && height > 64.0 && height < 64.5 -> chunk[x, y, z] = Blocks.sand
              y == height.toInt() -> chunk[x, y, z] = Blocks.grass
              y < height && y > height - 4 -> chunk[x, y, z] = Blocks.soil
              y <= height - 4 -> chunk[x, y, z] = Blocks.stone
            }
          }

          val blockSeed = (x + java.lang.Double.doubleToLongBits(start.x.toDouble())) * MAGIC_NUMBER xor
            (z + java.lang.Double.doubleToLongBits(start.z.toDouble())) * MAGIC_NUMBER xor
            seed - 200
          random.setSeed(blockSeed)

          if (height in 65.5..160.0) {
            if (random.nextInt(2) == 0 && height > 64.5) {
              if (y == height.toInt() + 1) chunk[x, y, z] = Blocks.shortGrass
            } else if (random.nextInt(50) == 0 && height > 64.5) {
//              chunk.set(x, height.toInt() + 1, z, Block.POPPY)
            } else if (random.nextInt(50) == 0 && height > 64.5) {
//              chunk.set(x, height.toInt() + 1, z, Block.DANDELION)
            } else if (random.nextInt(50) == 0 && height > 64.5) {
//              generateTree(unit, x, height.toInt() + 1, z, random)
            }
          } else if (height in 160.0..192.0) {
            for (y in (height - 6).toInt()..height.toInt()) {
              val i = random.nextInt(4)
              when (i) {
                0 -> chunk[x, y, z] = Blocks.stone
                1 -> chunk[x, y, z] = if (y == height.toInt())
                  if (random.nextInt(2) == 0) Blocks.grass
                  else Blocks.soil
                else Blocks.soil

                else -> chunk[x, y, z] = Blocks.cobblestone
//                3 -> chunk.set(x, y, z, Blocks.MOSSY_COBBLESTONE)
//                else -> chunk.set(x, y, z, Block.COBBLESTONE)
              }
            }
            if (height.toInt() in 192..1500) {
//              chunk.set(x, height.toInt() - 1, z, Block.SNOW_BLOCK)
              chunk[x, height.toInt(), z] = Blocks.snowyGrass
//              chunk.set(x, height.toInt() + 1, z, Block.)
            }
          }
        }
      }
    }
  }
}
//class Generator {
//  fun generate(chunk: ClientChunk) {
//    for (int xo = 0; xo < size.x(); xo++) {
//      for (int zo = 0; zo < size.z(); zo++) {
//          int x = (int) (start.x() + xo);
//          int z = (int) (start.z() + zo);
//
//          double height = evaluateNoise(x, z);
//          if (height < -64) height = -60;
//
//          for (int y = (int) start.y(); y < start.y() + size.y(); y++) {
//              if (height < 56) biomeSetter.setBiome(x, y, z, Biome.OCEAN);
//              if (height < 50) biomeSetter.setBiome(x, y, z, Biome.DEEP_OCEAN);
//              if (y == size.y()) {
//                  chunk.set(x, y, z, Block.BEDROCK);
//                  continue;
//              }
//              if (height < 64.0) {
//                  if (y < 64.0 && y > height) {
//                      chunk.set(x, y, z, Block.WATER);
//                  } else if (y < 64.0 && y > height - 3) {
//                      chunk.set(x, y, z, Block.SAND);
//                  } else if (y < 64.0) {
//                      chunk.set(x, y, z, Block.STONE);
//                  }
//                  continue;
//              }
//              if (y < height && height > 64.0 && height < 64.5) {
//                  chunk.set(x, y, z, Block.SAND);
//                  continue;
//              }
//              if (y == (int) height) {
//                  chunk.set(x, y, z, Block.GRASS_BLOCK);
//              } else if (y < height && y > height - 4) {
//                  chunk.set(x, y, z, Block.DIRT);
//              } else if (y <= height - 4) {
//                  chunk.set(x, y, z, Block.STONE);
//              }
//          }
//
//          // Generate decorations
//          long blockSeed = (x + Double.doubleToLongBits(start.x())) * QuantServer.MAGIC_NUMBER ^ (z + Double.doubleToLongBits(start.z())) * QuantServer.MAGIC_NUMBER ^ server.getWorldSeed();
//          random.setSeed(blockSeed);
//
//          if (height < 160) {
//              if (random.nextInt(2) == 0 && height > 64.5) {
//                  chunk.set(x, (int) height + 1, z, Block.SHORT_GRASS);
//              } else if (random.nextInt(50) == 0 && height > 64.5) {
//                  chunk.set(x, (int) height + 1, z, Block.POPPY);
//              } else if (random.nextInt(50) == 0 && height > 64.5) {
//                  chunk.set(x, (int) height + 1, z, Block.DANDELION);
//              } else if (random.nextInt(50) == 0 && height > 64.5) {
//                  generateTree(unit, x, (int) height + 1, z, random);
//              }
//          } else {
//              for (int y = (int) (height - 6); y <= height; y++) {
//                  int i = random.nextInt(6);
//                  if (i == 0) chunk.set(x, y, z, Block.STONE);
//                  else if (i == 1)
//                      chunk.set(x, y, z, y == (int) height ? (random.nextInt(2) == 0 ? Block.GRASS_BLOCK : Block.COARSE_DIRT) : Block.DIRT);
//                  else if (i == 2) chunk.set(x, y, z, Block.ANDESITE);
//                  else if (i == 3) chunk.set(x, y, z, Block.MOSSY_COBBLESTONE);
//                  else chunk.set(x, y, z, Block.COBBLESTONE);
//              }
//
//              if (height > 192) {
//                  chunk.set(x, (int) height - 1, z, Block.SNOW_BLOCK);
//                  chunk.set(x, (int) height, z, Block.SNOW_BLOCK);
//                  chunk.set(x, (int) height + 1, z, Block.SNOW);
//              }
//          }
//      }
//    }
//  }
//}
