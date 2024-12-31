package dev.ultreon.quantum.client.model

import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.math.Vector3

interface ItemModel {
  fun bake(): Model
}
