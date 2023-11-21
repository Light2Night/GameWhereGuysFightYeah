package Game.Effects;

import Game.Units.Characters.GameUnit;

public class Healing implements Effectable {
    private int cyclesLeft;
    private final int durationInCycles;
    public final int heal;

    public Healing(Healing healing) {
        this.durationInCycles = healing.durationInCycles;
        this.cyclesLeft = healing.cyclesLeft;
        this.heal = healing.heal;
    }

    public Healing() {
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

        if (unit.getHp() == 0) {
            return;
        }

        System.out.printf("Юніт (%s) відновлює %d здоров'я завдяки зіллю відновлення\n", unit.toString(), heal);

        unit.heal(heal);

        if (cyclesLeft > 0) {
            System.out.printf("Відновлення діятиме ще циклів: %d\n", cyclesLeft);
        }
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
    public Effectable clone() {
        return new Healing(this);
    }
}
