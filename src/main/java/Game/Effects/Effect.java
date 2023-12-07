package Game.Effects;

import Game.Effects.SharedDatas.EffectSharedData;
import Game.Units.Characters.GameUnit;

public abstract class Effect implements Effectable {
    protected final EffectSharedData sharedData;
    protected final GameUnit creator;

    public Effect(EffectSharedData sharedData, GameUnit creator) {
        this.sharedData = sharedData;
        this.creator = creator;
    }

    @Override
    public Effect clone() throws CloneNotSupportedException {
        return (Effect) super.clone();
    }
}
