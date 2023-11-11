package Game.Effects;

import Game.Units.Characters.GameUnit;

public interface Effectable {
    void effect(GameUnit unit);

    int getCyclesLeft();

    int getDurationInCycles();
}
