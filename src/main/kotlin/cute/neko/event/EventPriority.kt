package cute.neko.event

interface EventPriority : Comparable<EventPriority> {

    /**
     * Represents the priority level of an event,
     * larger value typically indicates higher priority.
     */
    val value: Int

    override fun compareTo(other: EventPriority): Int {
        return value.compareTo(other.value)
    }

    companion object {
        fun valueOf(value: Int): EventPriority {
            return Implement(value)
        }
    }

    private class Implement(override val value: Int) : EventPriority
}