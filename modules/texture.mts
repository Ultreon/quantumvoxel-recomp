import QuantumVoxelKt from "@ultreon/quantumjs/game/QuantumVoxelKt.mjs";
import Texture0 from "@ultreon/quantumjs/gdx/graphics/Texture.mjs";

export class Texture {
  handle: number

  constructor(resource: string) {
    this.handle = QuantumVoxelKt.store(new Texture0(resource));
  }

  dispose() {
    QuantumVoxelKt.delete(this.handle)
  }
}
