package Game.Event.Handlers;

import Game.Units.Characters.GameUnit;
import Game.Event.Arguments.Actions.ActionInfo;
import Game.Event.Eventable;

import java.util.ArrayList;

public class OnCycleLeft implements Eventable {
    private ArrayList<GameUnit> units;
    private ArrayList<ActionInfo> cycleActions;

    public OnCycleLeft(ArrayList<GameUnit> units, ArrayList<ActionInfo> cycleActions) {
        this.units = units;
        this.cycleActions = cycleActions;
    }

    @Override
    public void onEvent() {
        executeEffectsForAll();
        cycleActions.clear();
    }

    private void executeEffectsForAll() {
        units.forEach(GameUnit::executeEffects);
    }
}
