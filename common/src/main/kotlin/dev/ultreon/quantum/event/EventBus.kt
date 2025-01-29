package dev.ultreon.quantum.event

//import com.caoccao.javet.interop.V8Runtime
//import com.caoccao.javet.values.primitive.V8ValueString
//import com.caoccao.javet.values.reference.V8ValueObject
//
//typealias EventName = V8ValueString
//
//class EventBus {
//  private val eventMap = mutableMapOf<EventName, MutableList<V8ValueObject>>()
//
//  fun subscribe(event: EventName, listener: V8ValueObject) {
//    eventMap.getOrPut(event) { mutableListOf() }.add(listener)
//  }
//
//  fun unsubscribe(event: EventName, listener: V8ValueObject) {
//    eventMap[event]?.remove(listener)
//  }
//
//  fun fire(runtime: V8Runtime, event: Event) {
//    eventMap[V8ValueString(runtime, event.name)]?.forEach { it.invokeVoid("onEvent", event.payload) }
//  }
//}
