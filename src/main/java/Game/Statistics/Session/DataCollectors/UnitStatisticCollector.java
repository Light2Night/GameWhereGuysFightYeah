package Game.Statistics.Session.DataCollectors;

import Game.Effects.EffectTypes;

import java.util.ArrayList;

public class UnitStatisticCollector {
    private int damage;
    private int heal;
    private int receivedDamage;
    private int receivedHeal;
    private boolean died;
    private ArrayList<EffectTypes> imposedEffects;
    private ArrayList<EffectTypes> receivedEffects;

    public UnitStatisticCollector() {
        damage = 0;
        heal = 0;
        receivedDamage = 0;
        receivedHeal = 0;
        died = false;
        imposedEffects = new ArrayList<>();
        receivedEffects = new ArrayList<>();
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public void addHeal(int heal) {
        this.heal += heal;
    }

    public void addReceivedDamage(int receivedDamage) {
        this.receivedDamage += receivedDamage;
    }

    public void addReceivedHeal(int receivedHeal) {
        this.receivedHeal += receivedHeal;
    }

    public void setDied() {
        this.died = true;
    }

    public void addImposedEffect(EffectTypes effect) {
        imposedEffects.add(effect);
    }

    public void addReceivedEffect(EffectTypes effect) {
        receivedEffects.add(effect);
    }

    public int getDamage() {
        return damage;
    }

    public int getHeal() {
        return heal;
    }

    public int getReceivedDamage() {
        return receivedDamage;
    }

    public int getReceivedHeal() {
        return receivedHeal;
    }

    public boolean getDied() {
        return died;
    }

    public ArrayList<EffectTypes> getImposedEffects() {
        return (ArrayList<EffectTypes>) imposedEffects.clone();
    }

    public ArrayList<EffectTypes> getReceivedEffects() {
        return (ArrayList<EffectTypes>) receivedEffects.clone();
    }
}
