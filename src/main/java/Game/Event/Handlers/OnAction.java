package Game.Event.Handlers;

import Game.Event.Arguments.Actions.ActionInfo;
import Game.Event.HandlerGeneric;

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