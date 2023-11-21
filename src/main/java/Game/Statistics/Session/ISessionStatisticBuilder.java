package Game.Statistics.Session;

import ViewModels.SessionStatisticVm;

public interface ISessionStatisticBuilder extends IStatisticCollector {
    void reset();

    SessionStatisticVm build();
}
