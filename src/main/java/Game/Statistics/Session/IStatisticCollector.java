package Game.Statistics.Session;

import Game.Units.Characters.GameUnit;

public interface IStatisticCollector {
    IStatisticCollector addDamage(GameUnit unit, int damage);
}
