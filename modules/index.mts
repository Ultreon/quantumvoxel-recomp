// noinspection JSUnusedGlobalSymbols

import Gdx from "@ultreon/quantumjs/gdx/Gdx.mjs";
import {Texture} from "./texture.mjs";
import {SpriteBatch, ModelBatch} from "./batching.mjs";
import {GL20} from "./gl.mjs";

let spriteBatch: SpriteBatch
let texture: Texture

let lastRenderTime = 0

export default function () {
  console.info("Hello from Typescript!");
}

export function create() {
  spriteBatch = new SpriteBatch(); // Store in registry
  texture = new Texture("logo.png"); // Store in registry

  let modelBatch = new ModelBatch();
}

export function render() {
  if (lastRenderTime + 1000 < Date.now()) {
    lastRenderTime = Date.now()
    Gdx.graphics.setTitle(`FPS: ${Gdx.graphics.getFramesPerSecond()}`)
  }

  GL20.glClearColor(0.5, 0.5, 0.5, 1.0);
  GL20.glClear(GL20.GL_COLOR_BUFFER_BIT);

  spriteBatch.begin()
  spriteBatch.draw(texture, 100, 100)
  spriteBatch.end()
}

export function dispose() {
  console.info("Disposing")
  texture.dispose()
  spriteBatch.dispose()
}

export function pause() {

}

export function resume() {

}

export function resize(width: number, height: number) {
  console.info(`Resized to ${width} x ${height}`)
}
