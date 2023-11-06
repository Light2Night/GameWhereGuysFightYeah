package Game.Characters;

import Game.Actions;
import Game.Event.Aggregates.UnitEventsAggregate;
import Game.Event.Arguments.Actions.EffectActionInfo;
import Game.Event.Arguments.Actions.HealActionInfo;
import Game.UnitGetters.CompositeAccessor;
import Game.Effects.Effectable;
import Game.Effects.Healling;
import Game.Exceptions.InvalidActionException;
import Game.Move;
import Game.Teams.Team;

import java.util.Random;

public class Healer extends GameUnit implements Heallable {
    public Healer(CompositeAccessor accessor, UnitEventsAggregate events, Team team, int id) {
        super(accessor, events, team, "Цілитель", 125, id);
    }

    @Override
    public void move(Move move) throws InvalidActionException {
        if (!(move.getAction().equals(Actions.Healing) || move.getAction().equals(Actions.InstantHealing)))
            throw new InvalidActionException();

        GameUnit target = accessor.getUnitsAccessor().getUnitById(move.getTargetId());
        heal(target, move.getAction());
    }

    @Override
    public void moveAI() {
        Random random = new Random();
        int selectedAction = random.nextInt(1, 3);
        int index = random.nextInt(0, accessor.getEnemiesAccessor().getQuantity());

        GameUnit target = accessor.getEnemiesAccessor().getUnitByIndex(index);
        Actions action;
        if (selectedAction == 1) action = Actions.InstantHealing;
        else action = Actions.Healing;

        System.out.printf("AI %s %d (%s)\n", selectedAction == 1 ? "миттєво лікує" : "накладає лікувальний ефект на юніта", accessor.getAlliesAccessor().getQuantity() + index + 1, target.toString());
        heal(target, action);
    }

    private void heal(GameUnit target, Actions action) {
        if (action.equals(Actions.InstantHealing)) {
            int heal = getHeal();
            target.heal(heal);
            events.actionPerformed(new HealActionInfo(this, target, Actions.Healing, heal));
        } else if (action.equals(Actions.Healing)) {
            Effectable effect = getHealingEffect();
            target.takeEffect(effect);
            events.actionPerformed(new EffectActionInfo(this, target, Actions.Healing, effect));
        }
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
