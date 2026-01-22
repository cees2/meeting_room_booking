package com.booking.demo.exceptions;

import java.time.LocalDateTime;

public class InvalidBookingDatesException extends RuntimeException{
    public InvalidBookingDatesException(LocalDateTime startAt, LocalDateTime endAt) {
        super("End date must be after start date. Start: " + startAt + ", end: " + endAt);
    }
}
