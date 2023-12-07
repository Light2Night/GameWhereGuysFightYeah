package Game.Effects.SharedDatas;

import Game.Events.Aggregates.EffectEventsAggregate;
import Game.Statistics.Session.IUnitStatisticCollector;

public class EffectSharedData {
    public final IUnitStatisticCollector StatisticCollector;
    public final EffectEventsAggregate EventsAggregate;

    public EffectSharedData(IUnitStatisticCollector statisticCollector, EffectEventsAggregate eventsAggregate) {
        this.StatisticCollector = statisticCollector;
        this.EventsAggregate = eventsAggregate;
    }
}
