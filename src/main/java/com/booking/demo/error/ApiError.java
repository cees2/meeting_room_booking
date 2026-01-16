package com.booking.demo.error;

import java.util.List;

public record ApiError(String code, String message, List<ValidationError> validationErrors) {
    public ApiError(String code, String message){
        this(code, message, null);
    }
}
