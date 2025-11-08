package cute.neko.event

import cute.neko.event.annotation.EventHandler
import java.lang.reflect.ParameterizedType
import java.util.concurrent.CopyOnWriteArrayList

object EventManager {

    private val registry = HashMap<Class<out Event>, CopyOnWriteArrayList<EventHook<in Event>>>()

    /**
     * Register EventHook
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Event> registerEventHook(eventClass: Class<out Event>, eventHook: EventHook<T>) {
        if (!registry.containsKey(eventClass)) registry[eventClass] = CopyOnWriteArrayList()

        val handlers = registry[eventClass] ?: return

        val hook = eventHook as EventHook<in Event>

        if (!handlers.contains(hook)) {
            handlers.add(hook)

            handlers.sortByDescending { it.priority }
        }
    }

    /**
     * Unregisters a handler.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Event> unregisterEventHook(eventClass: Class<out Event>, eventHook: EventHook<T>) {
        registry[eventClass]?.remove(eventHook as EventHook<in Event>)
    }

    /**
     * This is for java
     * Use reflection register all [EventHook] hooks in [EventListener]
     */
    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun registerEventListener(listener: EventListener) {
        listener.javaClass.declaredMethods.forEach { method ->
            if (!method.isAnnotationPresent(EventHandler::class.java)
                || method.parameterCount != 1
                || !Event::class.java.isAssignableFrom(method.parameters[0].type)
            ) {
                return@forEach
            }

            val annotation = method.getAnnotation(EventHandler::class.java) ?: return@forEach

            method.isAccessible = true

            val clazz = (method.parameterTypes[0] as Class<out Event>)

            registerEventHook(
                clazz,
                EventHook(
                    listener,
                    { event -> method.invoke(listener, event) },
                    annotation.always,
                    EventPriority.valueOf(annotation.priority)
                )
            )
        }

        listener.javaClass.declaredFields.forEach { field ->
            if (!field.isAnnotationPresent(EventHandler::class.java)
                || !Handler::class.java.isAssignableFrom(field.type)
            ) {
                return@forEach
            }

            val annotation = field.getAnnotation(EventHandler::class.java) ?: return@forEach

            field.isAccessible = true

            val clazz = (field.genericType as ParameterizedType).actualTypeArguments[0] as Class<out Event>
            val handler = field.get(listener) as Handler<Event>

            registerEventHook(
                clazz,
                EventHook(
                    listener,
                    { event -> handler.invoke(event) },
                    annotation.always,
                    EventPriority.valueOf(annotation.priority)
                )
            )
        }
    }

    /**
     * Unregister listener
     */
    @JvmStatic
    fun unregisterEventListener(eventHandler: EventListener) {
        registry.values.forEach { hooks ->
            hooks.removeIf { hook -> hook.listener == eventHandler }
        }
    }

    /**
     * Call event to listeners
     */
    @JvmStatic
    fun <T : Event> callEvent(event: T): T {
        val targets = registry[event.javaClass] ?: return event

        for (eventHook in targets) {

            if (!eventHook.always && !eventHook.listener.running) continue

            runCatching {
                eventHook.handler.invoke(event)
            }.onFailure {
                println("Exception while executing handler. -> <${event::class.java.name}> -> ${it.cause} -> ${it.stackTraceToString()}")
            }
        }

        return event
    }
}
