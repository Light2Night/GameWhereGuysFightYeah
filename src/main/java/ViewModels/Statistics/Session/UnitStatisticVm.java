package ViewModels.Statistics.Session;

import Game.Effects.EffectTypes;
import Game.Statistics.Session.DataCollectors.UnitStatisticCollector;

import java.util.ArrayList;

public class UnitStatisticVm {
    public int Damage;
    public int Heal;
    public int ReceivedDamage;
    public int ReceivedHeal;
    public boolean IdDied;
    public ArrayList<EffectTypes> ImposedEffects;

    public int getImposedEffectsCount() {
        return ImposedEffects.size();
    }

    public ArrayList<EffectTypes> ReceivedEffects;

    public int getReceivedEffectsCount() {
        return ReceivedEffects.size();
    }

    public UnitStatisticVm(int damage, int heal, int receivedDamage, int receivedHeal, boolean idDied, ArrayList<EffectTypes> imposedEffects, ArrayList<EffectTypes> receivedEffects) {
        Damage = damage;
        Heal = heal;
        ReceivedDamage = receivedDamage;
        ReceivedHeal = receivedHeal;
        IdDied = idDied;
        ImposedEffects = imposedEffects;
        ReceivedEffects = receivedEffects;
    }

    public UnitStatisticVm() {
        this(0, 0, 0, 0, false, new ArrayList<>(), new ArrayList<>());
    }

    public UnitStatisticVm(UnitStatisticCollector collector) {
        this(
                collector.getDamage(),
                collector.getHeal(),
                collector.getReceivedDamage(),
                collector.getReceivedHeal(),
                collector.getDied(),
                collector.getImposedEffects(),
                collector.getReceivedEffects()
        );
    }
}
