package ViewModels.Statistics.Session;

import Game.Effects.EffectTypes;
import Game.Units.Characters.GameUnit;

import java.util.ArrayList;

public class TeamStatisticVm {
    public int Damage;
    public int Heal;
    public int ReceivedDamage;
    public int ReceivedHeal;
    public ArrayList<GameUnit> DeadUnits;

    public int DeadUnitsCount() {
        return DeadUnits.size();
    }

    public ArrayList<EffectTypes> ImposedEffects;

    public int getImposedEffectsCount() {
        return ImposedEffects.size();
    }

    public ArrayList<EffectTypes> ReceivedEffects;

    public int getReceivedEffectsCount() {
        return ReceivedEffects.size();
    }

    public TeamStatisticVm() {
        this(0, 0, 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public TeamStatisticVm(int damage, int heal, int receivedDamage, int receivedHeal, ArrayList<GameUnit> deadUnits, ArrayList<EffectTypes> imposedEffects, ArrayList<EffectTypes> receivedEffects) {
        Damage = damage;
        Heal = heal;
        ReceivedDamage = receivedDamage;
        ReceivedHeal = receivedHeal;
        DeadUnits = deadUnits;
        ImposedEffects = imposedEffects;
        ReceivedEffects = receivedEffects;
    }
}
