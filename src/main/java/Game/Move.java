package Game;

public class Move {
    private Actions action;
    private int targetId;

    public Move(Actions action, int targetId) {
        this.action = action;
        this.targetId = targetId;
    }

    public Actions getAction() {
        return action;
    }

    public int getTargetId() {
        return targetId;
    }
}
