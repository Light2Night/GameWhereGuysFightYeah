package Game;

import Game.CharacterGetters.CompositeAccessor;
import Game.Characters.GameUnit;
import Game.Event.GameEventsAggregate;

import java.util.ArrayList;

public class GameCycle {
    private CompositeAccessor accessor;
    private ArrayList<GameUnit> queue;
    private GameEventsAggregate events;

    public GameCycle(CompositeAccessor accessor, GameEventsAggregate events) {
        this.accessor = accessor;
        this.events = events;
        resetQueue();
    }

    private void resetQueue() {
        queue = new ArrayList<>();
        for (int i = 0; i < accessor.getUnitsAccessor().getQuantity(); i++) {
            queue.add(accessor.getUnitsAccessor().getUnitByIndex(i));
        }
        events.cycleLeft();
    }

    public int next() {
        removeDeadUnits();

        if (queue.isEmpty()) {
            resetQueue();
        }

        GameUnit unit = queue.get(0);
        queue.remove(0);

        return unit.getId();
    }

    private void removeDeadUnits() {
        queue.removeIf(unit -> !unit.isAlive());
    }
}
