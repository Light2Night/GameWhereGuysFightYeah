package Game;

import Game.CharacterGetters.CompositeAccessor;
import Game.Characters.GameUnit;

import java.util.ArrayList;

public class GameCycle {
    private CompositeAccessor accessor;
    private ArrayList<GameUnit> queue;

    public GameCycle(CompositeAccessor accessor) {
        this.accessor = accessor;
        resetQueue();
    }

    private void resetQueue() {
        queue = new ArrayList<>();
        for (int i = 0; i < accessor.getUnitsAccessor().getQuantity(); i++) {
            queue.add(accessor.getUnitsAccessor().getUnitByIndex(i));
        }
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
