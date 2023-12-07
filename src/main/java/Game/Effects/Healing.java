package Game.Effects;

import Game.Effects.SharedDatas.EffectSharedData;
import Game.Units.Characters.GameUnit;

public class Healing extends Effect {
    private int cyclesLeft;
    private final int durationInCycles;
    public final int heal;

    public Healing(EffectSharedData sharedData, GameUnit creator) {
        super(sharedData, creator);

        durationInCycles = cyclesLeft = 3;
        heal = 10;
    }

    @Override
    public EffectTypes getEffectType() {
        return EffectTypes.HEALING;
    }

    @Override
    public void effect(GameUnit target) {
        if (cyclesLeft == 0) return;
        cyclesLeft--;

        int hpHealed = target.heal(heal);
        sharedData.StatisticCollector.addHeal(creator, hpHealed);
    }

    @Override
    public int getCyclesLeft() {
        return cyclesLeft;
    }

    @Override
    public int getDurationInCycles() {
        return durationInCycles;
    }

    @Override
    public Healing clone() throws CloneNotSupportedException {
        return (Healing) super.clone();
    }
}
