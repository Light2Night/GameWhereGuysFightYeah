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

    public UnitStatisticVm(int damage, int heal, int receivedDamage, int receivedHeal, boolean idDied, ArrayList<EffectTypes> imposedEffects) {
        Damage = damage;
        Heal = heal;
        ReceivedDamage = receivedDamage;
        ReceivedHeal = receivedHeal;
        IdDied = idDied;
        ImposedEffects = imposedEffects;
    }

    public UnitStatisticVm() {
        this(0, 0, 0, 0, false, new ArrayList<>());
    }

    public UnitStatisticVm(UnitStatisticCollector collector) {
        this(
                collector.getDamage(),
                collector.getHeal(),
                collector.getDamageReceived(),
                collector.getHealReceived(),
                collector.getDied(),
                collector.getImposedEffects()
        );
    }
}
