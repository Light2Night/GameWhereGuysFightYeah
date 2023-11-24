package Game.Event;

import java.util.ArrayList;

public class Event {
    private final ArrayList<Handler> handlers;

    public Event() {
        handlers = new ArrayList<>();
    }

    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public void invoke() {
        for (Handler handler : handlers) {
            handler.handle();
        }
    }
}
