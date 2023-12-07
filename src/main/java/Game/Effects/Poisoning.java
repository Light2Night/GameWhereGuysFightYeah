package Game.Effects;

import Game.Effects.SharedDatas.EffectSharedData;
import Game.Units.Characters.GameUnit;

public class Poisoning extends Effect {
    private int cyclesLeft;
    private final int durationInCycles;
    public final int damage;

    public Poisoning(EffectSharedData sharedData) {
        super(sharedData);

        durationInCycles = cyclesLeft = 2;
        damage = 15;
    }

    @Override
    public EffectTypes getEffectType() {
        return EffectTypes.POISONING;
    }

    @Override
    public void effect(GameUnit unit) {
        if (cyclesLeft == 0) return;
        cyclesLeft--;

        int damageTakenByEnemy = unit.takeDamage(damage);
        sharedData.StatisticCollector.addDamage(unit, damageTakenByEnemy);
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
