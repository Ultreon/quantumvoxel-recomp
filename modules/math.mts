export class Vector2 {
  x: number;
  y: number;
  constructor(x: number, y: number) {
    this.x = x;
    this.y = y;
  }

  set(x: number, y: number): void {
    this.x = x;
    this.y = y;
  }

  add(x: number, y: number): void {
    this.x += x;
    this.y += y;
  }

  subtract(x: number, y: number): void {
    this.x -= x;
    this.y -= y;
  }

  multiply(x: number, y: number): void {
    this.x *= x;
    this.y *= y;
  }

  divide(x: number, y: number): void {
    this.x /= x;
    this.y /= y;
  }

  length(): number {
    return Math.sqrt(this.x * this.x + this.y * this.y);
  }

  normalize(): void {
    const length = this.length();
    this.x /= length;
    this.y /= length;
  }

  dot(other: Vector2): number {
    return this.x * other.x + this.y * other.y;
  }

  cross(other: Vector2): number {
    return this.x * other.y - this.y * other.x;
  }

  equals(other: Vector2): boolean {
    return this.x === other.x && this.y === other.y;
  }

  distance(other: Vector2): number {
    return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
  }

  angle(other: Vector2): number {
    return Math.atan2(this.y - other.y, this.x - other.x);
  }

  lerp(other: Vector2, t: number): Vector2 {
    return new Vector2(this.x + (other.x - this.x) * t, this.y + (other.y - this.y) * t);
  }

  clone(): Vector2 {
    return new Vector2(this.x, this.y);
  }

  toString(): string {
    return `(${this.x}, ${this.y})`;
  }

  toArray(): number[] {
    return [this.x, this.y];
  }

  static fromArray(array: number[]): Vector2 {
    return new Vector2(array[0], array[1]);
  }

  static zero(): Vector2 {
    return new Vector2(0, 0);
  }

  static one(): Vector2 {
    return new Vector2(1, 1);
  }

  static up(): Vector2 {
    return new Vector2(0, 1);
  }

  static down(): Vector2 {
    return new Vector2(0, -1);
  }

  static left(): Vector2 {
    return new Vector2(-1, 0);
  }

  static right(): Vector2 {
    return new Vector2(1, 0);
  }
}

export class Vector3 {
  x: number;
  y: number;
  z: number;

  constructor(props: { x: number, y: number, z: number } = { x: 0, y: 0, z: 0 }) {
    this.set(props.x, props.y, props.z);
  }
  set(x: number, y: number, z: number): this {
    this.x = x;
    this.y = y;
    this.z = z;

    return this;
  }

  setVec(vector: Vector3): this {
    this.x = vector.x;
    this.y = vector.y;
    this.z = vector.z;

    return this;
  }

  add(x: number, y: number, z: number): this {
    this.x += x;
    this.y += y;
    this.z += z;

    return this;
  }

  addVec(vector: Vector3): this {
    this.x += vector.x;
    this.y += vector.y;
    this.z += vector.z;

    return this;
  }

  subtract(x: number, y: number, z: number): this {
    this.x -= x;
    this.y -= y;
    this.z -= z;

    return this;
  }
  subtractVec(vector: Vector3): this {
    this.x -= vector.x;
    this.y -= vector.y;
    this.z -= vector.z;

    return this;
  }

  multiply(x: number, y: number, z: number): this {
    this.x *= x;
    this.y *= y;
    this.z *= z;

    return this;
  }

  multiplyVec(vector: Vector3): this {
    this.x *= vector.x;
    this.y *= vector.y;
    this.z *= vector.z;

    return this;
  }

  divide(x: number, y: number, z: number): this {
    this.x /= x;
    this.y /= y;
    this.z /= z;

    return this;
  }

  divideVec(vector: Vector3): this {
    this.x /= vector.x;
    this.y /= vector.y;
    this.z /= vector.z;

    return this;
  }

  modulo(x: number, y: number, z: number): this {
    this.x %= x;
    this.y %= y;
    this.z %= z;

    return this;
  }

  moduloVec(vector: Vector3): this {
    this.x %= vector.x;
    this.y %= vector.y;
    this.z %= vector.z;

    return this;
  }

  clamp(min: number, max: number): this {
    this.x = Math.min(Math.max(this.x, min), max);
    this.y = Math.min(Math.max(this.y, min), max);
    this.z = Math.min(Math.max(this.z, min), max);

    return this;
  }

  clampVec(min: Vector3, max: Vector3): this {
    this.x = Math.min(Math.max(this.x, min.x), max.x);
    this.y = Math.min(Math.max(this.y, min.y), max.y);
    this.z = Math.min(Math.max(this.z, min.z), max.z);

    return this;
  }

  length(): number {
    return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
  }

  normalize(): this {
    const length = this.length();
    this.x /= length;
    this.y /= length;
    this.z /= length;

    return this;
  }

  dot(other: Vector3): number {
    return this.x * other.x + this.y * other.y + this.z * other.z;
  }

  cross(other: Vector3): Vector3 {
    const x = this.y * other.z - this.z * other.y;
    const y = this.z * other.x - this.x * other.z;
    const z = this.x * other.y - this.y * other.x;
    return new Vector3({ x, y, z });
  }

  equals(other: Vector3): boolean {
    return this.x === other.x && this.y === other.y && this.z === other.z;
  }

  distance(other: Vector3): number {
    return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y) + (this.z - other.z) * (this.z - other.z));
  }

  angle(other: Vector3): number {
    return Math.atan2(this.y - other.y, this.x - other.x);
  }

  lerp(other: Vector3, t: number): Vector3 {
    const x = this.x + (other.x - this.x) * t;
    const y = this.y + (other.y - this.y) * t;
    const z = this.z + (other.z - this.z) * t;
    return new Vector3({ x, y, z });
  }

  clone(): Vector3 {
    return new Vector3({ x: this.x, y: this.y, z: this.z });
  }

  toString(): string {
    return `(${this.x}, ${this.y}, ${this.z})`;
  }

  toArray(): number[] {
    return [this.x, this.y, this.z];
  }

  static fromArray(array: number[]): Vector3 {
    return new Vector3({ x: array[0], y: array[1], z: array[2] });
  }

  static zero(): Vector3 {
    return new Vector3({ x: 0, y: 0, z: 0 });
  }

  static one(): Vector3 {
    return new Vector3({ x: 1, y: 1, z: 1 });
  }

  static up(): Vector3 {
    return new Vector3({ x: 0, y: 1, z: 0 });
  }

  static down(): Vector3 {
    return new Vector3({ x: 0, y: -1, z: 0 });
  }

  static left(): Vector3 {
    return new Vector3({ x: -1, y: 0, z: 0 });
  }

  static right(): Vector3 {
    return new Vector3({ x: 1, y: 0, z: 0 });
  }

  static forward(): Vector3 {
    return new Vector3({ x: 0, y: 0, z: 1 });
  }

  static back(): Vector3 {
    return new Vector3({ x: 0, y: 0, z: -1 });
  }
}

export class Matrix4 {
  m: number[][] = [
    [1, 0, 0, 0],
    [0, 1, 0, 0],
    [0, 0, 1, 0],
    [0, 0, 0, 1]
  ]

  constructor(props: { m: number[][] } = { m: [[1, 0, 0, 0], [0, 1, 0, 0], [0, 0, 1, 0], [0, 0, 0, 1]] }) {
    this.m = props.m;
  }

  multiply(matrix: Matrix4, result: Matrix4 = new Matrix4()): Matrix4 {
    for (let i = 0; i < 4; i++) {
      for (let j = 0; j < 4; j++) {
        result.m[i][j] = 0;
        for (let k = 0; k < 4; k++) {
          result.m[i][j] += this.m[i][k] * matrix.m[k][j];
        }
      }
    }
    return result;
  }

  subtract(matrix: Matrix4, result: Matrix4 = new Matrix4()): Matrix4 {
    for (let i = 0; i < 4; i++) {
      for (let j = 0; j < 4; j++) {
        result.m[i][j] = this.m[i][j] - matrix.m[i][j];
      }
    }
    return result;
  }

  normalize(result: Matrix4 = new Matrix4()): Matrix4 {
    for (let i = 0; i < 4; i++) {
      for (let j = 0; j < 4; j++) {
        result.m[i][j] = this.m[i][j] / this.length();
      }
    }

    return result;
  }

  length(): number {
    let length = 0;
    for (let i = 0; i < 4; i++) {
      for (let j = 0; j < 4; j++) {
        length += this.m[i][j] * this.m[i][j];
      }
    }
    return Math.sqrt(length);
  }

  toString(): string {
    return `(${this.m[0]}, ${this.m[1]}, ${this.m[2]}, ${this.m[3]})`;
  }

  toArray(): number[] {
    return this.m.flat();
  }

  static fromArray(array: number[]): Matrix4 {
    return new Matrix4({ m: [array.slice(0, 4), array.slice(4, 8), array.slice(8, 12), array.slice(12, 16)] });
  }

  static identity(): Matrix4 {
    return new Matrix4();
  }

  static translation(x: number, y: number, z: number): Matrix4 {
    return new Matrix4({ m: [[1, 0, 0, x], [0, 1, 0, y], [0, 0, 1, z], [0, 0, 0, 1]] });
  }

  static scaling(x: number, y: number, z: number): Matrix4 {
    return new Matrix4({ m: [[x, 0, 0, 0], [0, y, 0, 0], [0, 0, z, 0], [0, 0, 0, 1]] });
  }

  static rotation(x: number, y: number, z: number): Matrix4 {
    const matrix = new Matrix4();

    matrix.m[0][0] = Math.cos(x);
    matrix.m[0][1] = -Math.sin(x);
    matrix.m[1][0] = Math.sin(x);
    matrix.m[1][1] = Math.cos(x);

    matrix.m[2][2] = Math.cos(y);
    matrix.m[2][1] = -Math.sin(y);
    matrix.m[1][2] = Math.sin(y);
    matrix.m[1][1] = Math.cos(y);

    matrix.m[0][2] = Math.cos(z);
    matrix.m[0][3] = -Math.sin(z);
    matrix.m[3][2] = Math.sin(z);
    matrix.m[3][3] = Math.cos(z);

    return matrix;
  }

  static perspective(fov: number, aspect: number, near: number, far: number): Matrix4 {
    const matrix = new Matrix4();
    const f = 1.0 / Math.tan(fov / 2);
    matrix.m[0][0] = f / aspect;
    matrix.m[1][1] = f;
    matrix.m[2][2] = (near + far) / (near - far);
    matrix.m[2][3] = (2 * near * far) / (near - far);
    matrix.m[3][2] = -1;
    return matrix;
  }

  static orthographic(left: number, right: number, bottom: number, top: number, near: number, far: number): Matrix4 {
    const matrix = new Matrix4();
    matrix.m[0][0] = 2 / (right - left);
    matrix.m[1][1] = 2 / (top - bottom);
    matrix.m[2][2] = -2 / (far - near);
    matrix.m[3][0] = -(right + left) / (right - left);
    matrix.m[3][1] = -(top + bottom) / (top - bottom);
    matrix.m[3][2] = -(far + near) / (far - near);
    return matrix;
  }

  static lookAt(position: Vector3, target: Vector3, up: Vector3): Matrix4 {
    const matrix = new Matrix4();
    const z = position.clone().subtractVec(target).normalize();
    const x = up.clone().cross(z).normalize();
    const y = z.clone().cross(x).normalize();
    matrix.m[0][0] = x.x;
    matrix.m[0][1] = y.x;
    matrix.m[0][2] = z.x;
    matrix.m[1][0] = x.y;
    matrix.m[1][1] = y.y;
    matrix.m[1][2] = z.y;
    matrix.m[2][0] = x.z;
    matrix.m[2][1] = y.z;
    matrix.m[2][2] = z.z;
    matrix.m[3][0] = -x.dot(position);
    matrix.m[3][1] = -y.dot(position);
    matrix.m[3][2] = -z.dot(position);
    return matrix;
  }
}

export class Quaternion {
  x: number;
  y: number;
  z: number;
  w: number;

  constructor(x: number, y: number, z: number, w: number) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  multiply(quaternion: Quaternion): Quaternion {
    const result = new Quaternion(
      this.w * quaternion.x + this.x * quaternion.w + this.y * quaternion.z - this.z * quaternion.y,
      this.w * quaternion.y - this.x * quaternion.z + this.y * quaternion.w + this.z * quaternion.x,
      this.w * quaternion.z + this.x * quaternion.y - this.y * quaternion.x + this.z * quaternion.w,
      this.w * quaternion.w - this.x * quaternion.x - this.y * quaternion.y - this.z * quaternion.z
    );
    return result;
  }

  normalize(): Quaternion {
    const result = new Quaternion(
      this.x / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w),
      this.y / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w),
      this.z / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w),
      this.w / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w)
    );
    return result;
  }

  rotateXYZ(x: number, y: number, z: number): Quaternion {
    const halfX = x / 2;
    const halfY = y / 2;
    const halfZ = z / 2;

    const sinX = Math.sin(halfX);
    const cosX = Math.cos(halfX);
    const sinY = Math.sin(halfY);
    const cosY = Math.cos(halfY);
    const sinZ = Math.sin(halfZ);
    const cosZ = Math.cos(halfZ);

    this.x=cosZ * sinY * cosX + sinZ * cosY * sinX;
    this.y=cosZ * cosY * sinX - sinZ * sinY * cosX;
    this.z=sinZ * cosY * cosX - cosZ * sinY * sinX;
    this.w=cosZ * cosY * cosX + sinZ * sinY * sinX;

    return this;
  }

  rotateX(x: number): Quaternion {
    const halfX = x / 2;
    const sinX = Math.sin(halfX);
    const cosX = Math.cos(halfX);

    this.x = this.x * cosX - this.y * sinX - this.z * sinX;
    this.y = this.x * sinX + this.y * cosX - this.z * sinX;
    this.z = this.x * sinX + this.y * sinX + this.z * cosX;
    this.w = this.w * cosX + this.x * sinX + this.y * sinX + this.z * sinX;

    return this;
  }

  rotateY(y: number): Quaternion {
    const halfY = y / 2;
    const sinY = Math.sin(halfY);
    const cosY = Math.cos(halfY);

    this.x = this.x * cosY - this.z * sinY;
    this.y = this.y * cosY + this.z * sinY;
    this.z = this.x * sinY + this.y * cosY;
    this.w = this.w * cosY - this.x * sinY + this.y * sinY + this.z * cosY;

    return this;
  }

  rotateZ(z: number): Quaternion {
    const halfZ = z / 2;
    const sinZ = Math.sin(halfZ);
    const cosZ = Math.cos(halfZ);

    this.x = this.x * cosZ - this.y * sinZ;
    this.y = this.x * sinZ + this.y * cosZ;
    this.z = this.z * cosZ + this.x * sinZ + this.y * sinZ;
    this.w = this.w * cosZ - this.x * sinZ - this.y * sinZ + this.z * cosZ;

    return this;
  }

  invert(): Quaternion {
    this.x = -this.x;
    this.y = -this.y;
    this.z = -this.z;
    this.w = -this.w;
    return this;
  }

  conjugate(): Quaternion {
    this.x = -this.x;
    this.y = -this.y;
    this.z = -this.z;
    return this;
  }

  clone(): Quaternion {
    return new Quaternion(this.x, this.y, this.z, this.w);
  }

  equals(quaternion: Quaternion): boolean {
    return this.x === quaternion.x && this.y === quaternion.y && this.z === quaternion.z && this.w === quaternion.w;
  }

  length(): number {
    return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
  }

  toString(): string {
    return `Quaternion(x: ${this.x}, y: ${this.y}, z: ${this.z}, w: ${this.w})`;
  }

  toArray(): number[] {
    return [this.x, this.y, this.z, this.w];
  }

  static fromArray(array: number[]): Quaternion {
    return new Quaternion(array[0], array[1], array[2], array[3]);
  }

  static identity(): Quaternion {
    return new Quaternion(0, 0, 0, 1);
  }
}

export class Matrix3 {
  m: number[][] = [
    [1, 0, 0],
    [0, 1, 0],
    [0, 0, 1]
  ];
  constructor(props: { m: number[][] } = { m: [[1, 0, 0], [0, 1, 0], [0, 0, 1]] }) {
    this.m = props.m;
  }

  multiply(matrix: Matrix3): Matrix3 {
    const result = new Matrix3();
    for (let i = 0; i < 3; i++) {
      for (let j = 0; j < 3; j++) {
        result.m[i][j] = 0;
        for (let k = 0; k < 3; k++) {
          result.m[i][j] += this.m[i][k] * matrix.m[k][j];
        }
      }
    }
    return result;
  }

  invert(): Matrix3 {
    const det = this.m[0][0] * this.m[1][1] * this.m[2][2] + this.m[0][1] * this.m[1][2] * this.m[2][0] + this.m[0][2] * this.m[1][0] * this.m[2][1] -
      this.m[0][2] * this.m[1][1] * this.m[2][0] - this.m[0][0] * this.m[1][2] * this.m[2][1] - this.m[0][1] * this.m[1][0] * this.m[2][2];
    const invDet = 1 / det;
    const result = new Matrix3();
    result.m[0][0] = (this.m[1][1] * this.m[2][2] - this.m[1][2] * this.m[2][1]) * invDet;
    result.m[0][1] = (this.m[0][2] * this.m[2][1] - this.m[0][1] * this.m[2][2]) * invDet;
    result.m[0][2] = (this.m[0][1] * this.m[1][2] - this.m[0][2] * this.m[1][1]) * invDet;
    result.m[1][0] = (this.m[1][2] * this.m[2][0] - this.m[1][0] * this.m[2][2]) * invDet;
    result.m[1][1] = (this.m[0][0] * this.m[2][2] - this.m[0][2] * this.m[2][0]) * invDet;
    result.m[1][2] = (this.m[0][2] * this.m[1][0] - this.m[0][0] * this.m[1][2]) * invDet;
    result.m[2][0] = (this.m[1][0] * this.m[2][1] - this.m[1][1] * this.m[2][0]) * invDet;
    result.m[2][1] = (this.m[0][1] * this.m[2][0] - this.m[0][0] * this.m[2][1]) * invDet;
    result.m[2][2] = (this.m[0][0] * this.m[1][1] - this.m[0][1] * this.m[1][0]) * invDet;
    return result;
  }

  transpose(): Matrix3 {
    const result = new Matrix3();
    for (let i = 0; i < 3; i++) {
      for (let j = 0; j < 3; j++) {
        result.m[i][j] = this.m[j][i];
      }
    }
    return result;
  }

  determinant(): number {
    return this.m[0][0] * this.m[1][1] * this.m[2][2] + this.m[0][1] * this.m[1][2] * this.m[2][0] + this.m[0][2] * this.m[1][0] * this.m[2][1] -
      this.m[0][2] * this.m[1][1] * this.m[2][0] - this.m[0][0] * this.m[1][2] * this.m[2][1] - this.m[0][1] * this.m[1][0] * this.m[2][2];
  }

  get(i: number, j: number): number {
    return this.m[i][j];
  }

  set(i: number, j: number, value: number) {
    this.m[i][j] = value;
  }

  toArray(): number[] {
    return this.m.flat();
  }

  toArray2D(): number[][] {
    return this.m;
  }

  clone(): Matrix3 {
    return new Matrix3({ m: this.m });
  }

  deepClone(): Matrix3 {
    return new Matrix3({ m: this.m.map(row => row.slice()) });
  }

  equals(matrix: Matrix3): boolean {
    return this.m[0][0] === matrix.m[0][0] && this.m[0][1] === matrix.m[0][1] && this.m[0][2] === matrix.m[0][2] &&
      this.m[1][0] === matrix.m[1][0] && this.m[1][1] === matrix.m[1][1] && this.m[1][2] === matrix.m[1][2] &&
      this.m[2][0] === matrix.m[2][0] && this.m[2][1] === matrix.m[2][1] && this.m[2][2] === matrix.m[2][2];
  }

  toString(): string {
    return `Matrix3(m: ${this.m})`;
  }
}
