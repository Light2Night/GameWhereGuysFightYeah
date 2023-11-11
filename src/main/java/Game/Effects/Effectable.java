package Game.Effects;

import Game.Units.Characters.GameUnit;

public interface Effectable extends Cloneable {
    void effect(GameUnit unit);

    int getCyclesLeft();

    int getDurationInCycles();

    Effectable clone();
}
