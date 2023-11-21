package Game.Statistics.Session.DataCollectors;

import Game.Effects.EffectTypes;

import java.util.ArrayList;

public class UnitStatisticCollector {
    private int damage;
    private boolean died;
    private ArrayList<EffectTypes> imposedEffects;

    public UnitStatisticCollector() {
        damage = 0;
        died = false;
        imposedEffects = new ArrayList<>();
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public void setDied() {
        this.died = true;
    }

    public void addImposedEffect(EffectTypes effect) {
        imposedEffects.add(effect);
    }

    public int getDamage() {
        return damage;
    }

    public boolean getDied() {
        return died;
    }

    public ArrayList<EffectTypes> getImposedEffects() {
        return (ArrayList<EffectTypes>) imposedEffects.clone();
    }

    public int getImposedEffectsCount() {
        return imposedEffects.size();
    }
}
