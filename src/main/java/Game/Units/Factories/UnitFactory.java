package Game.Units.Factories;

import Game.Effects.Factories.EffectFactory;
import Game.Event.Aggregates.UnitEventsAggregate;
import Game.Statistics.Session.IUnitStatisticCollector;
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
    private final UnitEventsAggregate unitEvents;
    public final IUnitStatisticCollector statisticCollector;
    private final Team team;
    private final EffectFactory effectFactory;

    public UnitFactory(CompositeAccessor compositeAccessor, Team team, UnitEventsAggregate unitEvents, IUnitStatisticCollector statisticCollector, EffectFactory effectFactory) {
        this.compositeAccessor = compositeAccessor;
        this.team = team;
        this.unitEvents = unitEvents;
        this.statisticCollector = statisticCollector;
        this.effectFactory = effectFactory;
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
        return new UnitSharedData(compositeAccessor, unitEvents, statisticCollector, id, team, data.Name, data.HP, data.MaxHP, effectFactory);
    }
}
