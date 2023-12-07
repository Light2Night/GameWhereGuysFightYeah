package Game.Events.Handlers;

import Game.Events.Arguments.DeadUnitInfo;
import Game.Events.HandlerGeneric;
import Game.UnitsManager;

public class OnUnitDied extends HandlerGeneric<DeadUnitInfo> {
    private final UnitsManager unitsManager;

    public OnUnitDied(UnitsManager unitsManager) {
        this.unitsManager = unitsManager;
    }

    @Override
    public void handle(DeadUnitInfo deadUnitInfo) {
        unitsManager.removeDeadUnit(deadUnitInfo.Unit);
    }
}
