package Game.Effects.Factories;

import Game.Effects.Effectable;
import Game.Effects.Healing;
import Game.Effects.Poisoning;
import Game.Effects.SharedDatas.EffectSharedData;
import Game.Statistics.Session.IUnitStatisticCollector;
import Game.Units.Characters.GameUnit;

public class EffectFactory {
    private final EffectSharedData sharedData;

    public EffectFactory(EffectSharedData sharedData) {
        this.sharedData = sharedData;
    }

    public Effectable createPoisoning(GameUnit creator) {
        return new Poisoning(sharedData, creator);
    }

    public Effectable createHealing(GameUnit creator) {
        return new Healing(sharedData, creator);
    }
}
