package com.booking.demo.service;

import com.booking.demo.dto.request.CreateBookingRequest;
import com.booking.demo.dto.request.UpdateBookingRequest;
import com.booking.demo.dto.response.BookingResponse;
import com.booking.demo.entity.Booking;
import com.booking.demo.entity.Room;
import com.booking.demo.entity.User;
import com.booking.demo.mapper.BookingMapper;
import com.booking.demo.repository.BookingRepository;
import com.booking.demo.repository.RoomRepository;
import com.booking.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private BookingMapper bookingMapper;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, UserRepository userRepository, BookingMapper bookingMapper
    ) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.bookingMapper = bookingMapper;
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream().map(bookingMapper::toResponse).toList();
    }

    public BookingResponse getBookingByID(int bookingID) {
        Booking booking = bookingRepository.findById(bookingID).orElseThrow(() -> new EntityNotFoundException("Could not find the booking with given ID"));

        return bookingMapper.toResponse(booking);
    }


    @Transactional
    public BookingResponse createBooking(CreateBookingRequest createBookingRequest) {
        Integer roomID = createBookingRequest.room_id();
        Integer userID = createBookingRequest.user_id();

        Room room = roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find the user with ID: " + userID));
        User user = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Could not find the room with ID: " + roomID));

        Booking bookingToBeCreated = bookingMapper.createBookingFromRequest(createBookingRequest, user, room);

        bookingRepository.save(bookingToBeCreated);

        return bookingMapper.toResponse(bookingToBeCreated);
    }

    @Transactional
    public BookingResponse updateBooking(int bookingID, UpdateBookingRequest booking) {
        Booking bookingToBeUpdated = bookingRepository.findById(bookingID).orElseThrow(() -> new EntityNotFoundException("Could not find the booking with given ID"));
        Integer roomID = booking.room_id();
        Integer userID = booking.user_id();
        Room room = null;
        User user = null;

        if (roomID != null) {
            room = roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find the user with ID: " + userID));
        }

        if (userID != null) {
            user = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Could not find the room with ID: " + roomID));
        }

        bookingMapper.updateBookingFromRequest(booking, bookingToBeUpdated, user, room);

        return bookingMapper.toResponse(bookingToBeUpdated);
    }

    @Transactional
    public void deleteBooking(int bookingID) {
        Booking bookingToBeRemoved = bookingRepository.findById(bookingID).orElseThrow(() -> new EntityNotFoundException("Could not find the booking with given ID"));

        bookingRepository.delete(bookingToBeRemoved);
    }
}
