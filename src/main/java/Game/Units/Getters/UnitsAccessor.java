package Game.Units.Getters;

import Game.Units.Characters.GameUnit;

import java.util.ArrayList;

public class UnitsAccessor implements Accessiable {
    private ArrayList<GameUnit> units;

    public UnitsAccessor(ArrayList<GameUnit> units) {
        this.units = units;
    }

    @Override
    public GameUnit getUnitByIndex(int index) {
        if (index < 0) throw new IndexOutOfBoundsException("Number is too small");

        if (index >= units.size()) throw new IndexOutOfBoundsException("Number is too large");

        return units.get(index);
    }

    @Override
    public GameUnit getUnitById(int id) {
        for (GameUnit unit : units) {
            if (unit.getId() == id) return unit;
        }

        throw new IndexOutOfBoundsException("Invalid id");
    }

    @Override
    public Boolean contains(GameUnit unit) {
        for (GameUnit u : units) {
            if (u == unit) return true;
        }

        return false;
    }

    @Override
    public Boolean containsId(int id) {
        for (GameUnit unit : units) {
            if (unit.getId() == id) return true;
        }
        return false;
    }

    @Override
    public int getQuantity() {
        return units.size();
    }
}
