package com.booking.demo.error;

import com.booking.demo.exceptions.BookingOverlappingDatesException;
import com.booking.demo.exceptions.InvalidBookingDatesException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static Map<String, String> constraintToColumn = Map.of(
            "room.unique_name", "name",
            "user.unique_email", "email"
    );

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> globalEntityNotFoundHandler(EntityNotFoundException exception) {
        ApiError apiError = new ApiError("ENTITY_NOT_FOUND", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> globalDuplicateRequestHandler(ConstraintViolationException exception) {
        ApiError apiError = new ApiError("VALIDATION_ERROR", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> globalMethodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            String field = fieldError.getField();
            String value = fieldError.getDefaultMessage();

            return new ValidationError(field, value);
        }).toList();

        ApiError apiError = new ApiError("VALIDATION_ERROR", "Incorrect input provided", validationErrors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> globalDataIntegrityViolationHandler(DataIntegrityViolationException exception) {
        Throwable error = exception.getCause().getCause();
        ApiError apiError = new ApiError("DATA_INTEGRITY_ERROR", "Provided does not satisfy table's requirements");

        if (error instanceof SQLException) {
            int codeError = ((SQLException) error).getErrorCode();
            if (codeError == 1062) {
                String message = error.getMessage();
                String[] messageParts = message.split(" ");
                String field = messageParts[2].replaceAll("'", "");
                String duplicatedValue = messageParts[5].replaceAll("'", "");
                List<ValidationError> validationErrors = new ArrayList<>();
                validationErrors.add(new ValidationError(constraintToColumn.getOrDefault(duplicatedValue, duplicatedValue), field));
                apiError = new ApiError("DATA_UNIQUENESS_ERROR", "Unique field value already exists", validationErrors);
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> globalDataIntegrityViolationHandler(MethodArgumentTypeMismatchException exception) {
        ApiError apiError = new ApiError("TYPE_MISMATCH", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(InvalidBookingDatesException.class)
    public ResponseEntity<ApiError> globalInvalidBookingDatesHandler(InvalidBookingDatesException exception){
        ApiError apiError = new ApiError("INVALID_BOOKING_DATES", exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

    @ExceptionHandler(BookingOverlappingDatesException.class)
    public ResponseEntity<ApiError> globalBookingOverlappingDatesException(BookingOverlappingDatesException exception){
        ApiError apiError = new ApiError("OVERLAPPING_BOOKING_DATES", exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }
}
