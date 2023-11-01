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
    public GameUnit getUnit(int index) {
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
    public int getQuantity() {
        return allies.size() + enemies.size();
    }
}
