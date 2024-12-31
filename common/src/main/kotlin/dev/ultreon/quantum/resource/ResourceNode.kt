package dev.ultreon.quantum.resource

interface ResourceNode {
  fun isCategory(): Boolean
  fun isResource(): Boolean = !isCategory()
}
