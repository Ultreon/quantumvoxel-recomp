package dev.ultreon.quantum.client.world

import de.articdive.jnoise.core.api.functions.Combiner
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex2DVariant
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex3DVariant
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex4DVariant
import de.articdive.jnoise.pipeline.JNoise
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.Blocks
import dev.ultreon.quantum.world.SIZE
import kotlin.random.Random
import java.util.Random as JavaRandom

const val MAGIC_NUMBER = 0x24D2A6E6;
const val MAGIC_NUMBER_L = 0x24D2A6E648C3A8E9L;

private var seed = MAGIC_NUMBER_L xor java.lang.Double.doubleToLongBits(System.nanoTime().toDouble())

class Generator {
  private val noise = JNoise.newBuilder()
    .fastSimplex(seed, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 2048.0)
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 512.0)
        .addModifier { result -> result * 64 }
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 256.0)
        .addModifier { result -> result * 64 + 64 }
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 256.0)
        .addModifier { result -> result * 36 }
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 1024.0)
        .addModifier { result -> result * 12 }
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 128.0)
        .addModifier { result -> result * 8 }
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 64.0)
        .addModifier { result -> result * 12 }
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 16.0)
        .addModifier { result -> -result * 14 }
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 8.0)
        .addModifier { result -> -result * 16 }
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 8.0)
        .addModifier { result -> result * 16 }
        .build(),
      { a, b -> a + b }
    )
    .combine(
      JNoise.newBuilder()
        .fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
        .scale(1 / 4.0)
        .addModifier { result -> result * 8 }
        .build(),
      { a, b -> a + b }
    )
    .addModifier { result -> if ((result + 16) < 68) ((result + 16) - 68) / 1.5 + 68 else result + 16 }
    .addModifier { result -> if (result < 32) (result - 32) / 8 + 32 else result }
    .scale(1 / 16.0)
    .build()
//    noise = JNoise.newBuilder()
//                .fastSimplex(seed, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                .combine(
//                        // Smaller island generation
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 2048.0)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 512.0)
//                                .addModifier(result -> result * 64)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 256.0)
//                                .addModifier(result -> result * 64 + 64)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        // Smaller island generation
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 256.0)
//                                .addModifier(result -> result * 36)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        // Smaller island generation
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 1024.0)
//                                .addModifier(result -> result * 12)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 128.0)
//                                .addModifier(result -> result * 8)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 64.0)
//                                .addModifier(result -> result * 12)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 16.0)
//                                .addModifier(result -> -result * 14)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 8.0)
//                                .addModifier(result -> -result * 16)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 8.0)
//                                .addModifier(result -> result * 16)
//                                .build(),
//                        { a, b -> a + b }
//                ).combine(
//                        JNoise.newBuilder().fastSimplex(seed++, Simplex2DVariant.CLASSIC, Simplex3DVariant.IMPROVE_XZ, Simplex4DVariant.IMRPOVE_XYZ)
//                                .scale(1 / 4.0)
//                                .addModifier(result -> result * 8)
//                                .build(),
//                        { a, b -> a + b }
//                )
//                .addModifier(result -> (result + 16) < 68 ? ((result + 16) - 68) / 1.5 + 68 : result + 16)
//                .addModifier(result -> result < 32 ? (result - 32) / 8 + 32 : result)
//                .scale(1 / 16.0).build();
//}

  fun evaluateNoise(x: Int, z: Int): Double {
    return this.noise.evaluateNoise(x.toDouble(), z.toDouble())
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

          if (height < 160) {
            if (random.nextInt(2) == 0 && height > 64.5) {
//              chunk.set(x, height.toInt() + 1, z, Blocks.SHORT_GRASS)
            } else if (random.nextInt(50) == 0 && height > 64.5) {
//              chunk.set(x, height.toInt() + 1, z, Block.POPPY)
            } else if (random.nextInt(50) == 0 && height > 64.5) {
//              chunk.set(x, height.toInt() + 1, z, Block.DANDELION)
            } else if (random.nextInt(50) == 0 && height > 64.5) {
//              generateTree(unit, x, height.toInt() + 1, z, random)
            }
          } else {
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
            if (height > 192) {
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
