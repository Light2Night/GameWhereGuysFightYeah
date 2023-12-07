package Game.Effects;

import Game.Units.Characters.GameUnit;

public interface Effectable extends Cloneable {
    EffectTypes getType();

    void effect(GameUnit target);

    int getCyclesLeft();

    int getDurationInCycles();

    Effectable clone() throws CloneNotSupportedException;
}
