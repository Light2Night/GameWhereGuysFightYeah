package Game.Characters;

import Game.CharacterGetters.CompositeAccessor;
import Game.Effects.Effectable;
import Game.Effects.Poisoning;
import Helpers.SafeInput;

import java.util.Random;

public class Magician extends GameUnit implements Attackable, Magicable {
    public Magician() {
        super("Маг", 100);
    }

    @Override
    public void Move(CompositeAccessor accessor) {
        int selectedAction;
        do {
            System.out.println("Вибір дії:");
            System.out.println("1. Магічна атака");
            System.out.println("2. Накласти отруєння");
            selectedAction = SafeInput.getInt();
        } while (!(1 <= selectedAction && selectedAction <= 2));

        System.out.println("Кого атакувати?");

        int number = inputUnitNumber(accessor.getUnitsAccessor());

        GameUnit target = accessor.getUnitsAccessor()
                .getUnit(number - 1);

        if (selectedAction == 1)
            target.takeDamage(getRandomDamage());
        else if (selectedAction == 2)
            target.takeEffect(getMagicalEffect());
    }

    @Override
    public void MoveAI(CompositeAccessor accessor) {
        Random random = new Random();
        int selectedAction = random.nextInt(1, 3);
        int index = random.nextInt(0, accessor.getAlliesAccessor().getQuantity());

        GameUnit target = accessor.getAlliesAccessor().getUnit(index);

        sleep(2000);
        System.out.printf("AI %s %d (%s)\n", selectedAction == 1 ? "атакує магією по" : "накладає отруєння на юніта", index + 1, target.toString());
        sleep(3000);

        if (selectedAction == 1) target.takeDamage(getRandomDamage());
        else if (selectedAction == 2) target.takeEffect(getMagicalEffect());
    }

    private int getRandomDamage() {
        return new Random().nextInt(getDamage() - getDamageDelta(), getDamage() + getDamageDelta() + 1);
    }

    @Override
    public int getDamage() {
        return 25;
    }

    @Override
    public int getDamageDelta() {
        return 20;
    }

    @Override
    public Effectable getMagicalEffect() {
        return new Poisoning();
    }
}
