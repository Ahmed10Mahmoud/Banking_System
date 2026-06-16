package example.wep.app.exception;

public class UnauthorizedAccountAccessException extends RuntimeException{
    public UnauthorizedAccountAccessException(String message) {
        super(message);
    }
}
