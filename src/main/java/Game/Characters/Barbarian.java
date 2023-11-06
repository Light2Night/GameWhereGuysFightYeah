package Game.Characters;

import Game.Actions;
import Game.Event.Aggregates.UnitEventsAggregate;
import Game.Event.Arguments.Actions.AttackActionInfo;
import Game.UnitGetters.CompositeAccessor;
import Game.Exceptions.InvalidActionException;
import Game.Move;
import Game.Teams.Team;

import java.util.Random;

public class Barbarian extends GameUnit implements Attackable {
    public Barbarian(CompositeAccessor accessor, UnitEventsAggregate events, Team team, int id) {
        super(accessor, events, team, "Варвар", 200, id);
    }

    @Override
    public void move(Move move) throws InvalidActionException {
        if (!move.getAction().equals(Actions.Attack))
            throw new InvalidActionException();

        GameUnit target = accessor.getUnitsAccessor().getUnitById(move.getTargetId());
        attack(target);
    }

    @Override
    public void moveAI() {
        int index = new Random().nextInt(0, accessor.getAlliesAccessor().getQuantity());

        System.out.printf("AI атакує по %d (%s)\n", index + 1, accessor.getAlliesAccessor().getUnitByIndex(index));

        GameUnit target = accessor.getUnitsAccessor().getUnitByIndex(index);
        attack(target);
    }

    private void attack(GameUnit target) {
        int damage = getRandomDamage();
        target.takeDamage(damage);
        events.actionPerformed(new AttackActionInfo(this, target, Actions.Attack, damage));
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