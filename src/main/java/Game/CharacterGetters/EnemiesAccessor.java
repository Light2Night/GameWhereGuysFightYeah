package Game.CharacterGetters;

import Game.Characters.GameUnit;

import java.util.ArrayList;

public class EnemiesAccessor implements Accessiable {
    private ArrayList<GameUnit> enemies;

    public EnemiesAccessor(ArrayList<GameUnit> allies) {
        this.enemies = allies;
    }

    @Override
    public GameUnit getUnit(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Number is too small");

        if (index < enemies.size()) {
            return enemies.get(index);
        }

        throw new IndexOutOfBoundsException("Number is too large");
    }

    @Override
    public int getQuantity() {
        return enemies.size();
    }
}

