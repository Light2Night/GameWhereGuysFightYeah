package Game.Effects.Factories;

import Game.Effects.Effectable;
import Game.Effects.Healing;
import Game.Effects.Poisoning;
import Game.Statistics.Session.IUnitStatisticCollector;
import Game.Units.Characters.GameUnit;

public class EffectFactory {
    public final IUnitStatisticCollector statisticCollector;

    public EffectFactory(IUnitStatisticCollector statisticCollector) {
        this.statisticCollector = statisticCollector;
    }

    public Effectable createPoisoning(GameUnit unit) {
        return new Poisoning(statisticCollector, unit);
    }

    public Effectable createHealing() {
        return new Healing();
    }
}
