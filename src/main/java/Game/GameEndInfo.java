package Game;

import Game.Teams.Team;

public class GameEndInfo {
    private Team teamWinner;

    public GameEndInfo(Team teamWinner) {
        this.teamWinner = teamWinner;
    }

    public Team getTeamWinner() {
        return teamWinner;
    }
}
