package ViewModels.Statistics.Session;

import Game.Effects.EffectTypes;
import Game.Units.Characters.GameUnit;

import java.util.ArrayList;

public class TeamStatisticVm {
    public int Damage;
    public ArrayList<GameUnit> DeadUnits;

    public int DeadUnitsCount() {
        return DeadUnits.size();
    }

    public ArrayList<EffectTypes> ImposedEffects;

    public int getImposedEffectsCount() {
        return ImposedEffects.size();
    }

    public TeamStatisticVm(int damage, ArrayList<GameUnit> deadUnits, ArrayList<EffectTypes> imposedEffects) {
        Damage = damage;
        this.DeadUnits = deadUnits;
        ImposedEffects = imposedEffects;
    }

    public TeamStatisticVm() {
        this(0, new ArrayList<>(), new ArrayList<>());
    }
}
