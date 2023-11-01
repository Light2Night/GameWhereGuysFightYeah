package Game.CharacterGetters;

import Game.Characters.GameUnit;

import java.util.ArrayList;

public class UnitsAccessor implements Accessiable {
    private ArrayList<GameUnit> allies;
    private ArrayList<GameUnit> enemies;

    public UnitsAccessor(ArrayList<GameUnit> allies, ArrayList<GameUnit> enemies) {
        this.allies = allies;
        this.enemies = enemies;
    }

    @Override
    public GameUnit getUnitByIndex(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Number is too small");

        if (index < allies.size()) {
            return allies.get(index);
        }

        index -= allies.size();
        if (index < enemies.size()) {
            return enemies.get(index);
        }

        throw new IndexOutOfBoundsException("Number is too large");
    }

    @Override
    public GameUnit getUnitById(int id) {
        for (GameUnit ally : allies) {
            if (ally.getId() == id)
                return ally;
        }

        for (GameUnit enemy : enemies) {
            if (enemy.getId() == id)
                return enemy;
        }

        throw new IndexOutOfBoundsException("Invalid id");
    }

    @Override
    public Boolean contains(GameUnit unit) {
        for (GameUnit ally : allies) {
            if (ally == unit)
                return true;
        }

        for (GameUnit enemy : enemies) {
            if (enemy == unit)
                return true;
        }
        return false;
    }

    @Override
    public Boolean containsId(int id) {
        for (GameUnit ally : allies) {
            if (ally.getId() == id)
                return true;
        }
        for (GameUnit enemy : enemies) {
            if (enemy.getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public int getQuantity() {
        return allies.size() + enemies.size();
    }
}
