package Game.Units.Getters;

public class CompositeAccessor {
    private TeamAccessor alliesAccessor;
    private TeamAccessor enemiesAccessor;
    private UnitsAccessor unitsAccessor;

    public CompositeAccessor(TeamAccessor alliesAccessor, TeamAccessor enemiesAccessor, UnitsAccessor unitsAccessor) {
        this.alliesAccessor = alliesAccessor;
        this.enemiesAccessor = enemiesAccessor;
        this.unitsAccessor = unitsAccessor;
    }

    public TeamAccessor getAlliesAccessor() {
        return alliesAccessor;
    }

    public TeamAccessor getEnemiesAccessor() {
        return enemiesAccessor;
    }

    public UnitsAccessor getUnitsAccessor() {
        return unitsAccessor;
    }
}
