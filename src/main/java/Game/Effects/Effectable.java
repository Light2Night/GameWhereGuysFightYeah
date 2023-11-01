package Game.Effects;

import Game.Characters.GameUnit;

public interface Effectable {
    void effect(GameUnit unit);

    int getCyclesLeft();
}
