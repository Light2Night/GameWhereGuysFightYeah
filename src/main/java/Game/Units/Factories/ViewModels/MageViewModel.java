package Game.Units.Factories.ViewModels;

import Game.Effects.Effectable;

public class MageViewModel extends BaseUnitViewModel {
    public final int Damage;
    public final int DamageDelta;
    public final Effectable PoisoningEffect;

    public MageViewModel(String name, int hp, int maxHP, int damage, int damageDelta, Effectable poisoningEffect) {
        super(name, hp, maxHP);
        Damage = damage;
        DamageDelta = damageDelta;
        PoisoningEffect = poisoningEffect;
    }
}