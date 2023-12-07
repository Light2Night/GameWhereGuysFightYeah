package Game.Effects;

import Game.Effects.SharedDatas.EffectSharedData;
import Game.Units.Characters.GameUnit;

public class Poisoning extends Effect {
    private int cyclesLeft;
    private final int durationInCycles;
    public final int damage;

    public Poisoning(EffectSharedData sharedData, GameUnit creator) {
        super(sharedData, creator);

        durationInCycles = cyclesLeft = 2;
        damage = 15;
    }

    @Override
    public EffectTypes getType() {
        return EffectTypes.POISONING;
    }

    @Override
    public void effect(GameUnit target) {
        if (cyclesLeft == 0) return;
        cyclesLeft--;

        int damageTaken = target.takeDamage(damage);
        sharedData.StatisticCollector.addDamage(creator, damageTaken);
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
    public Poisoning clone() throws CloneNotSupportedException {
        return (Poisoning) super.clone();
    }
}
