package dev.ultreon.quantum.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import dev.ultreon.quantum.InternalApi
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.util.NamespaceID
import ktx.math.mat4
import ktx.math.vec3

inline fun ModelBuilder.part(
  name: String = "part",
  vertexAttributes: VertexAttributes = VertexAttributes(
    VertexAttribute.Position(),
    VertexAttribute.Normal(),
    VertexAttribute.ColorPacked(),
    VertexAttribute.TexCoords(0)
  ),
  primitiveType: Int = GL20.GL_TRIANGLES,
  material: Material = Material(),
  crossinline init: MeshPartBuilder.() -> Unit,
): ModelBuilder {
  part(name, GL20.GL_TRIANGLES, vertexAttributes, material).also {
    init(it)
  }
  return this
}

inline fun model(builder: ModelBuilder = ModelBuilder(), init: ModelBuilder.() -> Unit): Model {
  builder.begin()
  builder.init()
  return builder.end()
}

inline fun mesh(builder: MeshBuilder = MeshBuilder(), attributes: VertexAttributes, primitiveType: Int = GL20.GL_TRIANGLES, init: MeshPartBuilder.() -> Unit): Mesh {
  builder.begin(attributes, primitiveType)
  builder.init()
  return builder.end()
}

inline fun texture(path: FileHandle, crossinline init: Texture.() -> Unit = {}): Texture {
  val texture = Texture(path)
  init(texture)
  return texture
}

@Deprecated("Use texture with namespace id instead",
  ReplaceWith("texture(NamespaceID.of(path = path))", "dev.ultreon.quantum.util.NamespaceID")
)
inline fun texture(path: String, crossinline init: Texture.() -> Unit = {}): TextureRegion {
  return texture(NamespaceID.of(path = path))
}

fun texture(namespaceID: NamespaceID): TextureRegion {
  val texture = QuantumVoxel.textureManager[namespaceID]
  return texture
}

class Vertex {
  var position: Vector3 = Vector3()
  var normal: Vector3 = Vector3()
  var color: Color = Color(1f, 1f, 1f, 1f)
  var texCoords: Vector2 = Vector2()

  fun position(x: Float, y: Float, z: Float): Vertex {
    position.set(x, y, z)
    return this
  }

  fun normal(x: Float, y: Float, z: Float): Vertex {
    normal.set(x, y, z)
    return this
  }

  fun color(red: Float, green: Float, blue: Float, alpha: Float): Vertex {
    color.set(red, green, blue, alpha)
    return this
  }

  fun texCoords(x: Float, y: Float): Vertex {
    texCoords.set(x, y)
    return this
  }
}

@InternalApi
var tmpVertex = Vertex()

inline fun vertex(init: Vertex.() -> Unit): VertexInfo {
  val vertex = Vertex()
  init(vertex)
  return VertexInfo().set(
    vertex.position,
    vertex.normal,
    vertex.color,
    vertex.texCoords
  )
}

private var tmpMat = mat4()

fun MeshPartBuilder.face(x: Float, y: Float, z: Float, textureRegion: TextureRegion, width: Float, height: Float, direction: Vector3) {
  val normal = direction.nor()
  val v00 = vertex { position(x, y, z); normal(normal.x, normal.y, normal.z); texCoords(textureRegion.u, textureRegion.v) }
  val v10 = vertex { position(x + width, y, z); normal(normal.x, normal.y, normal.z); texCoords(textureRegion.u2, textureRegion.v) }
  val v11 = vertex { position(x + width, y + height, z); normal(normal.x, normal.y, normal.z); texCoords(textureRegion.u2, textureRegion.v2) }
  val v01 = vertex { position(x, y + height, z); normal(normal.x, normal.y, normal.z); texCoords(textureRegion.u, textureRegion.v2) }
  // Change position based on direction
  tmpMat.idt().rotate(Vector3.X, normal.x)
    .rotate(Vector3.Y, normal.y)
    .rotate(Vector3.Z, normal.z)

  v00.position.mul(tmpMat)
  v10.position.mul(tmpMat)
  v11.position.mul(tmpMat)
  v01.position.mul(tmpMat)

  rect(v00, v10, v11, v01)
}

fun MeshPartBuilder.cube(
  x: Float = 0f,
  y: Float = 0f,
  z: Float = 0f,
  width: Float = 1f,
  height: Float = 1f,
  depth: Float = 1f,
  textureRegion: TextureRegion = TextureRegion()
) {
  // North
  if (!Gdx.input.isKeyPressed(Keys.NUM_1)) {
    northFace(x, y, z, textureRegion, width, height)
  }

  // West
  if (!Gdx.input.isKeyPressed(Keys.NUM_2)) {
    westFace(x, y, z, textureRegion, height, depth)
  }

  // South
  if (!Gdx.input.isKeyPressed(Keys.NUM_3)) {
    southFace(x, y, z, textureRegion, depth, width, height)
  }

  // East
  if (!Gdx.input.isKeyPressed(Keys.NUM_4)) {
    eastFace(x, y, z, textureRegion, width, depth, height)
  }

  // Top
  if (!Gdx.input.isKeyPressed(Keys.NUM_5)) {
    topFace(x, y, height, z, textureRegion, width, depth)
  }

  // Bottom
  if (!Gdx.input.isKeyPressed(Keys.NUM_6)) {
    bottomFace(x, y, z, textureRegion, width, depth)
  }
}

fun MeshPartBuilder.bottomFace(
  x: Float,
  y: Float,
  z: Float,
  textureRegion: TextureRegion,
  width: Float,
  depth: Float,
) {
  val v00 = vertex {
    position(x, y, z)
    normal(0f, -1f, 0f)
    texCoords(textureRegion.u, textureRegion.v)
  }

  val v10 = vertex {
    position(x + width, y, z)
    normal(0f, -1f, 0f)
    texCoords(textureRegion.u2, textureRegion.v)
  }

  val v11 = vertex {
    position(x + width, y, z + depth)
    normal(0f, -1f, 0f)
    texCoords(textureRegion.u2, textureRegion.v2)
  }

  val v01 = vertex {
    position(x, y, z + depth)
    normal(0f, -1f, 0f)
    texCoords(textureRegion.u, textureRegion.v2)
  }

  rect(v00, v10, v11, v01)
}

fun MeshPartBuilder.topFace(
  x: Float,
  y: Float,
  height: Float,
  z: Float,
  textureRegion: TextureRegion,
  width: Float,
  depth: Float,
) {
  val v00 = vertex {
    position(x, y + height, z)
    normal(0f, 1f, 0f)
    texCoords(textureRegion.u, textureRegion.v)
  }

  val v10 = vertex {
    position(x + width, y + height, z)
    normal(0f, 1f, 0f)
    texCoords(textureRegion.u2, textureRegion.v)
  }

  val v11 = vertex {
    position(x + width, y + height, z + depth)
    normal(0f, 1f, 0f)
    texCoords(textureRegion.u2, textureRegion.v2)
  }

  val v01 = vertex {
    position(x, y + height, z + depth)
    normal(0f, 1f, 0f)
    texCoords(textureRegion.u, textureRegion.v2)
  }

  rect(v10, v00, v01, v11)
}

fun MeshPartBuilder.eastFace(
  x: Float,
  y: Float,
  z: Float,
  textureRegion: TextureRegion,
  dx: Float,
  depth: Float,
  height: Float,
) {
  val v01 = vertex {
    position(x + dx, y, z + depth)
    normal(1f, 0f, 0f)
    texCoords(textureRegion.u, textureRegion.v)
  }

  val v11 = vertex {
    position(x + dx, y + height, z + depth)
    normal(1f, 0f, 0f)
    texCoords(textureRegion.u, textureRegion.v2)
  }

  val v10 = vertex {
    position(x + dx, y + height, z)
    normal(1f, 0f, 0f)
    texCoords(textureRegion.u2, textureRegion.v2)
  }

  val v00 = vertex {
    position(x + dx, y, z)
    normal(1f, 0f, 0f)
    texCoords(textureRegion.u2, textureRegion.v)
  }

  rect(v00, v10, v11, v01)
}

fun MeshPartBuilder.southFace(
  x: Float,
  y: Float,
  z: Float,
  textureRegion: TextureRegion,
  dz: Float,
  width: Float,
  height: Float,
) {
  val v00 = vertex {
    position(x, y, z + dz)
    normal(0f, 0f, -1f)
    texCoords(textureRegion.u, textureRegion.v)
  }

  val v10 = vertex {
    position(x + width, y, z + dz)
    normal(0f, 0f, -1f)
    texCoords(textureRegion.u2, textureRegion.v)
  }

  val v11 = vertex {
    position(x + width, y + height, z + dz)
    normal(0f, 0f, -1f)
    texCoords(textureRegion.u2, textureRegion.v2)
  }

  val v01 = vertex {
    position(x, y + height, z + dz)
    normal(0f, 0f, -1f)
    texCoords(textureRegion.u, textureRegion.v2)
  }

  rect(v00, v10, v11, v01)
}

fun MeshPartBuilder.westFace(
  x: Float,
  y: Float,
  z: Float,
  textureRegion: TextureRegion,
  height: Float,
  depth: Float,
) {
  val v00 = vertex {
    position(x, y, z)
    normal(-1f, 0f, 0f)
    texCoords(textureRegion.u, textureRegion.v)
  }

  val v10 = vertex {
    position(x, y + height, z)
    normal(-1f, 0f, 0f)
    texCoords(textureRegion.u, textureRegion.v2)
  }

  val v11 = vertex {
    position(x, y + height, z + depth)
    normal(-1f, 0f, 0f)
    texCoords(textureRegion.u2, textureRegion.v2)
  }

  val v01 = vertex {
    position(x, y, z + depth)
    normal(-1f, 0f, 0f)
    texCoords(textureRegion.u2, textureRegion.v)
  }

  rect(v10, v00, v01, v11)
}

fun MeshPartBuilder.northFace(
  x: Float,
  y: Float,
  z: Float,
  textureRegion: TextureRegion,
  width: Float,
  height: Float,
) {
  val v00 = vertex {
    position(x, y, z)
    normal(0f, 0f, 1f)
    texCoords(textureRegion.u, textureRegion.v)
  }

  val v10 = vertex {
    position(x + width, y, z)
    normal(0f, 0f, 1f)
    texCoords(textureRegion.u2, textureRegion.v)
  }

  val v11 = vertex {
    position(x + width, y + height, z)
    normal(0f, 0f, 1f)
    texCoords(textureRegion.u2, textureRegion.v2)
  }

  val v01 = vertex {
    position(x, y + height, z)
    normal(0f, 0f, 1f)
    texCoords(textureRegion.u, textureRegion.v2)
  }

  rect(v10, v00, v01, v11)
}

fun MeshPartBuilder.quad(
  x: Float = 0f,
  y: Float = 0f,
  z: Float = 0f,
  width: Float = 1f,
  height: Float = 1f,
  textureRegion: TextureRegion = TextureRegion()
) {
  val v00 = vertex {
    position(x, y, z)
    normal(0f, 0f, 1f)
    texCoords(textureRegion.u, textureRegion.v)
  }

  val v01 = vertex {
    position(x + width, y, z)
    normal(0f, 0f, 1f)
    texCoords(textureRegion.u2, textureRegion.v)
  }

  val v10 = vertex {
    position(x, y + height, z)
    normal(0f, 0f, 1f)
    texCoords(textureRegion.u, textureRegion.v2)
  }

  val v11 = vertex {
    position(x + width, y + height, z)
    normal(0f, 0f, 1f)
    texCoords(textureRegion.u2, textureRegion.v2)
  }

  triangle(v00, v01, v10)
  triangle(v10, v01, v11)
}

fun perspectiveCamera(init: PerspectiveCamera.() -> Unit = {}): PerspectiveCamera {
  val camera = PerspectiveCamera()
  camera.init()
  return camera
}

fun instance(model: Model, init: ModelInstance.() -> Unit = {}): ModelInstance {
  val instance = ModelInstance(model)
  instance.init()
  return instance
}

fun vec3d(x: Double, y: Double, z: Double): Vector3D {
  return Vector3D(x, y, z)
}

private var tmpVecD = vec3d(0.0, 0.0, 0.0)
internal var tmpVec = vec3(0F, 0F, 0F)

fun ModelInstance.relative(camera: Camera, position: Vector3D): ModelInstance {
  this.transform.setTranslation(tmpVecD.set(position.x, position.y, position.z).sub(camera.position).let {
    return@let tmpVec.set(it.x.toFloat(), it.y.toFloat(), it.z.toFloat())
  })
  return this
}
