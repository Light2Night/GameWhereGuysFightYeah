package Game.Units.Factories;

import Game.Event.Aggregates.UnitEventsAggregate;
import Game.Teams.Team;
import Game.Units.Characters.Barbarian;
import Game.Units.Characters.GameUnit;
import Game.Units.Characters.Healer;
import Game.Units.Characters.Magician;
import Game.Units.Factories.ViewModels.BarbarianViewModel;
import Game.Units.Factories.ViewModels.BaseUnitViewModel;
import Game.Units.Factories.ViewModels.HealerViewModel;
import Game.Units.Factories.ViewModels.MageViewModel;
import Game.Units.Getters.CompositeAccessor;
import Game.Units.UnitSharedData;
import Helpers.IdGenerator;

public class UnitFactory {
    private final CompositeAccessor compositeAccessor;
    private final Team team;
    private final UnitEventsAggregate unitEvents;

    public UnitFactory(CompositeAccessor compositeAccessor, Team team, UnitEventsAggregate unitEvents) {
        this.compositeAccessor = compositeAccessor;
        this.team = team;
        this.unitEvents = unitEvents;
    }

    public GameUnit createBarbarian(BarbarianViewModel data) {
        return new Barbarian(getSharedData(data), data.Damage, data.DamageDelta);
    }

    public GameUnit createMagician(MageViewModel data) {
        return new Magician(getSharedData(data), data.Damage, data.DamageDelta);
    }

    public GameUnit createHealer(HealerViewModel data) {
        return new Healer(getSharedData(data), data.Heal);
    }

    private UnitSharedData getSharedData(BaseUnitViewModel data) {
        int id = IdGenerator.getId();
        return new UnitSharedData(compositeAccessor, unitEvents, id, team, data.Name, data.HP, data.MaxHP);
    }
}
