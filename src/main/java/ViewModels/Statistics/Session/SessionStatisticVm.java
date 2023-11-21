package ViewModels.Statistics.Session;

import Game.Effects.EffectTypes;
import Game.Units.Characters.GameUnit;

import java.util.ArrayList;

public class SessionStatisticVm {
    public int Damage;
    public ArrayList<GameUnit> DeadUnits;

    public int DeadUnitsCount() {
        return DeadUnits.size();
    }

    public ArrayList<EffectTypes> ImposedEffects;

    public int getImposedEffectsCount() {
        return ImposedEffects.size();
    }

    public int CyclesCount;

    public SessionStatisticVm(int damage, ArrayList<GameUnit> deadUnits, ArrayList<EffectTypes> imposedEffects, int cyclesCount) {
        Damage = damage;
        DeadUnits = deadUnits;
        ImposedEffects = imposedEffects;
        this.CyclesCount = cyclesCount;
    }

    public SessionStatisticVm() {
        this(0, new ArrayList<>(), new ArrayList<>(), 0);
    }
}
