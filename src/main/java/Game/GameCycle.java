package Game;

import Game.Units.Getters.CompositeAccessor;
import Game.Units.Characters.GameUnit;
import Game.Events.Aggregates.GameEventsAggregate;

import java.util.ArrayList;

public class GameCycle {
    private final CompositeAccessor accessor;
    private ArrayList<GameUnit> queue;
    private final GameEventsAggregate events;

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
        events.CycleLeftEvent.invoke();
        resetFields();
    }

    public Integer next() {
        if (queue.isEmpty()) {
            resetQueue();
        }

        if (queue.isEmpty()) return null;

        GameUnit unit = queue.get(0);
        queue.remove(0);

        return unit.getId();
    }

    public void removeUnitFromQueue(GameUnit unit) {
        queue.remove(unit);
    }
}
