package Game.Events.Handlers;

import Game.Events.Arguments.Actions.ActionInfo;
import Game.Events.HandlerGeneric;
import Game.UnitsManager;

public class OnActionCompleted extends HandlerGeneric<ActionInfo> {
    private final UnitsManager unitsManager;

    public OnActionCompleted(UnitsManager unitsManager) {
        this.unitsManager = unitsManager;
    }

    @Override
    public void handle(ActionInfo actionInfo) {
        unitsManager.removeDeadUnits();
    }
}
