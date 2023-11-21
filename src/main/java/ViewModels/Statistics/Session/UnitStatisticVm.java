package ViewModels.Statistics.Session;

import Game.Effects.EffectTypes;
import Game.Statistics.Session.DataCollectors.UnitStatisticCollector;

import java.util.ArrayList;

public class UnitStatisticVm {
    public int Damage;
    public boolean IdDied;
    public ArrayList<EffectTypes> ImposedEffects;

    public int getImposedEffectsCount() {
        return ImposedEffects.size();
    }

    public UnitStatisticVm(int damage, boolean idDied, ArrayList<EffectTypes> imposedEffects) {
        Damage = damage;
        IdDied = idDied;
        ImposedEffects = imposedEffects;
    }

    public UnitStatisticVm() {
        this(0, false, new ArrayList<>());
    }

    public UnitStatisticVm(UnitStatisticCollector unitStatisticCollector) {
        this(
                unitStatisticCollector.getDamage(),
                unitStatisticCollector.getDied(),
                unitStatisticCollector.getImposedEffects()
        );
    }
}
