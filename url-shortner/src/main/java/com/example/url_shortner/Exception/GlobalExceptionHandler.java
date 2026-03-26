package com.example.url_shortner.Exception;
import com.example.url_shortner.DTO.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Collect all error messages into one string
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(400)
                .body(new ErrorResponse("VALIDATION_FAILED", errorMessage));
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(UrlNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(new ErrorResponse("URL_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ErrorResponse> handleInvalid(InvalidUrlException ex) {
        return ResponseEntity.status(400)
                .body(new ErrorResponse("INVALID_URL", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred"));
    }
}
