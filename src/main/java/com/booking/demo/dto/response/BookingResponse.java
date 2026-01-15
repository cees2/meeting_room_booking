package com.booking.demo.dto.response;

import com.booking.demo.entity.Room;
import com.booking.demo.entity.User;

import java.time.LocalDateTime;

public record BookingResponse(
        Integer id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String purpose,
        String status,
        User user,
        Room room
) {
}
