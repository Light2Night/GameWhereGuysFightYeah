package ViewModels.Recruit;

import Game.Characters.UnitTypes;

public class BarbarianViewModel extends BaseRecruitViewModel {
    public final int Damage;
    public final int DamageDelta;

    public BarbarianViewModel() {
        super(UnitTypes.BARBARIAN, 200);

        Damage = 15;
        DamageDelta = 10;
    }
}
