package Game.Characters;

import Game.CharacterGetters.CompositeAccessor;
import Game.Effects.Effectable;
import Game.Effects.Healling;
import Helpers.SafeInput;

import java.util.Random;

public class Healer extends GameUnit implements Heallable {
    public Healer() {
        super("Цілитель", 125);
    }

    @Override
    public void Move(CompositeAccessor accessor) {
        int selectedAction;
        do {
            System.out.println("Вибір дії:");
            System.out.println("1. Миттєве лікування");
            System.out.println("2. Накласти лікувальний ефект");
            selectedAction = SafeInput.getInt();
        } while (!(1 <= selectedAction && selectedAction <= 2));

        System.out.println("Кого лікувати?");

        int number = inputUnitNumber(accessor.getUnitsAccessor());

        GameUnit target = accessor.getUnitsAccessor().getUnit(number - 1);

        if (selectedAction == 1) target.heal(getHeal());
        else if (selectedAction == 2) target.takeEffect(getHealingEffect());
    }

    @Override
    public void MoveAI(CompositeAccessor accessor) {
        Random random = new Random();
        int selectedAction = random.nextInt(1, 3);
        int index = random.nextInt(0, accessor.getEnemiesAccessor().getQuantity());

        GameUnit target = accessor.getEnemiesAccessor().getUnit(index);

        sleep(2000);
        System.out.printf("AI %s %d (%s)\n", selectedAction == 1 ? "миттєво лікує" : "накладає лікувальний ефект на юніта", accessor.getAlliesAccessor().getQuantity() + index + 1, target.toString());
        sleep(3000);

        if (selectedAction == 1) target.heal(getHeal());
        else if (selectedAction == 2) target.takeEffect(getHealingEffect());
    }

    @Override
    public int getHeal() {
        return 20;
    }

    @Override
    public Effectable getHealingEffect() {
        return new Healling();
    }
}
