package Game.Exceptions;

public class InvalidActionException extends Exception {
    public InvalidActionException(String message) {
        super(message);
    }

    public InvalidActionException() {
        this("Invalid action");
    }
}
