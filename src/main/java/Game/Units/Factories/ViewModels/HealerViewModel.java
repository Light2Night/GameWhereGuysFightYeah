package Game.Units.Factories.ViewModels;

import Game.Effects.Effectable;
import Game.Units.Characters.UnitTypes;

public class HealerViewModel extends BaseUnitViewModel {
    public final int Heal;
    public final Effectable HeallingEffect;

    public HealerViewModel(String name, int hp, int maxHP, int heal, Effectable heallingEffect) {
        super(name, hp, maxHP);
        Heal = heal;
        HeallingEffect = heallingEffect;
    }
}
