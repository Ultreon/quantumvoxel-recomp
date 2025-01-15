package dev.ultreon.quantum.client

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.*
import dev.ultreon.quantum.InternalApi

@DslMarker
annotation class MaterialDsl

/**
 * MaterialBuilder is a DSL interface for configuring materials with various attributes used in 3D rendering.
 *
 * This builder provides methods to define textures, colors, and properties that influence the appearance
 * of a material under different types of lighting, such as diffuse, emissive, ambient, and specular. It
 * also allows setting additional properties like fog color, ambient lighting, and face culling mode to
 * control rendering behavior and visual effects.
 *
 * The interface enables a type-safe and declarative way to construct `Material` instances through
 * custom configurations during the material creation process.
 */
@MaterialDsl
interface MaterialBuilder {
  /**
   * Sets the diffuse texture for the material.
   *
   * The diffuse texture determines the base color or pattern of the material
   * when lit by a light source, providing realistic shading and coloring effects.
   *
   * @param texture The texture to be used as the diffuse map for the material.
   */
  fun diffuse(texture: Texture)

  /**
   * Sets the diffuse texture for the material using a `TextureRegion`.
   *
   * The diffuse texture determines the base appearance of the material,
   * including color and pattern, when subjected to lighting, allowing for
   * realistic shading and rendering. This method allows the use of a
   * specific region of a texture as the diffuse map.
   *
   * @param texture The `TextureRegion` to use as the diffuse map for the material.
   */
  fun diffuse(texture: TextureRegion)

  /**
   * Sets the diffuse color for the material.
   *
   * The diffuse color defines the base coloration of the material when lit by a light source,
   * affecting how light interacts with the surface for realistic shading and rendering.
   *
   * @param color The color to be applied as the diffuse property of the material.
   */
  fun diffuse(color: Color)

  /**
   * Sets the emissive texture for the material.
   *
   * The emissive texture allows the material to emit light, simulating a glowing effect. This property
   * is used to define parts of the material that should appear self-illuminated, independent of lighting in the scene.
   *
   * @param texture The texture to be applied as the emissive map for the material.
   */
  fun emissive(texture: Texture)

  /**
   * Sets the emissive texture for the material using a `TextureRegion`.
   *
   * The emissive texture allows the material to emit light, creating a glowing effect. This property
   * defines parts of the material that should appear self-illuminated, unaffected by external lighting in the scene.
   *
   * @param texture The `TextureRegion` to be applied as the emissive map for the material.
   */
  fun emissive(texture: TextureRegion)

  /**
   * Sets the emissive color for the material.
   *
   * The emissive color allows the material to emit light, creating a glowing effect.
   * This property defines the self-illumination color of the material, making it appear
   * as though it is radiating light regardless of external lighting in the scene.
   *
   * @param color The color to be applied as the emissive property of the material.
   */
  fun emissive(color: Color)

  /**
   * Sets the normal texture for the material.
   *
   * The normal texture is used to add detailed surface features like bumps or grooves,
   * enhancing the illusion of a complex surface geometry. This property modifies how light
   * interacts with the material, creating a more realistic appearance by simulating variations
   * in the surface normals.
   *
   * @param texture The texture to be applied as the normal map for the material.
   */
  fun normal(texture: Texture)

  /**
   * Sets the normal texture for the material using a `TextureRegion`.
   *
   * The normal texture is used to add fine details like bumps or grooves to the surface,
   * improving the realism of the material by simulating changes in the surface normals.
   * This enhances the way light interacts with the material, creating the appearance
   * of more detailed surface geometry.
   *
   * @param texture The `TextureRegion` to be applied as the normal map for the material.
   */
  fun normal(texture: TextureRegion)

  /**
   * Sets the ambient texture for the material.
   *
   * The ambient texture defines how the material responds to ambient light,
   * which is the indirect, all-encompassing light in a scene. This property
   * affects the overall tone and coloring of the material under ambient lighting conditions.
   *
   * @param texture The texture to be applied as the ambient map for the material.
   */
  fun ambient(texture: Texture)

  /**
   * Sets the ambient texture for the material using a `TextureRegion`.
   *
   * The ambient texture determines how the material interacts with ambient lighting,
   * which represents the indirect and scattered light present in the environment.
   * This property influences the overall look and tone of the material under
   * ambient lighting conditions. Using a `TextureRegion` allows specifying
   * a specific portion of a texture as the ambient map.
   *
   * @param texture The `TextureRegion` to use as the ambient map for the material.
   */
  fun ambient(texture: TextureRegion)

  /**
   * Sets the ambient color for the material.
   *
   * The ambient color determines how the material responds to ambient light in the scene.
   * This property influences the overall tone and illumination of the material under
   * indirect lighting, providing a softer and more diffused lighting effect.
   *
   * @param color The color to be applied as the ambient property of the material.
   */
  fun ambient(color: Color)

  /**
   * Sets the specular texture for the material.
   *
   * The specular texture determines the shininess and reflectivity of the material's surface.
   * It defines how light reflects from the material, influencing highlights and creating
   * a more polished or metallic appearance depending on the texture.
   *
   * @param texture The texture to be applied as the specular map for the material.
   */
  fun specular(texture: Texture)

  /**
   * Sets the specular texture for the material using a `TextureRegion`.
   *
   * The specular texture controls the shininess and reflective qualities of the material's surface.
   * It affects how light reflects from the material, defining highlights and contributing to
   * a polished or metallic look. Using a `TextureRegion` allows for specifying a specific portion
   * of a texture as the specular map.
   *
   * @param texture The `TextureRegion` to be applied as the specular map for the material.
   */
  fun specular(texture: TextureRegion)

  /**
   * Sets the specular color for the material.
   *
   * The specular color determines the shininess and reflective properties of the material's surface.
   * It controls how light interacts with the surface to produce highlights, influencing the appearance
   * of glossiness or metallic effects.
   *
   * @param color The color to be applied as the specular property of the material.
   */
  fun specular(color: Color)

  /**
   * Sets the ambient light color for the material.
   *
   * The ambient light color defines how the material reacts to general light present in a scene.
   * This property influences the overall brightness and tone of the material under ambient lighting conditions,
   * simulating the effect of indirect, non-directional lighting.
   *
   * @param ambientLight The color representing the ambient light applied to the material.
   */
  fun ambientLight(ambientLight: Color)

  /**
   * Sets the fog color for the material.
   *
   * The fog color defines the appearance of fog applied to the material,
   * creating a sense of depth or atmospheric effect by blending the material's color
   * with the fog color over distance.
   *
   * @param fog The color to be applied as the fog property of the material.
   */
  fun fog(fog: Color)

  /**
   * Sets the cull face property for the material.
   *
   * The cull face determines which side(s) of the material should be culled or not rendered.
   * This property is typically used for rendering optimization by not displaying back-facing
   * or front-facing polygons, depending on the specified culling mode.
   *
   * @param cullFace The culling mode to apply, represented as an integer. Common values
   *                 may include constants for front-face, back-face, or both-face culling.
   */
  fun cullFace(cullFace: Int)

  /**
   * Configures the blending mode for the material.
   *
   * The blending mode determines how the material's colors are combined with those
   * of the background when rendered. It uses source and destination factors to define
   * the blending behavior, enabling effects such as transparency or additive blending.
   *
   * @param srcFactor The source blending factor, which influences how the material's color is computed.
   * @param dstFactor The destination blending factor, which influences how the background color is computed.
   */
  fun blendMode(srcFactor: Int, dstFactor: Int)

  /**
   * Sets the alpha test threshold for the material.
   *
   * The alpha test is used to discard fragments based on their alpha value.
   * Fragments with an alpha value below the specified threshold are discarded,
   * which can be useful for creating transparency effects without blending.
   *
   * @param alpha The alpha threshold value. Fragments with an alpha below this value will be discarded.
   *
   */
  fun alphaTest(alpha: Float)

  /**
   * Configures depth testing for rendering operations.
   *
   * Depth testing determines how fragments are processed based on their depth values
   * to decide visibility in the final rendered scene. This helps create a sense of
   * depth and occlusion between objects.
   *
   * @param depthMask Specifies whether the depth buffer is writable. If set to `true`,
   *                  the depth buffer is updated during rendering; otherwise, it remains
   *                  unchanged.
   * @param depthFunc Specifies the depth comparison function used to compare incoming
   *                  depth values with those already in the depth buffer. Common values
   *                  may include constants for less-than, equal, or greater-than comparisons.
   * @param depthRangeNear The minimum depth value in the depth range, typically normalized
   *                       to a [0, 1] range. This value represents the near clipping plane.
   * @param depthRangeFar The maximum depth value in the depth range, typically normalized
   *                      to a [0, 1] range. This value represents the far clipping plane.
   */
  fun depthTest(depthMask: Boolean, depthFunc: Int, depthRangeNear: Float, depthRangeFar: Float)
}

/**
 * Internal implementation of the `MaterialBuilder` interface.
 *
 * This class provides methods to configure and build a `Material` instance by setting its various attributes,
 * such as diffuse, emissive, normal, ambient, and specular textures or colors, along with additional settings like
 * ambient light, fog color, and face culling mode. Each attribute modifies the appearance or behavior of the material's
 * surface in rendered scenes.
 *
 * Note that this implementation is annotated with `@InternalApi` and is not intended to be used directly outside the library.
 * Instead, use the DSL-based `material` function for constructing materials in a type-safe and user-friendly way.
 *
 * This class maintains a stateful `Material` object that is incrementally configured through its methods and can be
 * finalized by calling the `build` method.
 */
@InternalApi
class MaterialBuilderImpl : MaterialBuilder {
  private val material = Material()

  override fun diffuse(texture: Texture) {
    material.set(TextureAttribute.createDiffuse(texture))
  }

  override fun diffuse(texture: TextureRegion) {
    material.set(TextureAttribute.createDiffuse(texture))
  }

  override fun diffuse(color: Color) {
    material.set(ColorAttribute.createDiffuse(color))
  }

  override fun emissive(texture: Texture) {
    material.set(TextureAttribute.createEmissive(texture))
  }

  override fun emissive(texture: TextureRegion) {
    material.set(TextureAttribute.createEmissive(texture))
  }

  override fun emissive(color: Color) {
    material.set(ColorAttribute.createEmissive(color))
  }

  override fun normal(texture: Texture) {
    material.set(TextureAttribute.createNormal(texture))
  }

  override fun normal(texture: TextureRegion) {
    material.set(TextureAttribute.createNormal(texture))
  }

  override fun ambient(texture: Texture) {
    material.set(TextureAttribute.createAmbient(texture))
  }

  override fun ambient(texture: TextureRegion) {
    material.set(TextureAttribute.createAmbient(texture))
  }

  override fun ambient(color: Color) {
    material.set(ColorAttribute.createAmbient(color))
  }

  override fun specular(texture: Texture) {
    material.set(TextureAttribute.createSpecular(texture))
  }

  override fun specular(texture: TextureRegion) {
    material.set(TextureAttribute.createSpecular(texture))
  }

  override fun specular(color: Color) {
    material.set(ColorAttribute.createSpecular(color))
  }

  override fun ambientLight(ambientLight: Color) {
    material.set(ColorAttribute.createAmbientLight(ambientLight))
  }

  override fun fog(fog: Color) {
    material.set(ColorAttribute.createFog(fog))
  }

  override fun cullFace(cullFace: Int) {
    material.set(IntAttribute.createCullFace(cullFace))
  }

  override fun blendMode(srcFactor: Int, dstFactor: Int) {
    material.set(BlendingAttribute(srcFactor, dstFactor))
  }

  override fun alphaTest(alpha: Float) {
    material.set(FloatAttribute.createAlphaTest(alpha))
  }

  override fun depthTest(depthMask: Boolean, depthFunc: Int, depthRangeNear: Float, depthRangeFar: Float) {
    material.set(DepthTestAttribute(depthFunc, depthRangeNear, depthRangeFar, depthMask))
  }

  fun build(): Material = material
}

/**
 * Creates and configures a new instance of a `Material` using a DSL-style builder pattern.
 *
 * The builder provides methods to define various material properties such as diffuse, emissive,
 * ambient, specular textures or colors, ambient light, fog, and culling face options.
 *
 * This function simplifies the creation of a `Material` by using an inline lambda block.
 * Inside the block, you can call methods on the provided `MaterialBuilder` to configure the material's attributes.
 *
 * @param init A lambda with a receiver that configures the material using the `MaterialBuilder` API.
 * @return A new instance of the configured `Material`.
 */
@OptIn(InternalApi::class)
inline fun material(init: MaterialBuilder.() -> Unit): Material {
  val builder = MaterialBuilderImpl()
  builder.init()
  return builder.build()
}
