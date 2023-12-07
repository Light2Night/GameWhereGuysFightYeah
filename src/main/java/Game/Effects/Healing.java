package Game.Effects;

import Game.Effects.SharedDatas.EffectSharedData;
import Game.Units.Characters.GameUnit;

public class Healing extends Effect {
    private int cyclesLeft;
    private final int durationInCycles;
    public final int heal;

    public Healing(EffectSharedData sharedData) {
        super(sharedData);

        durationInCycles = cyclesLeft = 3;
        heal = 10;
    }

    @Override
    public EffectTypes getEffectType() {
        return EffectTypes.HEALING;
    }

    @Override
    public void effect(GameUnit unit) {
        if (cyclesLeft == 0) return;

        cyclesLeft--;

        if (unit.getHp() == 0) return;

        unit.heal(heal);
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
