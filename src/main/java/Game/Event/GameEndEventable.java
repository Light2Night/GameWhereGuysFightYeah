package Game.Event;

import Game.Event.Arguments.GameEndInfo;

public interface GameEndEventable {
    void onEvent(GameEndInfo gameEndInfo);
}
