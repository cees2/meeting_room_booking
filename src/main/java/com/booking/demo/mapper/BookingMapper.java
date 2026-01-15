package com.booking.demo.mapper;

import com.booking.demo.dto.request.CreateBookingRequest;
import com.booking.demo.dto.request.UpdateBookingRequest;
import com.booking.demo.dto.response.BookingResponse;
import com.booking.demo.entity.Booking;
import com.booking.demo.entity.Room;
import com.booking.demo.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingMapper {
    public Booking createBookingFromRequest(CreateBookingRequest createBookingRequest, User user, Room room) {
        Booking booking = new Booking();
        booking.setStartTime(createBookingRequest.startTime());
        booking.setEndTime(createBookingRequest.endTime());
        booking.setStatus(createBookingRequest.purpose());
        booking.setStatus(createBookingRequest.status());
        booking.setUser(user);
        booking.setRoom(room);

        return booking;
    }

    public Booking updateBookingFromRequest(UpdateBookingRequest createBookingRequest, Booking bookingToBeUpdated, User user, Room room) {
        LocalDateTime startTime = createBookingRequest.startTime();
        LocalDateTime endTime = createBookingRequest.endTime();
        String purpose = createBookingRequest.purpose();
        String status = createBookingRequest.status();

        if (startTime != null) bookingToBeUpdated.setStartTime(startTime);
        if (endTime != null) bookingToBeUpdated.setEndTime(endTime);
        if (purpose != null) bookingToBeUpdated.setPurpose(purpose);
        if (status != null) bookingToBeUpdated.setStatus(status);
        if (user != null) bookingToBeUpdated.setUser(user);
        if (room != null) bookingToBeUpdated.setRoom(room);

        return bookingToBeUpdated;
    }

    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(booking.getId(), booking.getStartTime(), booking.getEndTime(), booking.getPurpose(), booking.getStatus(), booking.getUser(), booking.getRoom());
    }
}
