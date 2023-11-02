package Game.Event;

import Game.GameEndInfo;
import org.jetbrains.annotations.Nullable;

public class GameEventsAggregate {
    @Nullable
    private Eventable selectedIndexChangedEvent;
    @Nullable
    private Eventable currentIndexChangedEvent;
    @Nullable
    private Eventable moveCompletedEvent;
    @Nullable
    private GameEndEventable gameEndEvent;

    public void setSelectedIndexChangedEvent(@Nullable Eventable selectedIndexChangedEvent) {
        this.selectedIndexChangedEvent = selectedIndexChangedEvent;
    }

    public void setCurrentIndexChangedEvent(@Nullable Eventable currentIndexChangedEvent) {
        this.currentIndexChangedEvent = currentIndexChangedEvent;
    }

    public void setMoveCompletedEvent(@Nullable Eventable moveCompletedEvent) {
        this.moveCompletedEvent = moveCompletedEvent;
    }

    public void setGameEndEvent(@Nullable GameEndEventable gameEndEvent) {
        this.gameEndEvent = gameEndEvent;
    }

    public void selectedIndexChanged() {
        if (selectedIndexChangedEvent != null) selectedIndexChangedEvent.onEvent();
    }

    public void currentIndexChanged() {
        if (currentIndexChangedEvent != null) currentIndexChangedEvent.onEvent();
    }

    public void moveCompleted() {
        if (moveCompletedEvent != null) moveCompletedEvent.onEvent();
    }

    public void gameEnd(GameEndInfo info) {
        if (gameEndEvent != null) gameEndEvent.onEvent(info);
    }
}
