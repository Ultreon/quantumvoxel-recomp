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
import dev.ultreon.quantum.vec3d
import ktx.math.mat4
import ktx.math.unaryMinus
import ktx.math.vec3

/**
 * Creates and initializes a part within a 3D model using the specified parameters.
 *
 * @param name The name of the part. Defaults to "part".
 * @param vertexAttributes The vertex attributes defining the properties of the model's vertices. Defaults to a set of position, normal, color-packed, and texture coordinates attributes.
 * @param primitiveType The type of primitive to use for rendering. Defaults to `GL20.GL_TRIANGLES`.
 * @param material The material to apply to this part of the model. Defaults to an empty `Material`.
 * @param init A lambda executed with a `MeshPartBuilder` to define the details of the mesh for this part.
 * @return The current `ModelBuilder` instance for chaining.
 */
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

/**
 * Creates a 3D model by initializing and finalizing a `ModelBuilder` using the provided lambda function.
 *
 * This function begins a new model construction process using the supplied `ModelBuilder`, applies
 * the initialization logic defined in the `init` lambda, and finalizes the model construction,
 * returning the resulting `Model` instance.
 *
 * @param builder The `ModelBuilder` to use for constructing the model. Defaults to a new `ModelBuilder` instance.
 * @param init A lambda containing the initialization logic for defining the model parts, materials, and geometry.
 * @return The constructed `Model` instance.
 */
inline fun model(builder: ModelBuilder = ModelBuilder(), init: ModelBuilder.() -> Unit): Model {
  builder.begin()
  builder.init()
  return builder.end()
}

/**
 * Creates and initializes a 3D mesh using the provided parameters.
 *
 * This function simplifies the creation of a `Mesh` by using a `MeshBuilder` with a given set of
 * vertex attributes and a primitive type. Custom initialization logic can be defined in the
 * `init` lambda, which is applied to a `MeshPartBuilder` instance during the build process.
 *
 * @param builder The `MeshBuilder` to use for creating the mesh. Defaults to a new `MeshBuilder` instance.
 * @param attributes The vertex attributes defining the vertex properties, such as position, normal, color, and texture coordinates.
 * @param primitiveType The type of primitive to use for rendering. Defaults to `GL20.GL_TRIANGLES`.
 * @param init A lambda executed to configure the mesh through a `MeshPartBuilder`.
 * @return The constructed `Mesh` instance.
 */
inline fun mesh(builder: MeshBuilder = MeshBuilder(), attributes: VertexAttributes, primitiveType: Int = GL20.GL_TRIANGLES, init: MeshPartBuilder.() -> Unit): Mesh {
  builder.begin(attributes, primitiveType)
  builder.init()
  return builder.end()
}

/**
 * Creates a `Texture` instance from the specified file and applies an optional initialization block.
 *
 * This function simplifies the creation of a `Texture` by initializing it with the given file path.
 * Additionally, a lambda function can be provided to configure the `Texture` instance.
 *
 * @param path The file handle pointing to the texture file.
 * @param init An optional lambda expression used to configure the `Texture`. Defaults to an empty block.
 * @return The created and initialized `Texture` instance.
 */
inline fun texture(path: FileHandle, crossinline init: Texture.() -> Unit = {}): Texture {
  val texture = Texture(path)
  init(texture)
  return texture
}

/**
 * Creates a `TextureRegion` from the specified texture path and applies an optional initialization block.
 *
 * This function simplifies the creation of a `TextureRegion` by utilizing a string path to identify the texture
 * and optionally initializing it through the provided lambda. It has been deprecated in favor of using `NamespaceID`
 * for texture identification.
 *
 * @param path The path to the texture file as a string.
 * @param init An optional lambda expression used to configure the `Texture`. Defaults to an empty block.
 * @return The created `TextureRegion` instance.
 *
 * @deprecated Use the `texture` function that accepts a `NamespaceID` instead.
 * Replace with `texture(NamespaceID.of(path = path))`.
 */
@Deprecated("Use texture with namespace id instead",
  ReplaceWith("texture(NamespaceID.of(path = path))", "dev.ultreon.quantum.util.NamespaceID")
)
inline fun texture(path: String, crossinline init: Texture.() -> Unit = {}): TextureRegion {
  return texture(NamespaceID.of(path = path))
}

/**
 * Retrieves a `TextureRegion` associated with the given `NamespaceID`.
 *
 * This function uses the `QuantumVoxel.textureManager` to look up and return the texture
 * corresponding to the provided `NamespaceID`.
 *
 * @param namespaceID The identifier for the texture resource to retrieve.
 * @return The `TextureRegion` associated with the specified `NamespaceID`.
 */
fun texture(namespaceID: NamespaceID): TextureRegion {
  val texture = QuantumVoxel.textureManager[namespaceID]
  return texture
}

/**
 * Represents a vertex in a 3D space, containing properties for position, normal, color, and texture coordinates.
 *
 * This class is used to define the attributes of a vertex in a 3D model, including its spatial position, surface
 * normal for lighting calculations, color, and associated texture coordinates for mapping textures.
 *
 * The provided methods allow for the modification of these attributes with a fluent API style.
 */
class Vertex {
  var position: Vector3 = Vector3()
  var normal: Vector3 = Vector3()
  var color: Color = Color(1f, 1f, 1f, 1f)
  var texCoords: Vector2 = Vector2()

  /**
   * Sets the position of the vertex using the provided coordinates.
   *
   * @param x The x-coordinate of the position.
   * @param y The y-coordinate of the position.
   * @param z The z-coordinate of the position.
   * @return The modified vertex instance.
   */
  fun position(x: Float, y: Float, z: Float): Vertex {
    position.set(x, y, z)
    return this
  }

  /**
   * Sets the normal vector of the vertex using the provided coordinates.
   *
   * @param x The x-coordinate of the normal vector.
   * @param y The y-coordinate of the normal vector.
   * @param z The z-coordinate of the normal vector.
   * @return The modified vertex instance.
   */
  fun normal(x: Float, y: Float, z: Float): Vertex {
    normal.set(x, y, z)
    return this
  }

  /**
   * Sets the color of the vertex using the provided red, green, blue, and alpha components.
   *
   * @param red The red component of the color, ranging from 0.0 to 1.0.
   * @param green The green component of the color, ranging from 0.0 to 1.0.
   * @param blue The blue component of the color, ranging from 0.0 to 1.0.
   * @param alpha The alpha (transparency) component of the color, ranging from 0.0 (completely transparent) to 1.0 (completely opaque).
   * @return The modified vertex instance.
   */
  fun color(red: Float, green: Float, blue: Float, alpha: Float): Vertex {
    color.set(red, green, blue, alpha)
    return this
  }

  /**
   * Sets the texture coordinates of the vertex using the provided x and y components.
   *
   * @param x The x-coordinate of the texture coordinate.
   * @param y The y-coordinate of the texture coordinate.
   * @return The modified vertex instance.
   */
  fun texCoords(x: Float, y: Float): Vertex {
    texCoords.set(x, y)
    return this
  }
}

@InternalApi
var tmpVertex = Vertex()

/**
 * Creates and initializes a `Vertex` instance using the specified configuration block.
 *
 * This function provides a convenient way to configure a vertex by applying the given
 * lambda to a newly created `Vertex` instance. The resulting vertex attributes, such as
 * position, normal, color, and texture coordinates, are then transferred to a `VertexInfo`
 * object for further use in constructing meshes or 3D models.
 *
 * @param init A lambda block that is applied to the `Vertex` instance for configuring its attributes.
 * @return A `VertexInfo` containing the configured attributes of the vertex.
 */
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

/**
 * Adds a face to the current mesh being built using the specified parameters.
 *
 * This method creates a rectangular face at the given position with the specified width and height,
 * applies the given texture region to the face, and calculates its normal vector based on the direction provided.
 * The vertices of the face are transformed to align with the direction vector.
 *
 * @receiver The `MeshPartBuilder` instance to which the face will be added.
 * @param x The x-coordinate of the bottom-left corner of the face.
 * @param y The y-coordinate of the bottom-left corner of the face.
 * @param z The z-coordinate of the bottom-left corner of the face.
 * @param textureRegion The texture region to apply to the face.
 * @param width The width of the face along the local x-direction.
 * @param height The height of the face along the local y-direction.
 * @param direction A vector representing the direction in which the face's normal should point.
 */
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

/**
 * Adds a cube to the current mesh being built with specified dimensions and texture.
 *
 * This method generates a cube by creating its six faces (north, west, south, east, top, and bottom).
 * Each face is added conditionally based on user input, allowing for selective rendering of faces.
 * Texture coordinates are applied to ensure the specified `TextureRegion` is mapped onto the cube's surfaces.
 *
 * @receiver The `MeshPartBuilder` instance to which the cube will be added.
 * @param x The x-coordinate of the origin of the cube. Defaults to 0.
 * @param y The y-coordinate of the origin of the cube. Defaults to 0.
 * @param z The z-coordinate of the origin of the cube. Defaults to 0.
 * @param width The width of the cube along the x-axis. Defaults to 1.
 * @param height The height of the cube along the y-axis. Defaults to 1.
 * @param depth The depth of the cube along the z-axis. Defaults to 1.
 * @param textureRegion The texture region to be applied to the cube's faces. Defaults to an empty `TextureRegion`.
 */
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

private var tmpVecD = vec3d(0.0, 0.0, 0.0)
internal var tmpVec = vec3(0F, 0F, 0F)

/**
 * Adjusts the `ModelInstance`'s position relative to the given `Camera` and `Vector3D` position.
 *
 * This function modifies the translation of the `ModelInstance`'s transformation matrix
 * by calculating the difference between the specified `position` and the `camera`'s current position.
 * The resulting translation ensures the `ModelInstance` is positioned correctly in relation to the camera and the target position.
 *
 * The reason this is used is to prevent mesh tearing issues due to floating point precision on large distances.
 *
 * @receiver The `ModelInstance` whose transformation will be adjusted.
 * @param camera The camera used to determine the reference position for the adjustment.
 * @param position The target `Vector3D` position used to compute the relative position.
 * @return The updated `ModelInstance` with adjusted transformation.
 */
fun ModelInstance.relative(position: Vector3D): ModelInstance {
  this.transform.setTranslation(-(tmpVecD.set(position.x, position.y, position.z).sub(vec3d()).let {
    return@let tmpVec.set(it.x.toFloat(), it.y.toFloat(), it.z.toFloat())
  }))
  return this
}
