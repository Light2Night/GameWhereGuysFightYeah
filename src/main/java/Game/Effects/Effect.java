package Game.Effects;

import Game.Effects.SharedDatas.EffectSharedData;

public abstract class Effect implements Effectable {
    protected final EffectSharedData sharedData;

    public Effect(EffectSharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public Effect clone() throws CloneNotSupportedException {
        return (Effect) super.clone();
    }
}
