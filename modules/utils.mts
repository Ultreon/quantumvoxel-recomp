import InternalPerspectiveCamera from "@ultreon/quantumjs/gdx/graphics/PerspectiveCamera.mjs";
import QuantumVoxelKt from "@ultreon/quantumjs/game/QuantumVoxelKt.mjs";
import {Matrix4, Vector3} from "./math.mjs";

export interface Camera {
  handle: number

  update(): void
  update(updateFrustum: boolean): void
}

export class PerspectiveCamera implements Camera {
  handle: number

  constructor(props: { fov: number, viewWidth: number, viewHeight: number, near: number, far: number } = { fov: 67, viewWidth: 1, viewHeight: 1, near: 0.01, far: 1000 }) {
    let camera = new InternalPerspectiveCamera(props.fov, props.viewWidth, props.viewHeight);
    camera.near = props.near;
    camera.far = props.far;
    this.handle = QuantumVoxelKt.store(camera);
  }

  get fov() {
    return (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).fieldOfView
  }

  get viewWidth() {
    return (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).viewportWidth
  }

  get viewHeight() {
    return (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).viewportHeight
  }

  get near() {
    return (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).near
  }

  get far() {
    return (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).far
  }

  get position(): Vector3 {
    let position = (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).position;
    return new Vector3({x: position.x, y: position.y, z: position.z})
  }

  set position(position: Vector3) {
    (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).position.set(position.x, position.y, position.z);
  }

  get up(): Vector3 {
    let up = (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).up;
    return new Vector3({x: up.x, y: up.y, z: up.z})
  }

  set up(up: Vector3) {
    (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).up.set(up.x, up.y, up.z);
  }

  get direction(): Vector3 {
    let direction = (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).direction;
    return new Vector3({x: direction.x, y: direction.y, z: direction.z})
  }

  set direction(direction: Vector3) {
    (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).direction.set(direction.x, direction.y, direction.z);
  }

  get projectionMatrix(): Matrix4 {
    let projectionMatrix = (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).projection
    return Matrix4.fromArray(projectionMatrix.val as unknown as number[]);
  }

  get viewMatrix(): Matrix4 {
    let viewMatrix = (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).view
    return Matrix4.fromArray(viewMatrix.val as unknown as number[]);
  }

  get invViewProjectionMatrix(): Matrix4 {
    let viewProjectionMatrix = (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).invProjectionView
    return Matrix4.fromArray(viewProjectionMatrix.val as unknown as number[]);
  }

  set projectionMatrix(projectionMatrix: Matrix4) {
    ((QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).projection as any).val = projectionMatrix.toArray();
  }

  set viewMatrix(viewMatrix: Matrix4) {
    ((QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).view as any).val = viewMatrix.toArray();
  }

  set invViewProjectionMatrix(invViewProjectionMatrix: Matrix4) {
    ((QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).invProjectionView as any).val = invViewProjectionMatrix.toArray();
  }

  update(updateFrustum: boolean = true) {
    (QuantumVoxelKt.retrieve(this.handle) as InternalPerspectiveCamera).update(updateFrustum);
  }

  dispose() {
    QuantumVoxelKt.delete(this.handle);
  }
}
