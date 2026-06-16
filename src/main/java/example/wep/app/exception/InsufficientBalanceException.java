package example.wep.app.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException (String message){
        super(message);
    }
}
