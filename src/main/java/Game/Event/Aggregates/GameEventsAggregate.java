package Game.Event.Aggregates;

import Game.Event.Event;
import Game.Event.EventGeneric;
import Game.Event.Arguments.GameEndInfo;

public class GameEventsAggregate {
    public final Event SelectedIndexChangedEvent = new Event();
    public final Event CurrentIndexChangedEvent = new Event();
    public final Event MoveCompletedEvent = new Event();
    public final EventGeneric<GameEndInfo> GameEndEvent = new EventGeneric<>();
    public final Event CycleLeftEvent = new Event();
}
