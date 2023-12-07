package Game.Units.Characters;

import Game.Actions;
import Game.Events.Arguments.Actions.ActionInfo;
import Game.Events.Arguments.Actions.EffectActionInfo;
import Game.Events.Arguments.Actions.HealActionInfo;
import Game.Effects.Effectable;
import Exceptions.InvalidActionException;
import Game.Effects.SharedDatas.UnitSharedData;

import java.util.Random;

public class Healer extends GameUnit implements Heallable {
    private final int heal;

    public Healer(UnitSharedData sharedData, int heal) {
        super(sharedData);

        this.heal = heal;
    }

    @Override
    public ActionInfo moveAI() throws InvalidActionException {
        Random random = new Random();
        int selectedAction = random.nextInt(1, 3);
        int index = random.nextInt(0, accessor.getEnemiesAccessor().getQuantity());

        GameUnit target = accessor.getEnemiesAccessor().getUnitByIndex(index);
        Actions action;
        if (selectedAction == 1) action = Actions.InstantHealing;
        else action = Actions.Healing;

        return act(target, action);
    }

    protected ActionInfo act(GameUnit target, Actions action) throws InvalidActionException {
        if (action.equals(Actions.InstantHealing)) {
            return instantHealing(target);
        } else if (action.equals(Actions.Healing)) {
            return healingEffect(target);
        }

        throw new InvalidActionException();
    }

    private ActionInfo instantHealing(GameUnit target) {
        int heal = getHeal();
        heal = target.heal(heal);
        statisticCollector.addHeal(this, heal);
        ActionInfo actionInfo = new HealActionInfo(this, target, Actions.Healing, heal);

        events.ActionPerformedEvent.invoke(actionInfo);

        return actionInfo;
    }

    private ActionInfo healingEffect(GameUnit target) {
        Effectable effect = getHealingEffect();
        target.takeEffect(effect);
        statisticCollector.addImposedEffect(this, effect.getEffectType());
        ActionInfo actionInfo = new EffectActionInfo(this, target, Actions.Healing, effect);

        events.ActionPerformedEvent.invoke(actionInfo);

        return actionInfo;
    }

    @Override
    public int getHeal() {
        return heal;
    }

    @Override
    public Effectable getHealingEffect() {
        return effectFactory.createHealing(this);
    }
}
