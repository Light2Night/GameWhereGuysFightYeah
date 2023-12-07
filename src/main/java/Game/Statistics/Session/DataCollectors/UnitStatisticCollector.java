package Game.Statistics.Session.DataCollectors;

import Game.Effects.EffectTypes;

import java.util.ArrayList;

public class UnitStatisticCollector {
    private int damage;
    private int heal;
    private int damageReceived;
    private int healReceived;
    private boolean died;
    private ArrayList<EffectTypes> imposedEffects;

    public UnitStatisticCollector() {
        damage = 0;
        heal = 0;
        damageReceived = 0;
        healReceived = 0;
        died = false;
        imposedEffects = new ArrayList<>();
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public void addHeal(int heal) {
        this.heal += heal;
    }

    public void addReceivedDamage(int damageReceived) {
        this.damageReceived += damageReceived;
    }

    public void addReceivedHeal(int healReceived) {
        this.damageReceived += damageReceived;
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

    public int getHeal() {
        return heal;
    }

    public int getDamageReceived() {
        return damageReceived;
    }

    public int getHealReceived() {
        return healReceived;
    }

    public boolean getDied() {
        return died;
    }

    public ArrayList<EffectTypes> getImposedEffects() {
        return (ArrayList<EffectTypes>) imposedEffects.clone();
    }
}
