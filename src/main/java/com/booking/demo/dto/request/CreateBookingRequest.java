package com.booking.demo.dto.request;

import java.time.LocalDateTime;

public record CreateBookingRequest(
        LocalDateTime startTime,
        LocalDateTime endTime,
        String purpose,
        String status,
        int user_id,
        int room_id
) {
}
