package Game.CharacterGetters;

public class CompositeAccessor {
    private AlliesAccessor alliesAccessor;
    private EnemiesAccessor enemiesAccessor;
    private UnitsAccessor unitsAccessor;

    public CompositeAccessor(AlliesAccessor alliesAccessor, EnemiesAccessor enemiesAccessor, UnitsAccessor unitsAccessor) {
        this.alliesAccessor = alliesAccessor;
        this.enemiesAccessor = enemiesAccessor;
        this.unitsAccessor = unitsAccessor;
    }

    public AlliesAccessor getAlliesAccessor() {
        return alliesAccessor;
    }

    public EnemiesAccessor getEnemiesAccessor() {
        return enemiesAccessor;
    }

    public UnitsAccessor getUnitsAccessor() {
        return unitsAccessor;
    }
}
