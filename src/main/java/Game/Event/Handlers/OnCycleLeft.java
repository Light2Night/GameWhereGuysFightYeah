package Game.Event.Handlers;

import Game.Statistics.Session.ICycleStatisticCollector;
import Game.Units.Characters.GameUnit;
import Game.Event.Arguments.Actions.ActionInfo;
import Game.Event.Eventable;

import java.util.ArrayList;

public class OnCycleLeft implements Eventable {
    private ArrayList<GameUnit> units;
    private ArrayList<ActionInfo> cycleActions;
    private ICycleStatisticCollector statisticCollector;

    public OnCycleLeft(ArrayList<GameUnit> units, ArrayList<ActionInfo> cycleActions, ICycleStatisticCollector statisticCollector) {
        this.units = units;
        this.cycleActions = cycleActions;
        this.statisticCollector = statisticCollector;
    }

    @Override
    public void onEvent() {
        executeEffectsForAll();
        cycleActions.clear();
        statisticCollector.addCycle();
    }

    private void executeEffectsForAll() {
        units.forEach(GameUnit::executeEffects);
    }
}
