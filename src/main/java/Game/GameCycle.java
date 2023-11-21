package Game;

import Game.Statistics.Session.IUnitStatisticCollector;
import Game.Units.Getters.CompositeAccessor;
import Game.Units.Characters.GameUnit;
import Game.Event.Aggregates.GameEventsAggregate;

import java.util.ArrayList;

public class GameCycle {
    private CompositeAccessor accessor;
    private ArrayList<GameUnit> queue;
    private GameEventsAggregate events;

    public GameCycle(CompositeAccessor accessor, GameEventsAggregate events) {
        this.accessor = accessor;
        this.events = events;
        resetFields();
    }

    public void resetFields() {
        queue = new ArrayList<>();
        for (int i = 0; i < accessor.getUnitsAccessor().getQuantity(); i++) {
            queue.add(accessor.getUnitsAccessor().getUnitByIndex(i));
        }
    }

    private void resetQueue() {
        resetFields();
        events.cycleLeft();
    }

    public Integer next() {
        removeDeadUnits();

        if (queue.isEmpty()) {
            resetQueue();
        }

        if (queue.isEmpty()) return null;

        GameUnit unit = queue.get(0);
        queue.remove(0);

        return unit.getId();
    }

    private void removeDeadUnits() {
        queue.removeIf(unit -> !unit.isAlive());
    }
}
