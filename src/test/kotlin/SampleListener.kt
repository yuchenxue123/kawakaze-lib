import cute.neko.event.EventListener
import cute.neko.event.handler

class SampleListener : EventListener {

    override val running: Boolean
        get() = true

    private val onDummy = handler<DummyEvent> { event ->
        println("Kt: ${event.message}")
    }
}