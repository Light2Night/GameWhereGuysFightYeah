package Game.Statistics.Session;

import Game.Effects.EffectTypes;
import Game.Units.Characters.GameUnit;

public interface IUnitStatisticCollector {
    IUnitStatisticCollector addDamage(GameUnit unit, int damage);

    IUnitStatisticCollector setDied(GameUnit unit);

    IUnitStatisticCollector addImposedEffect(GameUnit unit, EffectTypes effect);
}
