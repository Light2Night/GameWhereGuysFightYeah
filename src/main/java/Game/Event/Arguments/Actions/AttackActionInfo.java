package Game.Event.Arguments.Actions;

import Game.Actions;
import Game.Characters.GameUnit;

public class AttackActionInfo extends ActionInfo {
    private int damage;

    public AttackActionInfo(GameUnit actor, GameUnit target, Actions action, int damage) {
        super(actor, target, action);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
