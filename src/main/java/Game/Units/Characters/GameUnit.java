package Game.Units.Characters;

import Game.Actions;
import Game.Effects.Factories.EffectFactory;
import Game.Events.Aggregates.UnitEventsAggregate;
import Game.Events.Arguments.Actions.ActionInfo;
import Game.Events.Arguments.DeadUnitInfo;
import Game.Statistics.Session.IUnitStatisticCollector;
import Game.Units.Getters.CompositeAccessor;
import Game.Effects.Effectable;
import Exceptions.InvalidActionException;
import Game.Move;
import Game.Teams.Team;
import Game.Effects.SharedDatas.UnitSharedData;

import java.util.ArrayList;

public abstract class GameUnit {
    protected final CompositeAccessor accessor;
    private final int id;
    private final String name;
    private final int maxHp;
    private int hp;
    private final ArrayList<Effectable> effects;
    private final Team team;
    protected UnitEventsAggregate events;
    protected IUnitStatisticCollector statisticCollector;
    protected EffectFactory effectFactory;

    public int getId() {
        return id;
    }

    public GameUnit(UnitSharedData sharedData) {
        this.accessor = sharedData.Accessor;
        this.events = sharedData.Events;
        this.statisticCollector = sharedData.StatisticCollector;
        this.effectFactory = sharedData.EffectFactory;
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

    public int takeDamage(int damage) {
        if (damage < 0) throw new RuntimeException("damage");

        int damageReceived = Math.min(hp, damage);
        hp -= damageReceived;
        statisticCollector.addReceivedDamage(this, damageReceived);

        if (!isAlive()) events.UnitDiedEvent.invoke(new DeadUnitInfo(this));

        return damageReceived;
    }

    public int heal(int newHp) {
        if (!isAlive()) return 0;

        int healed = Math.min(maxHp, hp + newHp) - hp;
        hp += healed;
        statisticCollector.addReceivedHeal(this, healed);

        return healed;
    }

    public Boolean isAlive() {
        return hp != 0;
    }

    public void takeEffect(Effectable effect) {
        effects.add(effect);
        statisticCollector.addReceivedEffect(this, effect.getType());
    }

    public void executeEffects() {
        for (Effectable effect : effects) {
            if (!isAlive()) break;

            effect.effect(this);
        }
        effects.removeIf(e -> e.getCyclesLeft() == 0);
    }

    public ActionInfo move(Move move) throws InvalidActionException {
        GameUnit target = accessor.getUnitsAccessor().getUnitById(move.getTargetId());
        return act(target, move.getAction());
    }

    public abstract ActionInfo moveAI() throws InvalidActionException;

    protected abstract ActionInfo act(GameUnit target, Actions action) throws InvalidActionException;
}
