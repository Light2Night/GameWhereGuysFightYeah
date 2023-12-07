package Game.Events.Handlers;

import Game.Events.Handler;
import Game.Statistics.Session.ICycleStatisticCollector;
import Game.Units.Characters.GameUnit;
import Game.Events.Arguments.Actions.ActionInfo;

import java.util.ArrayList;

public class OnCycleLeft extends Handler {
    private final ArrayList<GameUnit> units;
    private final ArrayList<ActionInfo> cycleActions;
    private final ICycleStatisticCollector statisticCollector;

    public OnCycleLeft(ArrayList<GameUnit> units, ArrayList<ActionInfo> cycleActions, ICycleStatisticCollector statisticCollector) {
        this.units = units;
        this.cycleActions = cycleActions;
        this.statisticCollector = statisticCollector;
    }

    @Override
    public void handle() {
        executeEffectsForAll();
        cycleActions.clear();
        statisticCollector.addCycle();
    }

    private void executeEffectsForAll() {
        ArrayList<GameUnit> queue = new ArrayList<>(units);
        queue.forEach(GameUnit::executeEffects);
    }
}
