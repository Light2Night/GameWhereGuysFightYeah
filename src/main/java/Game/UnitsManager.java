package Game;

import Game.Statistics.Session.ISessionStatisticBuilder;
import Game.Units.Characters.GameUnit;

import java.util.ArrayList;

public class UnitsManager {
    private final ArrayList<GameUnit> units;
    private final GameCycle gameCycle;
    private final ISessionStatisticBuilder sessionStatisticBuilder;

    public UnitsManager(ArrayList<GameUnit> units, GameCycle gameCycle, ISessionStatisticBuilder sessionStatisticBuilder) {
        this.units = units;
        this.gameCycle = gameCycle;
        this.sessionStatisticBuilder = sessionStatisticBuilder;
    }

    public void removeDeadUnit(GameUnit unit) {
        units.remove(unit);
        gameCycle.removeUnitFromQueue(unit);

        sessionStatisticBuilder.setDied(unit);
    }
}
