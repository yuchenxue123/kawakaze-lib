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
        EventManager.unregisterEventListener(this)
    }
}