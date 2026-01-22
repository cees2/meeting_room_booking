package com.booking.demo.dto.request;

import com.booking.demo.enums.BookingStatus;

import java.time.LocalDateTime;

public record UpdateBookingRequest(LocalDateTime startTime,
                                   LocalDateTime endTime,
                                   String purpose,
                                   BookingStatus status,
                                   Integer user_id,
                                   Integer room_id) {

}
