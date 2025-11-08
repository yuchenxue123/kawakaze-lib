import cute.neko.event.EventManager
import cute.neko.event.EventPriority
import kotlin.test.Test

class KotlinTest {

    @Test
    fun test1() {
        val list = mutableListOf(
            EventPriority.valueOf(3),
            EventPriority.valueOf(1),
            EventPriority.valueOf(2),
        )

        val list1 = mutableListOf(
            3,
            1,
            2
        )

        list.sort()

        list.forEach { println(it.value) }

        println("---------")

        list1.sort()

        list1.forEach { println(it) }
    }

    @Test
    fun test2() {
        val listener = SampleListener()

        EventManager.callEvent(DummyEvent("Ciallo World!"))
    }
}