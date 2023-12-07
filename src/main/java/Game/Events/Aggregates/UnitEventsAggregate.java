package Game.Events.Aggregates;

import Game.Events.Arguments.Actions.ActionInfo;
import Game.Events.Arguments.DeadUnitInfo;
import Game.Events.EventGeneric;

public class UnitEventsAggregate {
    public final EventGeneric<ActionInfo> ActionPerformedEvent = new EventGeneric<>();
    public final EventGeneric<DeadUnitInfo> UnitDiedEvent = new EventGeneric<>();
}
