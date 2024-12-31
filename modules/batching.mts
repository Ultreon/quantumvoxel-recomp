import QuantumVoxelKt from "@ultreon/quantumjs/game/QuantumVoxelKt.mjs";
import SpriteBatch0 from "@ultreon/quantumjs/gdx/graphics/g2d/SpriteBatch.mjs";
import ModelBatch0 from "@ultreon/quantumjs/gdx/graphics/g3d/ModelBatch.mjs";
import {Texture} from "./texture.mjs";
import {Camera} from "./utils.mjs";

export class SpriteBatch {
  handle: number

  constructor() {
    this.handle = QuantumVoxelKt.store(new SpriteBatch0());
  }

  begin() {
    (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).begin()
  }

  draw(texture: Texture, x: number, y: number);
  draw(texture: Texture, x: number, y: number, width: number, height: number);
  draw(texture: Texture, x: number, y: number, width: number, height: number, srcX: number, srcY: number, srcWidth: number, srcHeight: number);
  draw(...args: any[]) {
    if (args.length === 3) {
      return (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).draw(
        QuantumVoxelKt.retrieve(args[0].handle),
        args[1],
        args[2]
      )
    }

    if (args.length === 5) {
      return (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).draw(
        QuantumVoxelKt.retrieve(args[0].handle),
        args[1],
        args[2],
        args[3],
        args[4]
      )
    }

    if (args.length === 9) {
      return (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).draw(
        QuantumVoxelKt.retrieve(args[0].handle),
        args[1],
        args[2],
        args[3],
        args[4],
        args[5],
        args[6],
        args[7],
        args[8]
      )
    }

    throw new Error("Invalid number of arguments")
  }

  get isDrawing() {
    return (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).isDrawing()
  }

  enableBlending() {
    (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).enableBlending()
  }

  disableBlending() {
    (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).disableBlending()
  }

  setBlendFunction(srcFactor: number, dstFactor: number) {
    (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).setBlendFunction(srcFactor, dstFactor)
  }

  setBlendFunctionSeparate(srcFactorRGB: number, dstFactorRGB: number, srcFactorAlpha: number, dstFactorAlpha: number) {
    (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).setBlendFunctionSeparate(srcFactorRGB, dstFactorRGB, srcFactorAlpha, dstFactorAlpha)
  }

  end() {
    (QuantumVoxelKt.retrieve(this.handle) as SpriteBatch0).end()
  }

  dispose() {
    QuantumVoxelKt.delete(this.handle)
  }
}

export class ModelBatch {
  private handle: number

  constructor() {
    this.handle = QuantumVoxelKt.store(new ModelBatch0())
  }

  begin(camera: Camera) {
    (QuantumVoxelKt.retrieve(this.handle) as ModelBatch0).begin(QuantumVoxelKt.retrieve(camera.handle))
  }

  end() {
    (QuantumVoxelKt.retrieve(this.handle) as ModelBatch0).end()
  }

  // render(model: Model, environment: Environment = null) {
  //   (QuantumVoxelKt.retrieve(this.handle) as ModelBatch0).render(model.getHandle())
  // }

  dispose() {
    QuantumVoxelKt.delete(this.handle)
  }
}
