package cute.neko.kawakaze.config

object ConfigTask {
    private val beforeLoadTasks = mutableListOf<(ConfigRegistrar) -> Unit>()

    private val afterLoadTasks = mutableListOf<() -> Unit>()

    /**
     * Add a task before the config be loaded.
     *
     * @param task A lambda that takes a [ConfigRegistrar] and performs the registration.
     */
    fun before(task: (registrar: ConfigRegistrar) -> Unit) {
        beforeLoadTasks.add(task)
    }

    /**
     * Add a task after the config be loaded.
     *
     * @param task A lambda without params.
     */
    fun after(task: () -> Unit) {
        afterLoadTasks.add(task)
    }

    internal fun runBeforeTasks(registrar: ConfigRegistrar) {
        beforeLoadTasks.forEach { it.invoke(registrar) }
    }

    internal fun runAfterTasks() {
        afterLoadTasks.forEach { it.invoke() }
    }
}