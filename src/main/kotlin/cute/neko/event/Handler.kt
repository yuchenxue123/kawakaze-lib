package cute.neko.event

fun interface Handler<T : Event> {

    fun invoke(event: T)

}