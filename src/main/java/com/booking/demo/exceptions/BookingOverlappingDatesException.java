package com.booking.demo.exceptions;

import java.time.LocalDateTime;


public class BookingOverlappingDatesException extends RuntimeException {
    public BookingOverlappingDatesException(LocalDateTime requestStartTime, LocalDateTime requestEndTime, LocalDateTime dbStartTime, LocalDateTime dbEndTime) {
        super("Provided dates (" + requestStartTime + ", " + requestEndTime + ") overlap existing bookings(" + dbStartTime + ", " + dbEndTime + ")");
    }
}
