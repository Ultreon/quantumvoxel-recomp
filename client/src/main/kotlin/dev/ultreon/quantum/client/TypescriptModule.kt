package dev.ultreon.quantum.client

class TypescriptModule(val module: String) {
  private val typescriptApis = mutableMapOf<String, TypescriptApi>()

  fun register(name: String): TypescriptApi {
    return TypescriptApi(name).also {
      typescriptApis[name] = it
    }
  }

  fun register(name: String, onApiRegister: TypescriptApi.() -> Unit) {
    register(name).onApiRegister()
  }

  fun list(): List<TypescriptApi> {
    return typescriptApis.values.toList()
  }

  operator fun get(name: String): TypescriptApi {
    return typescriptApis[name] ?: throw Exception("API not found: $name")
  }
}

object TypescriptApiManager {
  private val modules = mutableMapOf<String, TypescriptModule>()

  fun register(module: String): TypescriptModule {
    return TypescriptModule(module).also {
      modules[module] = it
    }
  }

  fun list(): List<TypescriptModule> {
    return modules.values.toList()
  }

  operator fun get(module: String): TypescriptModule {
    return modules[module] ?: throw Exception("Module not found: $module")
  }
}
