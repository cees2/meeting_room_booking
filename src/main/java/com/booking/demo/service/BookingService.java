package com.booking.demo.service;

import com.booking.demo.dto.request.CreateBookingRequest;
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

    @Transactional
    public Booking createBooking(CreateBookingRequest createBookingRequest) {
        int roomID = createBookingRequest.room_id();
        int userID = createBookingRequest.user_id();

        Room room = roomRepository.getRoomById(roomID);
        User user = userRepository.getUserById(userID);

        if (user == null) {
            throw new EntityNotFoundException("Could not find the user with ID: " + userID);
        }

        if (room == null) {
            throw new EntityNotFoundException("Could not find the room with ID: " + roomID);
        }

        Booking booking = bookingMapper.toEntity(createBookingRequest, user, room);

        return bookingRepository.createBooking(booking);
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.getAllBookings();
    }

    public Booking getBookingByID(int bookingID){
        return bookingRepository.getBookingByID(bookingID);
    }

    @Transactional
    public Booking updateBooking(int bookingID, Booking booking){
        return bookingRepository.updateBooking(bookingID, booking);
    }

    @Transactional
    public void deleteBooking(int bookingID){
        bookingRepository.deleteBooking(bookingID);
    }
}
