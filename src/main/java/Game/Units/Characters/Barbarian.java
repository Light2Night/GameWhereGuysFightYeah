package Game.Units.Characters;

import Game.Actions;
import Game.Events.Arguments.Actions.ActionInfo;
import Game.Events.Arguments.Actions.AttackActionInfo;
import Exceptions.InvalidActionException;
import Game.Effects.SharedDatas.UnitSharedData;

import java.util.Random;

public class Barbarian extends GameUnit implements Attackable {
    private final int damage;
    private final int damageDelta;

    public Barbarian(UnitSharedData sharedData, int damage, int damageDelta) {
        super(sharedData);
        this.damage = damage;
        this.damageDelta = damageDelta;
    }

    @Override
    public ActionInfo moveAI() throws InvalidActionException {
        int index = new Random().nextInt(0, accessor.getAlliesAccessor().getQuantity());

        GameUnit target = accessor.getUnitsAccessor().getUnitByIndex(index);
        return act(target, Actions.Attack);
    }

    protected ActionInfo act(GameUnit target, Actions action) throws InvalidActionException {
        if (action.equals(Actions.Attack)) {
            int damage = getRandomDamage();
            damage = target.takeDamage(damage);
            statisticCollector.addDamage(this, damage);
            ActionInfo actionInfo = new AttackActionInfo(this, target, Actions.Attack, damage);

            events.ActionPerformedEvent.invoke(actionInfo);

            return actionInfo;
        }

        throw new InvalidActionException();
    }

    private int getRandomDamage() {
        return new Random().nextInt(getDamage() - getDamageDelta(), getDamage() + getDamageDelta() + 1);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getDamageDelta() {
        return damageDelta;
    }
}