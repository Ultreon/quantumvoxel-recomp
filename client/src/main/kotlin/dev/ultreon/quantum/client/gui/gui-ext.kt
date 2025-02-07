package dev.ultreon.quantum.client.gui

import com.badlogic.gdx.graphics.g2d.Batch
import dev.ultreon.quantum.client.quantum
import dev.ultreon.quantum.util.NamespaceID

fun Batch.draw(id: NamespaceID, x: Float, y: Float) {
  draw(quantum.textureManager[id], x, y)
}

fun Batch.draw(id: NamespaceID, x: Float, y: Float, width: Float, height: Float) {
  draw(quantum.textureManager[id], x, y, width, height)
}

fun Batch.draw(id: NamespaceID, x: Float, y: Float, width: Float, height: Float, u: Float = 0F, v: Float = 0F, uSize: Float = width, vSize: Float = height, texWidth: Float = 256F, texHeight: Float = 256F) {
  val textureRegion = quantum.textureManager[id]
  draw(textureRegion, x, y, (textureRegion.regionX.toFloat() / textureRegion.regionWidth) + (u / texWidth), (textureRegion.regionY.toFloat() / textureRegion.regionHeight) + (v / texHeight), width, height, uSize / texWidth, vSize / texHeight, 0F)
}
