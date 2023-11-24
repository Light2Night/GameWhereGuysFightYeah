package Game.Events.Arguments;

import Game.Teams.Team;
import ViewModels.Statistics.Session.FullSessionStatisticVm;

public class GameEndInfo {
    private final Team teamWinner;
    private final FullSessionStatisticVm sessionStatistic;

    public GameEndInfo(Team teamWinner, FullSessionStatisticVm sessionStatistic) {
        this.teamWinner = teamWinner;
        this.sessionStatistic = sessionStatistic;
    }

    public Team getTeamWinner() {
        return teamWinner;
    }

    public FullSessionStatisticVm getSessionStatistic() {
        return sessionStatistic;
    }
}
