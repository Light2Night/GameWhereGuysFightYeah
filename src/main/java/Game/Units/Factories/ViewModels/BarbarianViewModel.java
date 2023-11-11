package Game.Units.Factories.ViewModels;

public class BarbarianViewModel extends BaseUnitViewModel {
    public final int Damage;
    public final int DamageDelta;

    public BarbarianViewModel(String name, int hp, int maxHp, int damage, int damageDelta) {
        super(name, hp, maxHp);
        Damage = damage;
        DamageDelta = damageDelta;
    }
}
