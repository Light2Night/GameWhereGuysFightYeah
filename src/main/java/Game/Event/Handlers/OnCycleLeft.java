package Game.Event.Handlers;

import Game.Characters.GameUnit;
import Game.Event.Eventable;

import java.util.ArrayList;

public class OnCycleLeft implements Eventable {
    private ArrayList<GameUnit> units;

    public OnCycleLeft(ArrayList<GameUnit> units) {
        this.units = units;
    }

    @Override
    public void onEvent() {
        executeEffectsForAll();
    }

    private void executeEffectsForAll() {
        units.forEach(GameUnit::executeEffects);
    }
}
