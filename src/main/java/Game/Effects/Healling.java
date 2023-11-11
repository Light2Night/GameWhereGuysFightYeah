package Game.Effects;

import Game.Units.Characters.GameUnit;

public class Healling implements Effectable {
    private int cyclesLeft;
    private final int durationInCycles;
    public final int heal;

    public Healling(Healling healling) {
        this.durationInCycles = healling.durationInCycles;
        this.cyclesLeft = healling.cyclesLeft;
        this.heal = healling.heal;
    }

    public Healling() {
        durationInCycles = cyclesLeft = 3;
        heal = 10;
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
        return new Healling(this);
    }
}
