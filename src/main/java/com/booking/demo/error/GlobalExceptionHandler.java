package com.booking.demo.error;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
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
            String message = fieldError.getDefaultMessage();

            return new ValidationError(field, message);
        }).toList();

        ApiError apiError = new ApiError("VALIDATION_ERROR", "Incorrect input provided", validationErrors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }
}
