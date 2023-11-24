package Game.Events;

public abstract class HandlerGeneric<T> {
    public abstract void handle(T t);
}
