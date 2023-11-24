package Game.Events.Aggregates;

import Game.Events.Arguments.UnitId;
import Game.Events.Event;
import Game.Events.EventGeneric;
import Game.Events.Arguments.GameEndInfo;

public class GameEventsAggregate {
    public final EventGeneric<UnitId> SelectedIndexChangedEvent = new EventGeneric<>();
    public final EventGeneric<UnitId> CurrentIndexChangedEvent = new EventGeneric<>();
    public final Event MoveCompletedEvent = new Event();
    public final EventGeneric<GameEndInfo> GameEndEvent = new EventGeneric<>();
    public final Event CycleLeftEvent = new Event();
}
