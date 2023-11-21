package Game.Statistics.Session;

import ViewModels.Statistics.Session.FullSessionStatisticVm;

public interface ISessionStatisticBuilder extends IUnitStatisticCollector, ICycleStatisticCollector {
    void reset();

    FullSessionStatisticVm build();
}
