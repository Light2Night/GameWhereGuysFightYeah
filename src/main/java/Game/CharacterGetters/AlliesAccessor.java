package Game.CharacterGetters;

import Game.Characters.GameUnit;

import java.util.ArrayList;

public class AlliesAccessor implements Accessiable {
    private ArrayList<GameUnit> allies;

    public AlliesAccessor(ArrayList<GameUnit> allies) {
        this.allies = allies;
    }

    @Override
    public GameUnit getUnit(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Number is too small");

        if (index < allies.size()) {
            return allies.get(index);
        }

        throw new IndexOutOfBoundsException("Number is too large");
    }

    @Override
    public int getQuantity() {
        return allies.size();
    }
}

