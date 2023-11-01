package Game.CharacterGetters;

import Game.Characters.GameUnit;

public interface Accessiable {
    GameUnit getUnit(int index);
    int getQuantity();
}
