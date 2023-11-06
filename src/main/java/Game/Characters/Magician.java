package Game.Characters;

import Game.Actions;
import Game.Event.Aggregates.UnitEventsAggregate;
import Game.Event.Arguments.Actions.AttackActionInfo;
import Game.Event.Arguments.Actions.EffectActionInfo;
import Game.UnitGetters.CompositeAccessor;
import Game.Effects.Effectable;
import Game.Effects.Poisoning;
import Game.Exceptions.InvalidActionException;
import Game.Move;
import Game.Teams.Team;

import java.util.Random;

public class Magician extends GameUnit implements Attackable, Magicable {
    public Magician(CompositeAccessor accessor, UnitEventsAggregate events, Team team, int id) {
        super(accessor, events, team, "Маг", 100, id);
    }

    @Override
    public void move(Move move) throws InvalidActionException {
        if (!(move.getAction().equals(Actions.Attack) || move.getAction().equals(Actions.Poisoning)))
            throw new InvalidActionException();

        GameUnit target = accessor.getUnitsAccessor().getUnitById(move.getTargetId());
        attack(target, move.getAction());
    }

    @Override
    public void moveAI() {
        Random random = new Random();
        int selectedAction = random.nextInt(1, 3);
        int index = random.nextInt(0, accessor.getAlliesAccessor().getQuantity());

        GameUnit target = accessor.getAlliesAccessor().getUnitByIndex(index);
        Actions action;
        if (selectedAction == 1) action = Actions.Attack;
        else action = Actions.Poisoning;

        System.out.printf("AI %s %d (%s)\n", selectedAction == 1 ? "атакує магією по" : "накладає отруєння на юніта", index + 1, target.toString());
        attack(target, action);
    }

    private void attack(GameUnit target, Actions action) {
        if (action.equals(Actions.Attack)) {
            int damage = getRandomDamage();
            target.takeDamage(damage);
            events.actionPerformed(new AttackActionInfo(this, target, Actions.Attack, damage));
        } else if (action.equals(Actions.Poisoning)) {
            Effectable effect = getMagicalEffect();
            target.takeEffect(effect);
            events.actionPerformed(new EffectActionInfo(this, target, Actions.Poisoning, effect));
        }
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
