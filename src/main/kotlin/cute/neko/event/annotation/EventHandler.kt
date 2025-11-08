package cute.neko.event.annotation

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventHandler(
    val priority: Int = 100,
    val always: Boolean = false
)
