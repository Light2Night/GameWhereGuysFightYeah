package Game.Event.Handlers;

import Game.Event.Arguments.Actions.ActionInfo;
import Game.Event.UnitActionEventable;

import java.util.ArrayList;

public class OnAction implements UnitActionEventable {
    private ArrayList<ActionInfo> cycleActions;

    public OnAction(ArrayList<ActionInfo> cycleActions) {
        this.cycleActions = cycleActions;
    }

    @Override
    public void onEvent(ActionInfo info) {
        cycleActions.add(info);
    }
}