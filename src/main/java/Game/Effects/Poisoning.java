package Game.Effects;

import Game.Characters.GameUnit;

public class Poisoning implements Effectable {
    private int cyclesLeft;
    private int damage;

    public Poisoning() {
        cyclesLeft = 2;
        damage = 15;
    }

    @Override
    public void effect(GameUnit unit) {
        if (cyclesLeft == 0)
            return;

        cyclesLeft--;

        System.out.printf("Юніт (%s) втрачає %d здоров'я від отруєння\n", unit.toString(), damage);

        unit.takeDamage(damage);

        if (cyclesLeft > 0) {
            System.out.printf("Отруєння діятиме ще циклів: %d\n", cyclesLeft);
        }
    }

    @Override
    public int getCyclesLeft() {
        return cyclesLeft;
    }
}
