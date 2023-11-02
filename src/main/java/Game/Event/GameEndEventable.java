package Game.Event;

import Game.GameEndInfo;

public interface GameEndEventable {
    void onEvent(GameEndInfo gameEndInfo);
}
