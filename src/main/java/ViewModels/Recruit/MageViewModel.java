package ViewModels.Recruit;

import Game.Characters.UnitTypes;

public class MageViewModel extends BaseRecruitViewModel {
    public final int Damage;
    public final int DamageDelta;
    public final int MagicalEffectDamage;
    public final int MagicalEffectTurns;

    public MageViewModel(UnitTypes type, int maxHP) {
        super(UnitTypes.MAGICIAN, 100);

        Damage = 25;
        DamageDelta = 20;
        MagicalEffectDamage = 15;
        MagicalEffectTurns = 2;
    }
}