package dev.ultreon.quantum.client.gui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import dev.ultreon.quantum.client.QuantumVoxel
import dev.ultreon.quantum.util.NamespaceID

fun SpriteBatch.draw(id: NamespaceID, x: Float, y: Float) {
  draw(QuantumVoxel.textureManager[id], x, y)
}

fun SpriteBatch.draw(id: NamespaceID, x: Float, y: Float, width: Float, height: Float) {
  draw(QuantumVoxel.textureManager[id], x, y, width, height)
}

fun SpriteBatch.draw(id: NamespaceID, x: Float, y: Float, originX: Float, originY: Float, width: Float, height: Float) {
  draw(QuantumVoxel.textureManager[id], x, y, originX, originY, width, height, 1f, 1f, 0f)
}

fun SpriteBatch.draw(id: NamespaceID, x: Float, y: Float, originX: Float, originY: Float, width: Float, height: Float, scaleX: Float, scaleY: Float, rotation: Float) {
  draw(QuantumVoxel.textureManager[id], x, y, originX, originY, width, height, scaleX, scaleY, rotation)
}
