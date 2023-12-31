package Game.Events.Arguments.Actions;

import Game.Actions;
import Game.Units.Characters.GameUnit;

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
