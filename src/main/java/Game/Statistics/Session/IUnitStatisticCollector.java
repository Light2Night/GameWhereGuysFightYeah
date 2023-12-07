package Game.Statistics.Session;

import Game.Effects.EffectTypes;
import Game.Units.Characters.GameUnit;

public interface IUnitStatisticCollector {
    IUnitStatisticCollector addDamage(GameUnit unit, int damage);

    IUnitStatisticCollector addHeal(GameUnit unit, int heal);

    IUnitStatisticCollector addReceivedDamage(GameUnit unit, int damageReceived);

    IUnitStatisticCollector addReceivedHeal(GameUnit unit, int healReceived);

    IUnitStatisticCollector setDied(GameUnit unit);

    IUnitStatisticCollector addImposedEffect(GameUnit unit, EffectTypes effect);

    IUnitStatisticCollector addReceivedEffect(GameUnit unit, EffectTypes effect);
}
