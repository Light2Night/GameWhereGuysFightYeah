package Game;

import Game.Statistics.Session.ISessionStatisticBuilder;
import Game.Units.Characters.GameUnit;

import java.util.ArrayList;
import java.util.List;

public class UnitsManager {
    private final ArrayList<GameUnit> units;
    private final ISessionStatisticBuilder sessionStatisticBuilder;

    public UnitsManager(ArrayList<GameUnit> units, ISessionStatisticBuilder sessionStatisticBuilder) {
        this.units = units;
        this.sessionStatisticBuilder = sessionStatisticBuilder;
    }

    public void removeDeadUnits() {
        List<GameUnit> deadUnits = units.stream().filter(u -> !u.isAlive()).toList();
        deadUnits.forEach(sessionStatisticBuilder::setDied);
        units.removeAll(deadUnits);
    }
}
