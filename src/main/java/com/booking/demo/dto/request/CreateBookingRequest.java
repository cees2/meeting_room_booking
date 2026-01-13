package com.booking.demo.dto.request;

import java.time.LocalDateTime;

public record CreateBookingRequest(
        LocalDateTime startTime,
        LocalDateTime endTime,
        String purpose,
        String status,
        Integer user_id,
        Integer room_id
) {
}
