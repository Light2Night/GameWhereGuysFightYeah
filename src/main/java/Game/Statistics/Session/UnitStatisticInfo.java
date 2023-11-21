package Game.Statistics.Session;

public class UnitStatisticInfo {
    private int damage;

    public UnitStatisticInfo() {
        damage = 0;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public int getDamage() {
        return damage;
    }
}
