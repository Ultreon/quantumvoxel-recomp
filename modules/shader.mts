import QuantumVoxelKt from "@ultreon/quantumjs/game/QuantumVoxelKt.mjs";
import ShaderProgram0 from "@ultreon/quantumjs/gdx/graphics/glutils/ShaderProgram.mjs";
import {Matrix4, Matrix3} from "./math.mjs";
import BufferUtils from "@ultreon/quantumjs/gdx/utils/BufferUtils.mjs";

export class ShaderProgram {
  handle: number

  constructor(vertexShader: string, fragmentShader: string) {
    this.handle = QuantumVoxelKt.store(new ShaderProgram0(vertexShader, fragmentShader));
  }

  begin() {
    (QuantumVoxelKt.retrieve(this.handle) as ShaderProgram0).begin()
  }

  setUniformI(name: string, value: number) {
    (QuantumVoxelKt.retrieve(this.handle) as ShaderProgram0).setUniformi(name, value)
  }

  setUniformF(name: string, value: number) {
    (QuantumVoxelKt.retrieve(this.handle) as ShaderProgram0).setUniformf(name, value)
  }

  setUniformMatrix(name: string, matrix: Matrix4) {
    (QuantumVoxelKt.retrieve(this.handle) as ShaderProgram0).setUniformMatrix4fv(name, matrix.m, /*offset*/ 0, /*length*/ 16)
  }

  setUniformMatrixArray(name: string, matrix: Matrix3, transpose: boolean = false) {
    let floatBuffer = BufferUtils.newFloatBuffer(9);
    floatBuffer.put(matrix.m);
    floatBuffer.flip();
    (QuantumVoxelKt.retrieve(this.handle) as ShaderProgram0).setUniformMatrix3fv(name, floatBuffer, /*count*/ 1, /*transpose*/ transpose)
    floatBuffer.clear()
  }

  end() {
    (QuantumVoxelKt.retrieve(this.handle) as ShaderProgram0).end()
  }

  dispose() {
    QuantumVoxelKt.delete(this.handle)
  }
}
