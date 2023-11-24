package Game.Event.Aggregates;

import Game.Event.Arguments.UnitId;
import Game.Event.Event;
import Game.Event.EventGeneric;
import Game.Event.Arguments.GameEndInfo;

public class GameEventsAggregate {
    public final EventGeneric<UnitId> SelectedIndexChangedEvent = new EventGeneric<>();
    public final EventGeneric<UnitId> CurrentIndexChangedEvent = new EventGeneric<>();
    public final Event MoveCompletedEvent = new Event();
    public final EventGeneric<GameEndInfo> GameEndEvent = new EventGeneric<>();
    public final Event CycleLeftEvent = new Event();
}
