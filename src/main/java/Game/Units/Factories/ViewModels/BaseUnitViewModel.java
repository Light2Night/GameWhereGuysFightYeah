package Game.Units.Factories.ViewModels;

public abstract class BaseUnitViewModel {
    public final String Name;
    public final int HP;
    public final int MaxHP;

    public BaseUnitViewModel(String name, int hp, int maxHP) {
        Name = name;
        HP = hp;
        this.MaxHP = maxHP;
    }
}
