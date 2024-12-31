import Gdx from "@ultreon/quantumjs/gdx/Gdx.mjs";

export function local(path: string) {
  return Gdx.files.local(path)
}

export function internal(path: string) {
  return Gdx.files.internal(path)
}

export function external(path: string) {
  return Gdx.files.external(path)
}

export function absolute(path: string) {
  return Gdx.files.absolute(path)
}
