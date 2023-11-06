package Game.Event;

import Game.Event.Arguments.Actions.ActionInfo;

public interface UnitActionEventable {
    void onEvent(ActionInfo info);
}
