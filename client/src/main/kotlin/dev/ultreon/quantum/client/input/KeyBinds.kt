package dev.ultreon.quantum.client.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys

object KeyBinds {
  val walkForwardsKey = KeyBind("walkForwardsKey", Keys.W)
  val walkBackwardsKey = KeyBind("walkBackwardsKey", Keys.S)
  val walkLeftKey = KeyBind("walkLeftKey", Keys.A)
  val walkRightKey = KeyBind("walkRightKey", Keys.D)
  val jumpKey = KeyBind("jumpKey", Keys.SPACE)
  val crouchKey = KeyBind("crouchKey", Keys.SHIFT_LEFT)
  val runningKey = KeyBind("runningKey", Keys.ALT_LEFT)
}

class KeyBind(val name: String, var key: Int) {
  fun isPressed() = Gdx.input.isKeyPressed(key)
  fun isReleased() = !Gdx.input.isKeyPressed(key)
  fun isJustPressed() = Gdx.input.isKeyJustPressed(key)
}
