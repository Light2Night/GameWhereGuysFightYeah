package Game.Events.Arguments;

import Game.Units.Characters.GameUnit;

public class DeadUnitInfo {
    public final GameUnit Unit;

    public DeadUnitInfo(GameUnit unit) {
        Unit = unit;
    }
}
