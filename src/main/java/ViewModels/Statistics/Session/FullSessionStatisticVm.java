package ViewModels.Statistics.Session;

import Game.Teams.Team;
import Game.Units.Characters.GameUnit;

import java.util.Map;

public class FullSessionStatisticVm {
    public final Map<GameUnit, UnitStatisticVm> UnitStatistics;
    public final Map<Team, TeamStatisticVm> TeamStatistics;
    public final SessionStatisticVm SessionStatistic;

    public FullSessionStatisticVm(Map<GameUnit, UnitStatisticVm> unitStatistics, Map<Team, TeamStatisticVm> teamStatisticVms, SessionStatisticVm sessionStatistic) {
        UnitStatistics = unitStatistics;
        TeamStatistics = teamStatisticVms;
        SessionStatistic = sessionStatistic;
    }
}
