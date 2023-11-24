package Game.Units.Characters;

import Exceptions.InvalidActionException;
import Game.Actions;
import Game.Effects.Effectable;
import Game.Events.Arguments.Actions.ActionInfo;
import Game.Events.Arguments.Actions.AttackActionInfo;
import Game.Events.Arguments.Actions.EffectActionInfo;
import Game.Units.UnitSharedData;

import java.util.Random;

public class Magician extends GameUnit implements Attackable, Magicable {
    public final int damage;
    public final int damageDelta;

    public Magician(UnitSharedData sharedData, int damage, int damageDelta) {
        super(sharedData);

        this.damage = damage;
        this.damageDelta = damageDelta;
    }

    @Override
    public ActionInfo moveAI() throws InvalidActionException {
        Random random = new Random();
        int selectedAction = random.nextInt(1, 3);
        int index = random.nextInt(0, accessor.getAlliesAccessor().getQuantity());

        GameUnit target = accessor.getAlliesAccessor().getUnitByIndex(index);
        Actions action;
        if (selectedAction == 1) action = Actions.Attack;
        else action = Actions.Poisoning;

        return act(target, action);
    }

    protected ActionInfo act(GameUnit target, Actions action) throws InvalidActionException {
        if (action.equals(Actions.Attack)) {
            return attack(target);
        } else if (action.equals(Actions.Poisoning)) {
            return poisoning(target);
        }

        throw new InvalidActionException();
    }

    private ActionInfo attack(GameUnit target) {
        int damage = getRandomDamage();
        damage = target.takeDamage(damage);
        statisticCollector.addDamage(this, damage);
        ActionInfo actionInfo = new AttackActionInfo(this, target, Actions.Attack, damage);
        events.ActionPerformedEvent.invoke(actionInfo);
        return actionInfo;
    }

    private ActionInfo poisoning(GameUnit target) {
        Effectable effect = getMagicalEffect();
        target.takeEffect(effect);
        statisticCollector.addImposedEffect(this, effect.getEffectType());
        ActionInfo actionInfo = new EffectActionInfo(this, target, Actions.Poisoning, effect);
        events.ActionPerformedEvent.invoke(actionInfo);
        return actionInfo;
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

    @Override
    public Effectable getMagicalEffect() {
        return effectFactory.createPoisoning(this);
    }
}
