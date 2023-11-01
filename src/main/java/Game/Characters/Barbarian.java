package Game.Characters;

import Game.CharacterGetters.CompositeAccessor;
import Helpers.SafeInput;

import java.util.Random;

public class Barbarian extends GameUnit implements Attackable {
    public Barbarian() {
        super("Варвар", 200);
    }

    @Override
    public void Move(CompositeAccessor accessor) {
        System.out.println("Кого атакувати?");

        int number;
        while (true) {
            number = SafeInput.getInt();
            if (0 < number && number <= accessor.getUnitsAccessor().getQuantity()) {
                break;
            }
            System.out.println("Некоректний номер юніта");
        }

        accessor.getUnitsAccessor()
                .getUnit(number - 1)
                .takeDamage(getRandomDamage());
    }

    @Override
    public void MoveAI(CompositeAccessor accessor) {
        int index = new Random().nextInt(0, accessor.getAlliesAccessor().getQuantity());

        sleep(2000);
        System.out.printf("AI атакує по %d (%s)\n", index + 1, accessor.getAlliesAccessor().getUnit(index));
        sleep(2000);

        accessor.getUnitsAccessor()
                .getUnit(index)
                .takeDamage(getRandomDamage());
    }

    private int getRandomDamage() {
        return new Random().nextInt(getDamage() - getDamageDelta(), getDamage() + getDamageDelta() + 1);
    }

    @Override
    public int getDamage() {
        return 15;
    }

    @Override
    public int getDamageDelta() {
        return 10;
    }
}
