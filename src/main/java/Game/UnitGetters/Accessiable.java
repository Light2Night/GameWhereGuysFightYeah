package Game.UnitGetters;

import Game.Characters.GameUnit;

public interface Accessiable {
    GameUnit getUnitByIndex(int index);
    GameUnit getUnitById(int id);
    Boolean contains(GameUnit unit);
    Boolean containsId(int id);
    int getQuantity();
}
