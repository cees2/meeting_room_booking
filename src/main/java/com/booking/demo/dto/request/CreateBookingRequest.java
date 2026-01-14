package com.booking.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateBookingRequest(
        @NotNull
        LocalDateTime startTime,
        @NotNull
        LocalDateTime endTime,
        @NotEmpty
        String purpose,
        @NotEmpty
        String status,
        @NotEmpty
        Integer user_id,
        @NotEmpty
        Integer room_id
) {
}
