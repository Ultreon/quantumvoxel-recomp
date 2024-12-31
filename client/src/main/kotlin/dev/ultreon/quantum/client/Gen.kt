package dev.ultreon.quantum.client//@file:Suppress("t")
//
//package dev.ultreon.quantum
//
//import com.badlogic.gdx.ApplicationLogger
//import com.badlogic.gdx.Gdx
//import com.badlogic.gdx.graphics.Texture
//import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
//import com.badlogic.gdx.graphics.g2d.SpriteBatch
//import com.badlogic.gdx.utils.Disposable
//import com.caoccao.javet.interfaces.IJavetLogger
//import com.caoccao.javet.interop.V8Runtime
//import com.caoccao.javet.interop.callback.IJavetDirectCallable
//import com.caoccao.javet.interop.callback.JavetCallbackContext
//import com.caoccao.javet.interop.callback.JavetCallbackType
//import com.caoccao.javet.javenode.JNEventLoop
//import com.caoccao.javet.javenode.enums.JNModuleType
//import com.caoccao.javet.values.V8Value
//import com.caoccao.javet.values.primitive.*
//import com.caoccao.javet.values.reference.V8Module
//import com.caoccao.javet.values.reference.V8ValueObject
//import com.caoccao.javet.values.reference.V8ValueProxy
//import ktx.app.KtxApplicationAdapter
//import ktx.app.KtxScreen
//import ktx.app.clearScreen
//import ktx.assets.disposeSafely
//import ktx.assets.toInternalFile
//import ktx.graphics.use
//import java.io.FileNotFoundException
//import java.lang.reflect.Constructor
//import java.lang.reflect.Field
//import java.lang.reflect.Method
//import java.lang.reflect.Modifier
//import java.util.*
//import kotlin.collections.HashMap
//import kotlin.io.path.Path
//import kotlin.system.exitProcess
//
//val timer = Timer()
//
//
//class QuantumVoxel(val v8Runtime: V8Runtime, val eventLoop: JNEventLoop) : KtxApplicationAdapter {
//  private val instanceHandles: MutableMap<Long, Any> = HashMap()
//  private var compiled: V8Module
//  val logger = LoggerFactory["Quantum"]
//
//  init {
//    try {
//      compiled = extracted()
//    } catch (e: Exception) {
//      logger.error("Failed to start Quantum Voxel")
//      logger.error(e.stackTraceToString())
//      exitProcess(1)
//    }
//  }
//
//  override fun create() {
//    try {
//      logger.info("Starting up Quantum Voxel")
//
//      Gdx.app.applicationLogger = object : ApplicationLogger {
//        override fun log(p0: String?, p1: String?) {
//          factory.getLogger(p0!!).debug(p1!!)
//        }
//
//        override fun log(p0: String?, p1: String?, p2: Throwable?) {
//          factory.getLogger(p0!!).error(p1!!)
//          for (e in p2!!.stackTraceToString().lines()) {
//            factory.getLogger(p0).error(e.replace("\t", "    "))
//          }
//        }
//
//        override fun error(p0: String?, p1: String?) {
//          factory.getLogger(p0!!).error(p1!!)
//        }
//
//        override fun error(p0: String?, p1: String?, p2: Throwable?) {
//          factory.getLogger(p0!!).error(p1!!)
//          for (e in p2!!.stackTraceToString().lines()) {
//            factory.getLogger(p0).error(e.replace("\t", "    "))
//          }
//        }
//
//        override fun debug(p0: String?, p1: String?) {
//          factory.getLogger(p0!!).debug(p1!!)
//        }
//
//        override fun debug(p0: String?, p1: String?, p2: Throwable?) {
//          factory.getLogger(p0!!).debug(p1!!)
//          for (e in p2!!.stackTraceToString().lines()) {
//            factory.getLogger(p0).debug(e.replace("\t", "    "))
//          }
//        }
//      }
//
//    } catch (e: Exception) {
//      val logger = LoggerFactory["Quantum"]
//      logger.error("Failed to start Quantum Voxel")
//      for (element in e.stackTraceToString().lines()) {
//        logger.error(element.replace("\t", "    "))
//      }
//
//      exitProcess(1)
//    }
//
//    invoke("create")
//  }
//
//  override fun render() {
//    invoke("render")
//  }
//
//  override fun dispose() {
//    invoke("dispose")
//  }
//
//  override fun pause() {
//    invoke("pause")
//  }
//
//  override fun resume() {
//    invoke("resume")
//  }
//
//  private fun invoke(name: String) {
//    (compiled.namespace as V8ValueObject).invokeVoid(name, *arrayOf())
//    instanceHandles.clear()
//  }
//
//  override fun resize(width: Int, height: Int) {
//    val s = "resize"
//    (compiled.namespace as V8ValueObject).invokeVoid(s, *arrayOf(width, height))
//  }
//
//  private fun extracted(): V8Module {
//    v8Runtime.logger = object : IJavetLogger {
//      val logger = LoggerFactory["<js>"]
//
//      override fun debug(message: String?) {
//      }
//
//      override fun error(message: String?) {
//        logger.error(message ?: "")
//      }
//
//      override fun error(message: String?, cause: Throwable?) {
//        logger.error(message ?: "")
//        for (e in cause!!.stackTraceToString().lines()) {
//          logger.error(e.replace("\t", "    "))
//        }
//      }
//
//      override fun info(message: String?) {
//      }
//
//      override fun warn(message: String?) {
//        logger.warn(message ?: "")
//      }
//    }
//
//    val packageMap = mutableMapOf(
//      "de.marhali.json5" to "json5",
//      "com.crashinvaders.vfx" to "vfx",
//      "dev.ultreon.mixinprovider" to "mixinprovider",
//      "org.lwjgl.egl" to "egl",
//      "org.lwjgl.glfw" to "glfw",
//      "org.lwjgl.opencl" to "opencl",
//      "org.lwjgl.openal" to "openal",
//      "org.lwjgl.opengl" to "opengl",
//      "org.lwjgl.nanovg" to "nanovg",
//      "org.lwjgl.nuklear" to "nuklear",
//      "org.lwjgl.stb" to "glstb",
//      "org.lwjgl.util" to "glutil",
//      "org.lwjgl.vulkan" to "vulkan",
//      "org.lwjgl.assimp" to "assimp",
//      "org.lwjgl" to "gl",
//
//      "com.apple" to "apple",
//      "com.fasterxml" to "fasterxml",
//      "com.github.tommyettinger.textra" to "textra",
//      "com.jagrosh.discordipc" to "discordipc",
//      "com.jcraft" to "jcraft",
//      "com.kotcrab.vis.ui" to "vis_ui",
//      "com.mojang.serialization" to "moj_serialization",
//      "com.oracle.js" to "oraclejs",
//      "com.oracle" to "oracle",
//      "com.sun" to "sun",
//      "io.github.libsdl4j" to "sdl",
//      "java.rmi" to "jrmi",
//      "javagames.util" to "javagames_util",
//      "net.bytebuddy" to "bytebuddy",
//      "net.miginfocom" to "miginfocom",
//      "org.apache.arrow" to "apache_arrow",
//      "org.ietf.jgss" to "ietf_jgss",
//      "org.jcp.xml.dsig.internal" to "xml_dsig",
//      "org.objenesis" to "objenesis",
//      "oxbow.swingbits" to "swingbits",
//      "svm.core.annotate" to "svm_annotate",
//      "text.formic" to "formic",
//      "de.damios.guacamole" to "guacamole",
//
//      "jdk.dynalink" to "dynalink",
//      "jdk.editpad" to "editpad",
//      "jdk.graal" to "graal",
//      "jdk.javadoc" to "javadoc",
//      "jdk.jpackage" to "jpackage",
//      "jdk.management" to "_management",
//      "jdk.net" to "_net",
//      "jdk.nio" to "_nio",
//      "jdk.nio.zipfs" to "_zipfs",
//      "jdk.random" to "_random",
//      "jdk.security" to "_security",
//      "jdk.swing" to "_swing",
//      "jdk.tools" to "_tools",
//      "jdk.xml" to "_xml",
//      "dev.ultreon.langgen" to "lang_gen",
//      "com.google.errorprone" to "gerrorprone",
//      "com.google.flatbuffers" to "gflatbuffers",
//      "com.google.thirdparty" to "gthrirdparty",
//      "com.google.gson" to "gson",
//      "com.google.protobuf" to "gprotobuf",
//      "com.google.common.collect" to "gcollect",
//      "com.google.common" to "gcommon",
//
//      "space.earlygrey.shapedrawer" to "shapedrawer",
//
//      "org.slf4j" to "slf4js",
//
//      "com.badlogic.gdx" to "gdx",
//      "com.badlogic.ashley" to "gdx-ashley",
//
//      "jdk.jshell" to "jshell",
//      "jdk.vm" to "jvm",
//      "jdk.jfr" to "jfr",
//      "jdk.internal" to "jdk_internal",
//      "joptsimple" to "optionsimple",
//      "libnoiseforjava" to "libnoise",
//      "jline" to "jline",
//      "javassist" to "assistjs",
//      "javazoom" to "zoomjs",
//      "net.java.games.input" to "inputjs",
//      "net.java.games" to "javagames",
//      "net.mgsx.gltf" to "gltf",
//      "net.miginfoccom" to "miginfoccom",
//      "net.java.jogl" to "jogl",
//      "net.java.jinput" to "jinput",
//      "org.apache.groovy" to "groovy._impl",
//      "org.apache.logging.slf4j" to "log4js.compat.slf4js",
//      "org.apache.logging.log4j" to "log4js",
//      "org.bouncycastle" to "bouncy",
//
//      "org.checkerframework" to "checkerjs",
//      "org.codehaus.groovy" to "groovyjs._codehaus",
//      "org.graalvm" to "graalvmjs",
//      "org.intellij" to "intellij",
//      "org.jetbrains" to "jetbrains",
//      "org.json" to "jsonjs",
//      "org.jspecify" to "jspecify",
//      "org.mozilla.classfile" to "moz_classfile",
//      "org.mozilla.javascript" to "js",
//      "org.objectweb.asm" to "jasm",
//      "org.oxbow" to "oxbow",
//      "org.reactivestreams" to "reactivejs",
//      "org.reflections" to "reflections",
//      "org.spongepowered.asm.mixin" to "mixin",
//      "org.spongepowered.asm" to "sponge_asm",
//      "org.spongepowered" to "sponge",
//      "org.tukaani.xz" to "xzjs",
//      "org.w3c" to "w3c",
//      "org.xml" to "xmljs",
//
//      "org.slf4j" to "slf4js",
//
//      "jna" to "jna",
//
//      "java.applet" to "japplet",
//      "java.beans" to "jbeans",
//      "java.lang" to "jlang",
//      "java.util" to "jutil",
//      "java.io" to "jio",
//      "java.nio" to "jnio",
//      "java.awt" to "jawt",
//      "java.net" to "jnet",
//      "java.security" to "jsec",
//      "java.text" to "jtext",
//      "java.time" to "jtime",
//      "java.managment" to "jman",
//      "java.math" to "jmath",
//      "java.sql" to "jsql",
//      "javax.xml" to "jxml",
//      "javax.imageio" to "jximageio",
//      "javax.sound" to "jxsound",
//      "javax.crypto" to "jxcrypto",
//      "javax.net" to "jxnet",
//
//      "kotlin" to "kotlin",
//      "kotlinx" to "kotlinx",
//
//      "org.joml" to "joml",
//
//      "org.apache.commons" to "commonsjs",
//      "org.apache.logging.log4j" to "log4js",
//
//      "dev.ultreon.libs" to "corelibs",
//      "dev.ultreon.data" to "ultreon_data",
//      "dev.ultreon.ubo" to "ubo",
//      "dev.ultreon.xeox.loader" to "xeox",
//      "dev.ultreon.quantumjs" to "game._internal",
//      "dev.ultreon.quantum" to "game",
//
//      "net.fabricmc.api" to "fabric_api",
//      "net.fabricmc.impl" to "fabric_impl",
//      "net.fabricmc.loader" to "fabric_loader",
//      "net.fabricmc" to "fabricmc",
//
//      "net.minecraft" to "minecraft",
//
//      "com.mojang.datafixers" to "mojang.datafixers",
//      "com.mojang.brigadier" to "brigadier",
//      "com.mojang.text2speech" to "mojang.text2speech",
//      "com.mojang.authlib" to "mojang.authlib",
//      "com.mojang.logging" to "mojang.logging",
//
//      "com.ultreon" to "ultreon._internal",
//      "dev.ultreon.quantumjs.wrap" to "quantumjs.wrap",
//
//      "scala" to "scalajs",
//
//      "clojure" to "clojure",
//
//      "imgui" to "imgui",
//
//      "groovy" to "groovy",
//      "groovyjarjarantlr" to "groovy._antlr",
//      "groovyjarjarantlr4" to "groovy._antlr4",
//      "groovyjarjarasm" to "groovy._asm",
//      "groovyjarjarpicocli" to "groovy._picocli",
//
//      "junit" to "junit",
//
//      "io.javalin" to "linjs",
//
//      "io.github.classgraph" to "classgraph",
//
//      "it.unimi.dsi.fastutil" to "fastutil",
//
//      "com.crashinvaders.jfx" to "fxjs",
//
//      "com.flowpowered.noise" to "flownoise",
//
//      "com.formdev.flatlaf" to "flatlaf",
//
//      "com.jcraft.jorbis" to "orbisjs",
//      "com.jcraft.jzlib" to "zlibjs",
//
//      "com.oracle.graal" to "graal",
//      "com.oracle.svm" to "svm",
//      "com.oracle.truffle" to "truffle",
//
//      "com.raylabz.opensimplex" to "opensimplex",
//
//      "com.studiohartman.jamepad" to "jamepad",
//
//      "com.sun.jna" to "native._jna",
//      "sun.jna.platform" to "native",
//      "sun.jna" to "native._internal",
//      "sun.misc" to "jmisc._impl",
//      "sun.nio" to "jnio._impl",
//      "sun.security" to "jsec._impl",
//      "sun.util" to "jutil._impl",
//      "sun.awt" to "jawt._impl",
//      "sun.net" to "jnet._impl",
//      "sun.text" to "jtext._impl",
//      "sun.management" to "jman._impl",
//      "sun.math" to "jmath._impl",
//      "sun.sql" to "jsql._impl",
//      "sun.xml" to "jxml._impl",
//      "sun.imageio" to "jximageio._impl",
//      "sun.sound" to "jxsound._impl",
//      "sun.crypto" to "jxcrypto._impl",
//
//      "com.jme3" to "jme3",
//
//      "io.netty" to "netty",
//
//      "io.github.ultreon.data" to "ultreon_data",
//      "io.github.ultreon.ubo" to "ubo",
//      "io.github.ultreon.xeox" to "xeox",
//      "io.github.ultreon.corelibs" to "corelibs",
//      "io.github.ultreon.libs" to "libs",
//      "io.github.xypercode.mods" to "xyper_mods",
//      "io.github.ultreon.quantumjs" to "quantumjs._old",
//      "dev.ultreon.gameprovider" to "quantumjs._gameprovider",
//      "de.articdive.jnoise" to "noisejs",
//      "de.articdive.marhali" to "marhali",
//
//      "org.owasp.encoder" to "owaspjs",
//    )
//
//    val moduleMap = packageMap.map { (key, value) ->
//      value to key
//    }.toMap()
//
//    v8Runtime.setV8ModuleResolver { v8Runtime, resourceName, v8ModuleReferrer ->
//      val modulePath = Path("modules").resolve(resourceName).toAbsolutePath()
//      if (resourceName.startsWith("@ultreon/quantum/")) {
//        return@setV8ModuleResolver v8Runtime.createV8Module(resourceName, v8Runtime.createV8ValueProxy())
//      }
//      if (resourceName.startsWith("@ultreon/quantumjs/") && resourceName.endsWith(".mjs")) {
//        resourceName.substring("@ultreon/quantumjs/".length..(resourceName.length - 5)).split("/", limit = 2).let {
//          if (it.size == 2) {
//            val packageName = it[0]
//            val className = it[1]
//            if (moduleMap.containsKey(packageName)) {
//              if (!resourceName.endsWith(".mjs")) {
//                throw Exception("Only .mjs files are supported")
//              }
//              val javaClass = Class.forName(moduleMap[packageName] + "." + className.replace('/', '.'))
//              val namespace = v8Runtime.createV8ValueProxy()
//              namespace.set(
//                "default", v8Runtime.createV8ValueFunction(
//                  JavetCallbackContext(
//                    // Use Javascript function invocation property name
//                    "new", JavetCallbackType.DirectCallNoThisAndResult,
//                    IJavetDirectCallable.NoThisAndResult<Exception> { args ->
//                      println("javaClass = $javaClass")
//                      println("args = $args")
//                      val constructor = determineConstructor(javaClass, args ?: emptyArray())
//                      val instance = constructor.newInstance(*(args?.map {
//                        mapArgument(it, constructor.parameterTypes[0], v8Runtime)
//                      }?.toTypedArray() ?: emptyArray()))
//                      createObject(v8Runtime, instance)
//                    }
//                  )).also {
//                  mapClass(javaClass, it, v8Runtime)
//                })
//              return@setV8ModuleResolver v8Runtime.createV8Module("$resourceName", namespace)
//            }
//          }
//        }
//      }
//      if (modulePath.toFile().exists()) {
//        return@setV8ModuleResolver v8Runtime.getExecutor(modulePath).compileV8Module()
//      }
//      throw FileNotFoundException(modulePath.toString())
//    }
//    eventLoop.loadStaticModules(JNModuleType.Console)
//    eventLoop.registerDynamicModules(JNModuleType.TimersPromises)
//    val module = v8Runtime.getExecutor(Path("modules/index.mjs").toAbsolutePath()).setModule(true)
//    val compiled = module.compileV8Module()
//    compiled.execute<V8Value>(false)
//    val namespace = compiled.namespace
//    if (namespace is V8ValueObject) {
//      if (namespace.has("default")) {
//        namespace.invokeVoid("default", *arrayOf())
//      }
//      return compiled
//    } else {
//      throw Exception("Namespace is not an object: ${namespace.javaClass.name}")
//    }
//  }
//
//  private fun mapClass(
//    javaClass: Class<*>,
//    it: V8ValueObject,
//    v8Runtime: V8Runtime,
//  ) {
//    for (field in javaClass.declaredFields) {
//      if (!Modifier.isStatic(field.modifiers) || !Modifier.isPublic(field.modifiers)) {
//        continue
//      }
//      mapField(it, field, v8Runtime, javaClass, null)
//    }
//
//    for (method in javaClass.declaredMethods) {
//      if (!Modifier.isStatic(method.modifiers) || !Modifier.isPublic(method.modifiers)) {
//        continue
//      }
////      if (method.name.startsWith("get")) {
////        if (method.returnType != Void.TYPE) {
////          mapGetterSetterMethod(it, method, v8Runtime, javaClass, null)
////        } else {
////          mapMethod(it, method, v8Runtime, null)
////        }
////      } else {
//        mapMethod(it, method, v8Runtime, null)
////      }
//    }
//  }
//
//  private fun determineConstructor(javaClass: Class<*>, args: Array<V8Value>): Constructor<*> {
//    c@ for (constructor in javaClass.constructors) {
//      if (args.isEmpty() && constructor.parameterCount == 0) {
//        return constructor
//      }
//      for ((i, param) in constructor.parameterTypes.withIndex()) {
//        if (constructor.parameterTypes.size != args.size) {
//          continue@c
//        }
//        val arg = args[i]
//        if (arg is V8ValueObject) {
//          val jhandle = arg.get<V8ValueLong>("__jhandle__")?.value
//          if (jhandle == null) {
//            logger.warn("__jhandle__ not present!")
//            continue@c
//          } else if (jhandle in instanceHandles) {
//            logger.warn("Object not found in instances: ${jhandle}")
//            if (!param.isInstance(instanceHandles[jhandle])) {
//              continue@c
//            }
//          } else {
//            if (param != V8ValueObject::class.java) {
//              logger.warn("Not a V8ValueObject: ${jhandle}")
//              continue@c
//            }
//          }
//        } else if (arg is V8ValueString) {
//          if (param != String::class.java) {
//            continue@c
//          }
//        } else if (arg is V8ValueBoolean) {
//          if (param != Boolean::class.java) {
//            continue@c
//          }
//        } else if (arg is V8ValueInteger) {
//          if (param != Int::class.java && param != Long::class.java && param != Short::class.java && param != Byte::class.java && param != Float::class.java && param != Double::class.java) {
//            continue@c
//          }
//        } else if (arg is V8ValueLong) {
//          if (param != Long::class.java && param != Int::class.java && param != Short::class.java && param != Byte::class.java && param != Float::class.java && param != Double::class.java) {
//            continue@c
//          }
//        } else if (arg is V8ValueDouble) {
//          if (param != Double::class.java && param != Float::class.java && param != Int::class.java && param != Long::class.java && param != Short::class.java && param != Byte::class.java) {
//            continue@c
//          }
//        } else {
//          throw Exception("Unsupported argument type: ${arg.javaClass.name}")
//        }
//
//        return constructor
//      }
//    }
//    throw IllegalArgumentException("Constructor not found: ${javaClass.name}(${args.joinToString(", ") { it.javaClass.name }})")
//  }
//
//  private fun mapField(
//    namespace: V8ValueObject,
//    field: Field,
//    v8Runtime: V8Runtime,
//    javaClass: Class<*>,
//    instance: Any?,
//  ) {
//    if (Modifier.isFinal(field.modifiers)) {
//      namespace.bindProperty(
//        JavetCallbackContext(
//          field.name, JavetCallbackType.DirectCallGetterAndThis,
//          IJavetDirectCallable.GetterAndThis<Exception> { _ ->
//            createObject(v8Runtime, javaClass.getDeclaredField(field.name).get(instance))
//          }),
//      )
//    } else {
//      namespace.bindProperty(
//        JavetCallbackContext(
//          field.name, JavetCallbackType.DirectCallGetterAndThis,
//          IJavetDirectCallable.GetterAndThis<Exception> { _ ->
//            createObject(v8Runtime, javaClass.getDeclaredField(field.name).get(instance))
//          }),
//        JavetCallbackContext(
//          field.name, JavetCallbackType.DirectCallSetterAndThis,
//          IJavetDirectCallable.SetterAndThis<Exception> { _, value ->
//            javaClass.getDeclaredField(field.name).set(instance, value)
//            value
//          }),
//      )
//    }
//  }
//
//  private fun mapMethod(
//    namespace: V8ValueObject,
//    method: Method,
//    v8Runtime: V8Runtime,
//    instance: Any?,
//    javaClass: Class<*> = method.declaringClass
//  ) {
//    if (method.returnType != Void.TYPE) {
//      namespace.bindFunction(
//        JavetCallbackContext(
//          method.name,
//          JavetCallbackType.DirectCallThisAndResult,
//          IJavetDirectCallable.ThisAndResult<Exception> { _, args ->
//            createObject(
//              v8Runtime,
//              determineMethod(javaClass, instance, method.name, args ?: emptyArray()).let {
//                it.invoke(
//                  instance,
//                  *(args ?: emptyArray()).mapIndexed<V8Value, Any?> { index, arg ->
//                    mapArgument(arg, it.parameterTypes[index], v8Runtime)
//                  }.toTypedArray().also { args ->
//                  }
//                )
//              }
//            )
//          })
//      )
//    } else {
//      namespace.bindFunction(
//        JavetCallbackContext(
//          method.name,
//          JavetCallbackType.DirectCallThisAndResult,
//          IJavetDirectCallable.ThisAndResult<Exception> { _, args ->
//            determineMethod(javaClass, instance, method.name, args ?: emptyArray()).let {
//              it.isAccessible = true
//              it.invoke(
//                instance,
//                *(args ?: emptyArray()).mapIndexed<V8Value, Any?> { index, arg ->
//                  mapArgument(arg, it.parameterTypes[index], v8Runtime)
//                }.toTypedArray().also { args ->
//                }
//              )
//            }
//            v8Runtime.createV8ValueUndefined()
//          })
//      )
//    }
//  }
//
//  private fun determineMethod(clazz: Class<*>, instance: Any?, name: String, args: Array<V8Value>): Method {
//    m@ for (method in clazz.methods + clazz.declaredMethods) {
//      if (method.name == name) {
//        if (method.parameterCount != args.size) {
//          continue@m
//        }
//        for ((i, param) in method.parameterTypes.withIndex()) {
//          val arg = args[i]
//          if (arg is V8ValueObject) {
//            val jhandle = arg.get<V8ValueLong>("__jhandle__")?.value
//            if (jhandle == null) {
//              logger.warn("__jhandle__ not present!")
//              continue@m
//            } else if (jhandle in instanceHandles) {
//              if (!param.isInstance(instanceHandles[jhandle])) {
//                continue@m
//              }
//            } else {
//              logger.warn("Object not found in instances: $jhandle")
//              if (param != V8ValueObject::class.java) {
//                logger.warn("Not a V8ValueObject: $jhandle")
//                continue@m
//              }
//            }
//          } else if (arg is V8ValueString) {
//            if (param != String::class.java) {
//              continue@m
//            }
//          } else if (arg is V8ValueBoolean) {
//            if (param != Boolean::class.java) {
//              continue@m
//            }
//          } else if (arg is V8ValueInteger) {
//            if (param != Int::class.java && param != Long::class.java && param != Short::class.java && param != Byte::class.java && param != Float::class.java && param != Double::class.java) {
//              continue@m
//            }
//          } else if (arg is V8ValueLong) {
//            if (param != Long::class.java && param != Int::class.java && param != Short::class.java && param != Byte::class.java && param != Float::class.java && param != Double::class.java) {
//              continue@m
//            }
//          } else if (arg is V8ValueDouble) {
//            if (param != Double::class.java && param != Float::class.java && param != Int::class.java && param != Long::class.java && param != Short::class.java && param != Byte::class.java) {
//              continue@m
//            }
//          } else {
//            throw Exception("Unsupported argument type: ${arg.javaClass.name}")
//          }
//        }
//
//        return method
//      }
//    }
//
//    throw Exception("Method not found for arguments: ${clazz.name}.${name}(${args.joinToString(", ") { it.javaClass.name }})")
//  }
//
//  private fun mapArgument(arg: V8Value, type: Class<*>, v8Runtime: V8Runtime): Any? {
//    return when (arg) {
//      is V8ValueObject -> {
//        val value = arg.get<V8ValueLong>("__jhandle__")?.value
//        if (value == null) {
//          logger.warn("__jhandle__ not present!")
//          arg
//        } else if (value in instanceHandles) {
//          instanceHandles[value]
//        } else {
//          logger.trace(instanceHandles.toString())
//          logger.warn("Object not found in instances: ${value}")
//          arg
//        }
//      }
//
//      is V8ValueProxy -> {
//        val value = arg.get<V8ValueLong>("__jhandle__")?.value
//        if (value == null) {
//          logger.warn("__jhandle__ not present!")
//          arg
//        } else if (value in instanceHandles) {
//          instanceHandles[value]
//        } else {
//          logger.trace(instanceHandles.toString())
//          logger.warn("Object not found in instances: ${value}")
//          arg
//        }
//      }
//
//      is V8ValueString -> arg.value
//      is V8ValueBoolean -> arg.value
//      is V8ValueInteger -> when (type) {
//        Double::class.javaPrimitiveType -> arg.value.toDouble()
//        Float::class.javaPrimitiveType -> arg.value.toFloat()
//        Long::class.javaPrimitiveType -> arg.value.toLong()
//        Int::class.javaPrimitiveType -> arg.value.toInt()
//        Short::class.javaPrimitiveType -> arg.value.toShort()
//        Byte::class.javaPrimitiveType -> arg.value.toByte()
//        Double::class.java -> arg.value.toDouble()
//        Float::class.java -> arg.value.toFloat()
//        Long::class.java -> arg.value.toLong()
//        Int::class.java -> arg.value.toInt()
//        Short::class.java -> arg.value.toShort()
//        Byte::class.java -> arg.value.toByte()
//        else -> arg.value
//      }
//
//      is V8ValueLong -> when (type) {
//        Double::class.javaPrimitiveType -> arg.value.toDouble()
//        Float::class.javaPrimitiveType -> arg.value.toFloat()
//        Long::class.javaPrimitiveType -> arg.value.toLong()
//        Int::class.javaPrimitiveType -> arg.value.toInt()
//        Short::class.javaPrimitiveType -> arg.value.toShort()
//        Byte::class.javaPrimitiveType -> arg.value.toByte()
//        Double::class.java -> arg.value.toDouble()
//        Float::class.java -> arg.value.toFloat()
//        Long::class.java -> arg.value.toLong()
//        Int::class.java -> arg.value.toInt()
//        Short::class.java -> arg.value.toShort()
//        Byte::class.java -> arg.value.toByte()
//        else -> arg.value
//      }
//
//      is V8ValueDouble -> when (type) {
//        Double::class.javaPrimitiveType -> arg.value.toDouble()
//        Float::class.javaPrimitiveType -> arg.value.toFloat()
//        Long::class.javaPrimitiveType -> arg.value.toLong()
//        Int::class.javaPrimitiveType -> arg.value.toInt()
//        Short::class.javaPrimitiveType -> arg.value.toInt().toShort()
//        Byte::class.javaPrimitiveType -> arg.value.toInt().toByte()
//        Double::class.java -> arg.value
//        Float::class.java -> arg.value.toFloat()
//        Int::class.java -> arg.value.toInt()
//        Long::class.java -> arg.value.toLong()
//        Short::class.java -> arg.value.toInt().toShort()
//        Byte::class.java -> arg.value.toInt().toByte()
//        else -> arg.value
//      }
//
//      else -> arg
//    }.also {
//    }
//  }
//
//  private fun mapGetterSetterMethod(
//    namespace: V8ValueObject,
//    method: Method,
//    v8Runtime: V8Runtime,
//    javaClass: Class<*>,
//    instance: Any?,
//  ) {
//    val propName = method.name.substring(3).let {
//      return@let it.substring(0, 1).lowercase(Locale.getDefault()) + it.substring(1)
//    }
//    namespace.set(propName, v8Runtime.createV8ValueProxy().also { proxy ->
//      try {
//        javaClass.getMethod("set${method.name.substring(3)}").let { setter ->
//          proxy.bindProperty(
//            JavetCallbackContext(
//              propName, JavetCallbackType.DirectCallGetterAndThis, IJavetDirectCallable.GetterAndThis<Exception> {
//                createObject(v8Runtime, method.invoke(instance))
//              }),
//            JavetCallbackContext(
//              propName,
//              JavetCallbackType.DirectCallSetterAndThis,
//              IJavetDirectCallable.SetterAndThis<Exception> { self, value ->
//                setter.invoke(instance, v8Runtime.converter.toObject(value))
//                return@SetterAndThis value
//              }
//            )
//          )
//        }
//      } catch (e: NoSuchMethodException) {
//        proxy.bindProperty(
//          JavetCallbackContext(
//            method.name,
//            JavetCallbackType.DirectCallGetterAndThis,
//            IJavetDirectCallable.GetterAndThis<Exception> {
//              createObject(v8Runtime, method.invoke(instance))
//            })
//        )
//      }
//    })
//
//  }
//
//  private fun createObject(v8Runtime: V8Runtime, invoke: Any?): V8Value? {
//    if (invoke == null) {
//      return v8Runtime.createV8ValueNull()
//    }
//
//    if (invoke is V8Value) {
//      return invoke
//    }
//
//    if (invoke is String) {
//      return v8Runtime.createV8ValueString(invoke)
//    }
//
//    if (invoke is Boolean) {
//      return v8Runtime.createV8ValueBoolean(invoke)
//    }
//
//    if (invoke is Double) {
//      return v8Runtime.createV8ValueDouble(invoke)
//    }
//
//    if (invoke is Int) {
//      return v8Runtime.createV8ValueInteger(invoke)
//    }
//
//    if (invoke is Long) {
//      return v8Runtime.createV8ValueLong(invoke)
//    }
//
//    if (invoke is Float) {
//      return v8Runtime.createV8ValueDouble(invoke.toDouble())
//    }
//
//    if (invoke is Short) {
//      return v8Runtime.createV8ValueInteger(invoke.toInt())
//    }
//
//    if (invoke is Byte) {
//      return v8Runtime.createV8ValueInteger(invoke.toInt())
//    }
//
//    if (invoke is Char) {
//      return v8Runtime.createV8ValueString(invoke.toString())
//    }
//
//    return invoke.let { value ->
//      v8Runtime.createV8ValueObject().also { obj ->
//        map(obj, v8Runtime, value, value.javaClass)
//
//        obj.set("__proto__", v8Runtime.createV8ValueString(value.javaClass.name))
//        val handle = System.nanoTime()
//        obj.set("__jhandle__", v8Runtime.createV8ValueLong(handle))
//        instanceHandles[handle] = value
//        obj.bindFunction(
//          JavetCallbackContext(
//            "toString",
//            JavetCallbackType.DirectCallThisAndResult,
//            IJavetDirectCallable.ThisAndResult<Exception> { _, _ ->
//              v8Runtime.createV8ValueString(value.toString())
//            }
//          )
//        )
//        obj.bindFunction(
//          JavetCallbackContext(
//            "equals",
//            JavetCallbackType.DirectCallThisAndResult,
//            IJavetDirectCallable.ThisAndResult<Exception> { _, args ->
//              v8Runtime.createV8ValueBoolean(args[0] == obj)
//            }
//          )
//        )
//        obj.bindFunction(
//          JavetCallbackContext(
//            "hashCode",
//            JavetCallbackType.DirectCallThisAndResult,
//            IJavetDirectCallable.ThisAndResult<Exception> { _, _ ->
//              v8Runtime.createV8ValueInteger(value.hashCode())
//            }
//          )
//        )
//      }
//    } ?: v8Runtime.createV8ValueNull()
//  }
//
//  private fun map(obj: V8ValueObject, v8Runtime: V8Runtime, instance: Any, javaClass: Class<*>) {
//    val stack = Stack<Class<*>>()
//    stack.push(javaClass)
//    while (stack.isNotEmpty()) {
//      val javaClass = stack.pop()
//      for (field in javaClass.fields) {
//        if (Modifier.isStatic(field.modifiers) || !Modifier.isPublic(field.modifiers)) {
//          continue
//        }
//        mapField(obj, field, v8Runtime, javaClass, instance)
//      }
//      for (method in javaClass.methods) {
//        if (method.name == "<init>") continue
//        if (Modifier.isStatic(method.modifiers) || !Modifier.isPublic(method.modifiers)) {
//          continue
//        }
////        if (method.name.startsWith("get")) {
////          if (method.name.length > 3 && method.returnType != Void.TYPE && method.parameterCount == 0) {
////            mapGetterSetterMethod(obj, method, v8Runtime, javaClass, instance)
////          } else {
////            mapMethod(obj, method, v8Runtime, instance)
////          }
////        } else {
//          mapMethod(obj, method, v8Runtime, instance)
////        }
//      }
//
//      javaClass.superclass?.let {
//        stack.push(it)
//      }
//
//      for (interfaceClass in javaClass.interfaces) {
//        stack.push(interfaceClass)
//      }
//    }
//  }
//}
//
//class FirstScreen : KtxScreen {
//  private val image = Texture("logo.png".toInternalFile(), true).apply { setFilter(Linear, Linear) }
//  private val batch = SpriteBatch()
//
//  override fun render(delta: Float) {
//    clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
//    batch.use {
//      it.draw(image, 100f, 160f)
//    }
//  }
//
//  override fun dispose() {
//    image.disposeSafely()
//    batch.disposeSafely()
//  }
//}
//
//private var registry: MutableMap<Long, Any> = mutableMapOf()
//private var id = 0L
//private val sharesLogger: Logger = LoggerFactory["Shares"]
//
//fun store(value: Any): Long {
//  val theId = id++
//  registry[theId] = value
//  return theId
//}
//
//fun delete(id: Long) {
//  val remove = registry.remove(id) ?: return
//
//  if (remove is Disposable) {
//    Gdx.app.postRunnable {
//      try {
//        remove.dispose()
//      } catch (e: Exception) {
//        sharesLogger.error("Failed to dispose $remove")
//        sharesLogger.error(e.stackTraceToString())
//      }
//    }
//  }
//  if (remove is AutoCloseable) {
//    Gdx.app.postRunnable {
//      try {
//        remove.close()
//      } catch (e: Exception) {
//        sharesLogger.error("Failed to close $remove")
//        sharesLogger.error(e.stackTraceToString())
//      }
//    }
//  }
//}
//
//fun retrieve(id: Long): Any? {
//  return registry[id]
//}
