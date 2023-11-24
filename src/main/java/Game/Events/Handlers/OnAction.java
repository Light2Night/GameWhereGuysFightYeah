package Game.Events.Handlers;

import Game.Events.Arguments.Actions.ActionInfo;
import Game.Events.HandlerGeneric;

import java.util.ArrayList;

public class OnAction extends HandlerGeneric<ActionInfo> {
    private final ArrayList<ActionInfo> cycleActions;

    public OnAction(ArrayList<ActionInfo> cycleActions) {
        this.cycleActions = cycleActions;
    }

    @Override
    public void handle(ActionInfo info) {
        cycleActions.add(info);
    }
}