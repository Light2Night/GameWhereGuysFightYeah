package ViewModels.Recruit;

import Game.Characters.UnitTypes;

public class HealerViewModel extends BaseRecruitViewModel {
    public final int Heal;
    public final int HealingEffectHeal;
    public final int HealingEffectTurns;

    public HealerViewModel() {
        super(UnitTypes.HEALER, 125);

        Heal = 20;
        HealingEffectHeal = 10;
        HealingEffectTurns = 3;
    }
}
