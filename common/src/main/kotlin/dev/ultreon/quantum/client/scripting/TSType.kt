package dev.ultreon.quantum.client.scripting

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

data class TSType(
  /**
   * The name of the type
   */
  val name: String,

  /**
   * Where this type comes from
   */
  val from: String? = null,

  /**
   * Which types to import with this type
   */
  val inclusions: List<String> = emptyList(),
) {
  override fun toString(): String = name

  private val nativeProperties: MutableMap<String, TSProperty> = mutableMapOf()
  private val properties: MutableMap<String, TSType> = mutableMapOf()
  private val functions = mutableSetOf<TSFunction>()
  private val importedTypes = mutableSetOf<TSType>()

  fun compile(): String {
    return buildString {
      append("\n\n")

      val content = """
${nativeProperties.values.joinToString("\n") { it.toString() }}

${properties.entries.joinToString("\n") { "${it.key}: ${it.value.name}" }}
${functions.sortedBy { it.toString() }.joinToString("\n\n") { it.toString() }}
""".trim()
      append(
        """
class $name {
${content.prependIndent("  ")}
}
"""
      )
    }
  }

  fun property(name: String, type: TSType, getter: Boolean = false, setter: Boolean = false): TSProperty {
    return TSProperty(name, type, getter, setter).also {
      nativeProperties[name] = it
    }
  }

  fun property(name: String, type: TSType): TSProperty {
    return TSProperty(name, type).also {
      properties[name] = type
    }
  }

  companion object {
    private val cachedTypes: MutableMap<Class<*>, TSType> = mutableMapOf()
    val VOID: TSType = TSType("void")
    val NUMBER: TSType = TSType("number")
    val STRING: TSType = TSType("string")
    val BOOLEAN: TSType = TSType("boolean")
    val ANY: TSType = TSType("any")
    val UNKNOWN: TSType = TSType("unknown")

    fun fromName(name: String): TSType = when (name) {
      "void" -> VOID
      "number" -> NUMBER
      "string" -> STRING
      "boolean" -> BOOLEAN
      "any" -> ANY
      else -> TSType(name)
    }

    fun <T : Any> fromReflectionObject(java: Class<T>): TSType {
      if (java == Void::class.javaPrimitiveType) return VOID
      if (java == Byte::class.javaPrimitiveType) return NUMBER
      if (java == Short::class.javaPrimitiveType) return NUMBER
      if (java == Int::class.javaPrimitiveType) return NUMBER
      if (java == Long::class.javaPrimitiveType) return NUMBER
      if (java == Float::class.javaPrimitiveType) return NUMBER
      if (java == Double::class.javaPrimitiveType) return NUMBER
      if (java == Boolean::class.javaPrimitiveType) return BOOLEAN
      if (java == String::class.java) return STRING

      if (!java.isSubclassOf(TSApi::class.java)) {
        return UNKNOWN
      }

      if (cachedTypes.containsKey(java)) {
        return cachedTypes[java]!!
      }

      var name = java.simpleName
      if (java.kotlin.isCompanion) {
        name = "${java}_KtCompanion"
      }

      return TSType(name).also { type ->
        this.cachedTypes[java] = type

        type.importedTypes += java.fields.map { fromReflectionObject(it.type) }
        type.importedTypes += java.methods.map {
          if (it.returnType == java) {
            type
          } else {
            fromReflectionObject(it.returnType)
          }
        }.toMutableSet()
        type.importedTypes += java.methods.map { method ->
          method.parameterTypes.map {
            if (it == java) {
              type
            } else {
              fromReflectionObject(it)
            }
          }
        }.reduce { acc, list -> acc + list }

        // Add methods and fields
        for (field in java.fields) {
          if (field.type.isSubclassOf(TSApi::class.java)) {
            run back@{
              if (field.isFinal()) {
                type.nativeProperties[field.name] =
                  TSProperty(
                    field.name,
                    if (field.type == java) type else fromReflectionObject(field.type).also { if (it == UNKNOWN) return@back },
                    getter = true,
                    setter = false
                  )
              } else {
                type.nativeProperties[field.name] = TSProperty(
                  field.name,
                  if (field.type == java) type else fromReflectionObject(field.type).also { if (it == UNKNOWN) return@back },
                  getter = true,
                  setter = true
                )
              }
            }
          }
        }

        for (method in java.methods) {
          if (method.name.matches(Regex("[a-zA-Z][a-zA-Z0-9]*"))) {
            if (method.name in listOf("wait", "notify", "notifyAll")) {
              continue
            }
            run back@{
              type.functions += TSFunction(
                method.name,
                TSParams(method.parameterTypes.mapIndexed { index, it ->
                  if (it.isSubclassOf(TSApi::class.java)) {
                    "arg$index" to fromReflectionObject(it).also {
                      if (it == UNKNOWN) {
                        return@back
                      }
                    }
                  } else {
                    return@back
                  }
                }),
                fromReflectionObject(method.returnType).also {
                  if (it == UNKNOWN) {
                    return@back
                  }
                },
                static = method.isStatic(),
                method = true
              )
            }
          }
        }

        for (field in java.declaredClasses) {
          if (field.isSubclassOf(TSApi::class.java)) {
            type.importedTypes += fromReflectionObject(field)
          }
        }
      }
    }

    fun <T : Any> fromReflectionClass(java: Class<T>): TSType {
      if (java == Void::class.javaPrimitiveType) return VOID
      if (java == Byte::class.javaPrimitiveType) return NUMBER
      if (java == Short::class.javaPrimitiveType) return NUMBER
      if (java == Int::class.javaPrimitiveType) return NUMBER
      if (java == Long::class.javaPrimitiveType) return NUMBER
      if (java == Float::class.javaPrimitiveType) return NUMBER
      if (java == Double::class.javaPrimitiveType) return NUMBER
      if (java == Boolean::class.javaPrimitiveType) return BOOLEAN
      if (java == String::class.java) return STRING

      if (cachedTypes.containsKey(java)) {
        return cachedTypes[java]!!
      }

      var name = java.simpleName
      if (java.kotlin.isCompanion) {
        name = "${name}_KtCompanion"
      }

      return TSType(name).also { type ->
        cachedTypes[java] = type
        type.importedTypes += java.fields.map { fromReflectionObject(it.type) }
        type.importedTypes += java.methods.map {
          if (it.returnType == java) {
            type
          } else {
            fromReflectionObject(it.returnType)
          }
        }
        type.importedTypes += java.methods.map { method ->
          method.parameterTypes.map {
            if (it == java) {
              type
            } else {
              fromReflectionObject(it)
            }
          }
        }.let {
          if (it.isEmpty()) {
            emptyList()
          } else {
            it.reduce { acc, list -> acc + list }
          }
        }

        type.importedTypes += java.declaredFields.map {
          if (it.type == java) {
            type
          } else {
            fromReflectionObject(it.type)
          }
        }
        type.importedTypes += java.declaredMethods.map {
          if (it.returnType == java) {
            type
          } else {
            fromReflectionObject(it.returnType)
          }
        }
        type.importedTypes += java.declaredMethods.map { method ->
          method.parameterTypes.map {
            if (it == java) {
              type
            } else {
              fromReflectionObject(it)
            }
          }
        }.let {
          if (it.isEmpty()) {
            emptyList()
          } else {
            it.reduce { acc, list -> acc + list }
          }
        }

        // Add methods and fields
        for (field in java.fields) {
          if (field.type.isSubclassOf(TSApi::class.java)) {
            run back@{
              if (field.isFinal()) {
                type.nativeProperties[field.name] = TSProperty(
                  field.name,
                  fromReflectionObject(field.type).also { if (it == UNKNOWN) return@back },
                  getter = true,
                  setter = false
                )
              } else {
                type.nativeProperties[field.name] = TSProperty(
                  field.name,
                  fromReflectionObject(field.type).also { if (it == UNKNOWN) return@back },
                  getter = true,
                  setter = true
                )
              }
            }
          }
        }
        for (method in java.methods) {
          if (method.name.matches(Regex("[a-zA-Z][a-zA-Z0-9]*"))) {
            if (method.name in listOf("wait", "notify", "notifyAll")) {
              continue
            }
            run back@{
              type.functions += TSFunction(
                method.name,
                TSParams(method.parameterTypes.mapIndexed { index, it -> "arg$index" to fromReflectionObject(it).also {
                  if (it == UNKNOWN) {
                    return@back
                  }
                }}),
                fromReflectionObject(method.returnType).also {
                  if (it == UNKNOWN) {
                    return@back
                  }
                },
                static = method.isStatic(),
                method = true
              )
            }
          }
        }

        for (field in java.declaredFields) {
          if (field.type.isSubclassOf(TSApi::class.java)) {
            run back@{
              if (field.isFinal()) {
                type.nativeProperties[field.name] =
                  TSProperty(
                    field.name,
                    if (field.type == java) type else fromReflectionObject(field.type).also { if (it == UNKNOWN) return@back },
                    setter = false,
                    static = field.isStatic()
                  )
              } else {
                type.nativeProperties[field.name] =
                  TSProperty(
                    field.name,
                    if (field.type == java) type else fromReflectionObject(field.type).also { if (it == UNKNOWN) return@back },
                    static = field.isStatic()
                  )
              }
            }
          }
        }
        for (method in java.declaredMethods)
          if (method.name.matches(Regex("[a-zA-Z][a-zA-Z0-9]*"))) {
            if (method.name in listOf("wait", "notify", "notifyAll")) {
              continue
            }
            run back@{
              type.functions += TSFunction(
                method.name,
                TSParams(method.parameterTypes.mapIndexed { index, it ->
                  "arg$index" to if (it == java) type else fromReflectionObject(it).also {
                    if (it == UNKNOWN) {
                      return@back
                    }
                  }
                }),
                if (method.returnType == java) type else fromReflectionObject(method.returnType).also {
                  if (it == UNKNOWN) {
                    return@back
                  }
                },
                static = method.isStatic(),
                method = true
              )
            }
          }

        for (field in java.declaredClasses) {
          if (field.isSubclassOf(TSApi::class.java)) {
            type.importedTypes += fromReflectionObject(field)
          }
        }
      }
    }

    private fun fromReflectionParams(parameterTypes: Array<Class<*>>?): TSParams {
      return TSParams(parameterTypes?.mapIndexed { index, it ->
        if (it.isSubclassOf(TSApi::class.java)) {
          "arg$index" to fromReflectionObject(it)
        } else {
          "arg$index" to UNKNOWN
        }
      } ?: emptyList())
    }
  }
}

private fun <T> Class<T>.isSubclassOf(java: Class<*>): Boolean {
  return java.isAssignableFrom(this) && this != java
}

private fun Field.isFinal(): Boolean {
  return (modifiers and Modifier.FINAL) != 0
}

private fun Field.isStatic(): Boolean {
  return (modifiers and Modifier.STATIC) != 0
}

private fun Method.isStatic(): Boolean {
  return (modifiers and Modifier.STATIC) != 0
}
