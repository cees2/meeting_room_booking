package com.booking.demo.mapper;

import com.booking.demo.dto.request.CreateBookingRequest;
import com.booking.demo.entity.Booking;
import com.booking.demo.entity.Room;
import com.booking.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public Booking toEntity(CreateBookingRequest createBookingRequest, User user, Room room){
        Booking booking = new Booking();
        booking.setStartTime(createBookingRequest.startTime());
        booking.setEndTime(createBookingRequest.endTime());
        booking.setStatus(createBookingRequest.purpose());
        booking.setStatus(createBookingRequest.status());
        booking.setUser(user);
        booking.setRoom(room);

        return booking;
    }
}
