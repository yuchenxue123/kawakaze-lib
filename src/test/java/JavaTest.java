import cute.neko.event.EventManager;
import org.junit.Test;

public class JavaTest {

    @Test
    public void test1() {
        SimpleListener listener = new SimpleListener();

        EventManager.registerEventListener(listener);

        EventManager.callEvent(new MessageEvent("Ciallo World!"));
    }
}
