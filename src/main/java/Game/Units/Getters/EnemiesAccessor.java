package Game.Units.Getters;

import Game.Units.Characters.GameUnit;

import java.util.ArrayList;

public class EnemiesAccessor implements Accessiable {
    private ArrayList<GameUnit> enemies;

    public EnemiesAccessor(ArrayList<GameUnit> allies) {
        this.enemies = allies;
    }

    @Override
    public GameUnit getUnitByIndex(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Number is too small");

        if (index < enemies.size()) {
            return enemies.get(index);
        }

        throw new IndexOutOfBoundsException("Number is too large");
    }

    @Override
    public GameUnit getUnitById(int id) {
        for (GameUnit enemy : enemies) {
            if (enemy.getId() == id)
                return enemy;
        }
        throw new IndexOutOfBoundsException("Invalid id");
    }

    @Override
    public Boolean contains(GameUnit unit) {
        for (GameUnit enemy : enemies) {
            if (enemy == unit)
                return true;
        }
        return false;
    }
    @Override
    public Boolean containsId(int id) {
        for (GameUnit ally : enemies) {
            if (ally.getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public int getQuantity() {
        return enemies.size();
    }
}

