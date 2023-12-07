package ViewModels.Statistics.Session;

import Game.Effects.EffectTypes;
import Game.Units.Characters.GameUnit;

import java.util.ArrayList;

public class SessionStatisticVm {
    public int Damage;
    public int Heal;
    public ArrayList<GameUnit> DeadUnits;

    public int DeadUnitsCount() {
        return DeadUnits.size();
    }

    public ArrayList<EffectTypes> ImposedEffects;

    public int getImposedEffectsCount() {
        return ImposedEffects.size();
    }

    public int CyclesCount;

    public SessionStatisticVm() {
        this(0, 0, new ArrayList<>(), new ArrayList<>(), 0);
    }

    public SessionStatisticVm(int damage, int heal, ArrayList<GameUnit> deadUnits, ArrayList<EffectTypes> imposedEffects, int cyclesCount) {
        Damage = damage;
        Heal = heal;
        DeadUnits = deadUnits;
        ImposedEffects = imposedEffects;
        CyclesCount = cyclesCount;
    }
}
