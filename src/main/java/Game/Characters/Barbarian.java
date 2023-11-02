package Game.Characters;

import Game.Actions;
import Game.CharacterGetters.CompositeAccessor;
import Game.Exceptions.InvalidActionException;
import Game.Move;
import Game.Teams.Team;

import java.util.Random;

public class Barbarian extends GameUnit implements Attackable {
    public Barbarian(CompositeAccessor accessor, Team team, int id) {
        super(accessor,team, id, "Варвар", 200);
    }

    @Override
    public void move(Move move) throws InvalidActionException {
        if (!move.getAction().equals(Actions.Attack))
            throw new InvalidActionException();

        accessor.getUnitsAccessor()
                .getUnitById(move.getTargetId())
                .takeDamage(getRandomDamage());
    }

    @Override
    public void moveAI() {
        int index = new Random().nextInt(0, accessor.getAlliesAccessor().getQuantity());

        sleep(2000);
        System.out.printf("AI атакує по %d (%s)\n", index + 1, accessor.getAlliesAccessor().getUnitByIndex(index));
        sleep(2000);

        accessor.getUnitsAccessor()
                .getUnitByIndex(index)
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