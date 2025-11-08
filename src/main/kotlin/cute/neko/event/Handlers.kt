package cute.neko.event

inline fun <reified T: Event> EventListener.handler(
    always: Boolean = false,
    priority: EventPriority = DefaultPriority.DEFAULT,
    handler: Handler<T>
) {
    EventManager.registerEventHook(T::class.java, EventHook(this, handler, always, priority))
}