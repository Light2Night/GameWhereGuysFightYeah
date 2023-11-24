package Game.Effects;

import Game.Units.Characters.GameUnit;

public interface Effectable {
    EffectTypes getEffectType();
    void effect(GameUnit unit);

    int getCyclesLeft();

    int getDurationInCycles();

    Effectable copy();
}
