package cute.neko.event

interface EventListener {

    /**
     * Checks if the event listener is currently active and should process events.
     * By default, it defers to its parent listener's running status if a parent exists,
     * otherwise, it's considered running.
     */
    val running: Boolean
        get() = parent()?.running ?: true

    /**
     * Parent listener of this listener,
     * default is null, indicating no parent.
     */
    fun parent(): EventListener? = null


    /**
     * Unregisters this event listener from the EventManager.
     */
    fun unregister() {
        EventManager.unregisterEventHandler(this)
    }
}

typealias Handler<T> = (event: T) -> Unit

class EventHook<T : Event>(
    val handlerClass: EventListener,
    val handler: Handler<T>,
    val always: Boolean,
    val priority: EventPriority,
)

inline fun <reified T: Event> EventListener.handler(
    always: Boolean = false,
    priority: EventPriority = DefaultPriority.DEFAULT,
    noinline handler: Handler<T>
) {
    EventManager.registerEventHook(T::class.java, EventHook(this, handler, always, priority))
}