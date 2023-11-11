package Game.Event.Arguments.Actions;

import Game.Actions;
import Game.Units.Characters.GameUnit;

public class HealActionInfo extends ActionInfo {
    private int healed;

    public HealActionInfo(GameUnit actor, GameUnit target, Actions action, int healed) {
        super(actor, target, action);
        this.healed = healed;
    }

    public int getHealed() {
        return healed;
    }
}
