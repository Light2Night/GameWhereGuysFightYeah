package Game.Effects;

import Game.Statistics.Session.IUnitStatisticCollector;
import Game.Units.Characters.GameUnit;

public class Poisoning implements Effectable {
    private int cyclesLeft;
    private final int durationInCycles;
    public final int damage;
    private final IUnitStatisticCollector statisticCollector;
    private final GameUnit unit;

    public Poisoning(Poisoning poisoning) {
        this.durationInCycles = poisoning.durationInCycles;
        this.cyclesLeft = poisoning.cyclesLeft;
        this.damage = poisoning.damage;
        this.statisticCollector = poisoning.statisticCollector;
        this.unit = poisoning.unit;
    }

    public Poisoning(IUnitStatisticCollector statisticCollector, GameUnit unit) {
        this.statisticCollector = statisticCollector;
        this.unit = unit;

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
        statisticCollector.addDamage(unit, damageTakenByEnemy);
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
    public Poisoning copy() {
        return new Poisoning(this);
    }
}
