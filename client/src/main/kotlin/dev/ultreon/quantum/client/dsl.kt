package dev.ultreon.quantum.client

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import dev.ultreon.quantum.InternalApi

@DslMarker
annotation class MaterialDsl

@MaterialDsl
interface MaterialBuilder {
  fun diffuse(texture: Texture)
  fun diffuse(texture: TextureRegion)
  fun diffuse(color: Color)
  fun emissive(texture: Texture)
  fun emissive(texture: TextureRegion)
  fun emissive(color: Color)
  fun normal(texture: Texture)
  fun normal(texture: TextureRegion)
  fun ambient(texture: Texture)
  fun ambient(texture: TextureRegion)
  fun ambient(color: Color)
  fun specular(texture: Texture)
  fun specular(texture: TextureRegion)
  fun specular(color: Color)
  fun ambientLight(ambientLight: Color)
  fun fog(fog: Color)

  fun cullFace(cullFace: Int)

  @InternalApi
  fun build(): Material
}

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

  override fun build(): Material = material
}

@OptIn(InternalApi::class)
inline fun material(init: MaterialBuilder.() -> Unit): Material {
  val builder = MaterialBuilderImpl()
  builder.init()
  return builder.build()
}
