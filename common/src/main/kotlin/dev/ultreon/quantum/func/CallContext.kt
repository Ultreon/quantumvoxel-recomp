package dev.ultreon.quantum.func

class CallContext {
  private val params: MutableList<ContextAware> = ArrayList()
  private val paramTypes: MutableList<ContextParam<*>> = ArrayList()

  fun add(param: ContextAware, paramType: ContextParam<*>) {
    params.add(param)
    paramTypes.add(paramType)
  }

  fun getParam(paramType: ContextParam<*>): ContextAware? {
    return params[paramTypes.indexOf(paramType)]
  }

  fun getParam(paramType: Class<*>): ContextAware? {
    return params[paramTypes.indexOfFirst { it.clazz == paramType }]
  }

  fun getParam(name: String): ContextAware? {
    return params[paramTypes.indexOfFirst { it.name == name }]
  }

  fun getParams(): List<ContextAware> {
    return params
  }

  fun getParamTypes(): List<ContextParam<*>> {
    return paramTypes
  }
}
