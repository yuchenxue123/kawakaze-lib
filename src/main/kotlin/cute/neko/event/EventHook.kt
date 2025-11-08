package cute.neko.event

data class EventHook<T : Event>(
    val listener: EventListener,
    val handler: Handler<T>,
    val always: Boolean,
    val priority: EventPriority,
)