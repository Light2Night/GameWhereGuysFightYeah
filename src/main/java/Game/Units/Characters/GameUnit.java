package Game.Units.Characters;

import Game.Event.Aggregates.UnitEventsAggregate;
import Game.Units.Getters.CompositeAccessor;
import Game.Effects.Effectable;
import Exceptions.InvalidActionException;
import Game.Move;
import Game.Teams.Team;
import Game.Units.UnitSharedData;

import java.util.ArrayList;

public abstract class GameUnit {
    protected final CompositeAccessor accessor;
    private final int id;
    private String name;
    private final int maxHp;
    private int hp;
    private ArrayList<Effectable> effects;
    private Team team;
    protected UnitEventsAggregate events;

    public int getId() {
        return id;
    }

    public GameUnit(UnitSharedData sharedData) {
        this.accessor = sharedData.Accessor;
        this.events = sharedData.Events;
        this.team = sharedData.Team;
        this.id = sharedData.Id;
        this.name = sharedData.Name;
        this.maxHp = sharedData.MaxHp;
        this.hp = sharedData.Hp;
        this.effects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public Team getTeam() {
        return team;
    }

    public ArrayList<Effectable> getEffects() {
        return new ArrayList<>(effects);
    }

    public void takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("damage");

        hp -= Math.min(hp, damage);
    }

    public void heal(int newHp) {
        if (!isAlive()) return;

        hp = Math.min(maxHp, hp + newHp);
    }

    public Boolean isAlive() {
        return hp != 0;
    }

    public void takeEffect(Effectable effect) {
        effects.add(effect);
    }

    public void executeEffects() {
        for (Effectable effect : effects) {
            effect.effect(this);
        }
        effects.removeIf(e -> e.getCyclesLeft() == 0);
    }

    public abstract void move(Move move) throws InvalidActionException;

    public abstract void moveAI();

    @Override
    public String toString() {
        return String.format("%d | %-8s | %3s♥ | Ефектів: %1d", id, name, hp, effects.size());
    }
}
