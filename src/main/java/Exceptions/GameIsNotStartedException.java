package Exceptions;

public class GameIsNotStartedException extends Exception {
    public GameIsNotStartedException(String message) {
        super(message);
    }

    public GameIsNotStartedException() {
        this("Game is not started now");
    }
}
