package com.booking.demo.dto.request;

import com.booking.demo.enums.BookingStatus;
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
        BookingStatus status,
        @NotNull
        Integer user_id,
        @NotNull
        Integer room_id
) {
}
