import cute.neko.event.EventListener;
import cute.neko.event.Handler;
import cute.neko.event.annotation.EventHandler;

public class SimpleListener implements EventListener {

    @EventHandler(priority = 999)
    private final Handler<MessageEvent> onMessage = event -> {
        System.out.println("Field: " + event.message());
    };

    @EventHandler(priority = 500)
    private void onMessage(MessageEvent event) {
        System.out.println("Func: " + event.message());
    }
}
