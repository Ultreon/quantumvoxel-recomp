package dev.ultreon.quantum.scripting.function

import com.badlogic.gdx.math.GridPoint3
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.utils.JsonValue
import dev.ultreon.quantum.blocks.Block
import dev.ultreon.quantum.blocks.BlockEntity
import dev.ultreon.quantum.blocks.BlockState
import dev.ultreon.quantum.entity.*
import dev.ultreon.quantum.item.Item
import dev.ultreon.quantum.logger
import dev.ultreon.quantum.math.Axis
import dev.ultreon.quantum.math.Vector3D
import dev.ultreon.quantum.registry.Registries
import dev.ultreon.quantum.scripting.condition.VirtualCondition
import dev.ultreon.quantum.util.Direction
import dev.ultreon.quantum.util.NamespaceID
import dev.ultreon.quantum.util.asIdOrNull
import dev.ultreon.quantum.world.Dimension
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.reflect.KClass
