package Game.Teams;

import Game.PlayerTypes;

public class Team {
    private int id;
    private PlayerTypes playerType;
    private String name;

    public Team(int id, PlayerTypes playerType, String name) {
        this.id = id;
        this.playerType = playerType;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public PlayerTypes getPlayerType() {
        return playerType;
    }

    public String getName() {
        return name;
    }
}
