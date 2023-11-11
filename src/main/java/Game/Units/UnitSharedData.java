package Game.Units;

import Game.Event.Aggregates.UnitEventsAggregate;
import Game.Teams.Team;
import Game.Units.Getters.CompositeAccessor;

public class UnitSharedData {
    public final CompositeAccessor Accessor;
    public final UnitEventsAggregate Events;
    public final int Id;
    public final Team Team;
    public final String Name;
    public final int Hp;
    public final int MaxHp;

    public UnitSharedData(CompositeAccessor accessor, UnitEventsAggregate events, int id, Team team, String name, int hp, int maxHp) {
        Accessor = accessor;
        Events = events;
        Id = id;
        Team = team;
        Name = name;
        Hp = hp;
        MaxHp = maxHp;
    }
}
