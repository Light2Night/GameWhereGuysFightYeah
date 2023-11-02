package Game.CharacterGetters;

import Game.Characters.GameUnit;
import Game.Teams.Team;

import java.util.ArrayList;

public class TeamAccessor implements Accessiable {
    private ArrayList<GameUnit> units;
    private Team team;

    public TeamAccessor(ArrayList<GameUnit> units, Team team) {
        this.units = units;
        this.team = team;
    }

    private ArrayList<GameUnit> getUnitsOnThisTeam() {
        return units.stream().filter(unit -> unit.getTeam() == team).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public GameUnit getUnitByIndex(int index) {
        if (index < 0) throw new IndexOutOfBoundsException("Number is too small");

        ArrayList<GameUnit> units = getUnitsOnThisTeam();

        if (index < units.size()) {
            return units.get(index);
        }

        throw new IndexOutOfBoundsException("Number is too large");
    }

    @Override
    public GameUnit getUnitById(int id) {
        for (GameUnit ally : getUnitsOnThisTeam()) {
            if (ally.getId() == id) return ally;
        }
        throw new IndexOutOfBoundsException("Invalid id");
    }

    @Override
    public Boolean contains(GameUnit unit) {
        for (GameUnit ally : getUnitsOnThisTeam()) {
            if (ally == unit) return true;
        }
        return false;
    }

    @Override
    public Boolean containsId(int id) {
        for (GameUnit ally : getUnitsOnThisTeam()) {
            if (ally.getId() == id) return true;
        }
        return false;
    }

    @Override
    public int getQuantity() {
        return getUnitsOnThisTeam().size();
    }
}

