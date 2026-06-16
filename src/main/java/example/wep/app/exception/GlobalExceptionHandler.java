package example.wep.app.exception;

import example.wep.app.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleAccountNotFound(
            AccountNotFoundException ex
    ){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(404)
                                .error("Account Not Found")
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse>
            handleInsufficientBalance(
            InsufficientBalanceException ex
    ){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(400)
                                .error("Insufficient Balance")
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(
            UnauthorizedAccountAccessException.class
    )
    public ResponseEntity<ErrorResponse>
    handleUnauthorized(
            UnauthorizedAccountAccessException ex
    ){

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(403)
                                .error("Access Denied")
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneric(
            Exception ex
    ){

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(500)
                                .error("Internal Server Error")
                                .message(ex.getMessage())
                                .build()
                );
    }
}
