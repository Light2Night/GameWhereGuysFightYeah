package Game.Event.Aggregates;

import Game.Event.Arguments.Actions.ActionInfo;
import Game.Event.UnitActionEventable;
import org.jetbrains.annotations.Nullable;

public class UnitEventsAggregate {
    @Nullable
    private UnitActionEventable actionPerformedEvent;

    public void setActionPerformedEvent(@Nullable UnitActionEventable actionPerformedEvent) {
        this.actionPerformedEvent = actionPerformedEvent;
    }

    public void actionPerformed(ActionInfo info) {
        if (actionPerformedEvent != null) actionPerformedEvent.onEvent(info);
    }
}
