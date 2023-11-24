package Game.Event.Aggregates;

import Game.Event.Arguments.Actions.ActionInfo;
import Game.Event.EventGeneric;

public class UnitEventsAggregate {
    public final EventGeneric<ActionInfo> ActionPerformedEvent = new EventGeneric<>();
}
