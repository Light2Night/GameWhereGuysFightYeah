package Game.Event.Arguments;

import Game.Teams.Team;
import ViewModels.SessionStatisticVm;

public class GameEndInfo {
    private final Team teamWinner;
    private final SessionStatisticVm sessionStatistic;

    public GameEndInfo(Team teamWinner, SessionStatisticVm sessionStatistic) {
        this.teamWinner = teamWinner;
        this.sessionStatistic = sessionStatistic;
    }

    public Team getTeamWinner() {
        return teamWinner;
    }

    public SessionStatisticVm getSessionStatistic() {
        return sessionStatistic;
    }
}
