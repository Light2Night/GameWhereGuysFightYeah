package Game.Events.Arguments.Actions;

import Game.Actions;
import Game.Units.Characters.GameUnit;
import Game.Effects.Effectable;

public class EffectActionInfo extends ActionInfo {
    private Effectable effect;

    public EffectActionInfo(GameUnit actor, GameUnit target, Actions action, Effectable effect) {
        super(actor, target, action);
        this.effect = effect;
    }

    public Effectable getEffect() {
        return effect;
    }
}
