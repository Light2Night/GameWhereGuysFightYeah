package ViewModels.Recruit;

import Game.Characters.UnitTypes;

public abstract class BaseRecruitViewModel {
    public final UnitTypes Type;
    public final int MaxHP;

    public BaseRecruitViewModel(UnitTypes type, int maxHP) {
        this.Type = type;
        this.MaxHP = maxHP;
    }
}
