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
        if (queue.isEmpty()) {
            resetQueue();
        }

        GameUnit unit;
        do {
            unit = queue.get(0);

            if (!unit.isAlive()) {
                queue.remove(0);
            }
        } while (!unit.isAlive() && !queue.isEmpty());

        if (queue.isEmpty()) {
            resetQueue();
            unit = queue.get(0);
        }

        queue.remove(0);

        if (queue.isEmpty()) {
            resetQueue();
        }

        return queue.get(0).getId();
    }
}
