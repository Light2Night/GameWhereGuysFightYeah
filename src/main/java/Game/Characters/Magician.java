package Game.Characters;

import Game.Actions;
import Game.CharacterGetters.CompositeAccessor;
import Game.Effects.Effectable;
import Game.Effects.Poisoning;
import Game.Exceptions.InvalidActionException;
import Game.Move;
import Game.Teams.Team;
import Helpers.SafeInput;

import java.util.Random;

public class Magician extends GameUnit implements Attackable, Magicable {
    public Magician(CompositeAccessor accessor, Team team, int id) {
        super(accessor, team, id, "Маг", 100);
    }

    @Override
    public void move(Move move) throws InvalidActionException {
        if (!(move.getAction().equals(Actions.Attack) || move.getAction().equals(Actions.Poisoning)))
            throw new InvalidActionException();

        GameUnit target = accessor.getUnitsAccessor()
                .getUnitById(move.getTargetId());

        if (move.getAction().equals(Actions.Attack))
            target.takeDamage(getRandomDamage());
        else if (move.getAction().equals(Actions.Poisoning))
            target.takeEffect(getMagicalEffect());
    }

    @Override
    public void moveAI() {
        Random random = new Random();
        int selectedAction = random.nextInt(1, 3);
        int index = random.nextInt(0, accessor.getAlliesAccessor().getQuantity());

        GameUnit target = accessor.getAlliesAccessor().getUnitByIndex(index);

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
