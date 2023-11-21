package Game.Statistics.Session;

import Game.Units.Characters.GameUnit;
import ViewModels.SessionStatisticVm;
import kotlin.NotImplementedError;

import java.util.Hashtable;
import java.util.Map;

public class SessionStatisticBuilder implements ISessionStatisticBuilder {
    Map<GameUnit, UnitStatisticInfo> unitStatistics;

    public SessionStatisticBuilder() {
        reset();
    }

    public void reset() {
        unitStatistics = new Hashtable<>();
    }

    @Override
    public SessionStatisticVm build() {
//        throw new NotImplementedError();
        return null;
    }

    private UnitStatisticInfo getStatistic(GameUnit unit) {
        if (!unitStatistics.containsKey(unit)) {
            unitStatistics.put(unit, new UnitStatisticInfo());
        }

        return unitStatistics.get(unit);
    }

    @Override
    public IStatisticCollector addDamage(GameUnit unit, int damage) {
        getStatistic(unit).addDamage(damage);
        return this;
    }
}
