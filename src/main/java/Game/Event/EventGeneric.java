package Game.Event;

import java.util.ArrayList;

public class EventGeneric<T> {
    private final ArrayList<HandlerGeneric<T>> handlers;

    public EventGeneric() {
        handlers = new ArrayList<>();
    }

    public void addHandler(HandlerGeneric<T> handler) {
        handlers.add(handler);
    }

    public void invoke(T t) {
        for (HandlerGeneric<T> handler : handlers) {
            handler.handle(t);
        }
    }
}
