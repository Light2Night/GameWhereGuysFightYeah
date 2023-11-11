package Game.Units.Factories.ViewModels;

import Game.Units.Characters.UnitTypes;

public class HealerViewModel extends BaseUnitViewModel {
    public final int Heal;

    public HealerViewModel(String name, int hp, int maxHP, int heal) {
        super(name, hp, maxHP);
        Heal = heal;
    }
}
