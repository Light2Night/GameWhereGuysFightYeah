package Game.Effects;

import Game.Characters.GameUnit;

public class Healling implements Effectable {
    private int cyclesLeft;
    private int heal;

    public Healling() {
        cyclesLeft = 3;
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
}
