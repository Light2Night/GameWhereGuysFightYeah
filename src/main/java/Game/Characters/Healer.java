package Game.Characters;

import Game.Actions;
import Game.CharacterGetters.CompositeAccessor;
import Game.Effects.Effectable;
import Game.Effects.Healling;
import Game.Exceptions.InvalidActionException;
import Game.Move;
import Helpers.SafeInput;

import java.util.Random;

public class Healer extends GameUnit implements Heallable {
    public Healer(CompositeAccessor accessor, int id) {
        super(accessor, id, "Цілитель", 125);
    }

    @Override
    public void move(Move move) throws InvalidActionException {
        if (!(move.getAction().equals(Actions.Healing) || move.getAction().equals(Actions.InstantHealing)))
            throw new InvalidActionException();

        GameUnit target = accessor.getUnitsAccessor().getUnitById(move.getTargetId());

        if (move.getAction().equals(Actions.InstantHealing)) target.heal(getHeal());
        else if (move.getAction().equals(Actions.Healing)) target.takeEffect(getHealingEffect());
    }

    @Override
    public void moveAI() {
        Random random = new Random();
        int selectedAction = random.nextInt(1, 3);
        int index = random.nextInt(0, accessor.getEnemiesAccessor().getQuantity());

        GameUnit target = accessor.getEnemiesAccessor().getUnitByIndex(index);

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
