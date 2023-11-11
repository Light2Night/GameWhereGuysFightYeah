package Game.Event.Arguments.Actions;

import Game.Actions;
import Game.Units.Characters.GameUnit;

public abstract class ActionInfo {
    private GameUnit actor;
    private GameUnit target;
    private Actions action;

    public ActionInfo(GameUnit actor, GameUnit target, Actions action) {
        this.actor = actor;
        this.target = target;
        this.action = action;
    }

    public GameUnit getActor() {
        return actor;
    }

    public GameUnit getTarget() {
        return target;
    }

    public Actions getAction() {
        return action;
    }
}
