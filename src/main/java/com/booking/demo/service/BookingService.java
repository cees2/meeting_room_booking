package com.booking.demo.service;

import com.booking.demo.dto.request.CreateBookingRequest;
import com.booking.demo.dto.request.UpdateBookingRequest;
import com.booking.demo.dto.response.BookingResponse;
import com.booking.demo.entity.Booking;
import com.booking.demo.entity.Room;
import com.booking.demo.entity.User;
import com.booking.demo.enums.BookingStatus;
import com.booking.demo.exceptions.BookingOverlappingDatesException;
import com.booking.demo.exceptions.InvalidBookingDatesException;
import com.booking.demo.mapper.BookingMapper;
import com.booking.demo.repository.BookingRepository;
import com.booking.demo.repository.RoomRepository;
import com.booking.demo.repository.UserRepository;
import com.booking.demo.specification.BookingSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public Page<BookingResponse> getAllBookings(
            LocalDateTime startTime,
            LocalDateTime endTime,
            String purpose,
            BookingStatus status,
            Integer userId,
            Integer roomId,
            Pageable page) {
        Specification<Booking> spec = (root, query, cb) -> cb.conjunction();

        if (startTime != null) {
            spec = spec.and(BookingSpecifications.startTimeAt(startTime));
        }

        if (endTime != null) {
            spec = spec.and(BookingSpecifications.endTimeAt(endTime));
        }

        if (purpose != null) {
            spec = spec.and(BookingSpecifications.purpose(purpose));
        }

        if (status != null) {
            spec = spec.and(BookingSpecifications.status(status));
        }

        if (userId != null) {
            spec = spec.and(BookingSpecifications.user(userId));
        }

        if (roomId != null) {
            spec = spec.and(BookingSpecifications.room(roomId));
        }

        return bookingRepository.findAll(spec,page).map(bookingMapper::toResponse);
    }

    public BookingResponse getBookingByID(int bookingID) {
        Booking booking = bookingRepository.findById(bookingID).orElseThrow(() -> new EntityNotFoundException("Could not find the booking with given ID"));

        return bookingMapper.toResponse(booking);
    }


    @Transactional
    public BookingResponse createBooking(CreateBookingRequest createBookingRequest) {
        Integer roomID = createBookingRequest.room_id();
        Integer userID = createBookingRequest.user_id();
        LocalDateTime startTime = createBookingRequest.startTime();
        LocalDateTime endTime = createBookingRequest.endTime();

        if (startTime.isAfter(endTime)) {
            throw new InvalidBookingDatesException(startTime, endTime);
        }

        List<Booking> overlappingBookings = bookingRepository.findAll(BookingSpecifications.roomAndBetweenTimeAt(startTime, endTime, roomID));

        if (!overlappingBookings.isEmpty()) {
            LocalDateTime overlappedStartTime = overlappingBookings.getFirst().getStartTime();
            LocalDateTime overlappedEndTime = overlappingBookings.getFirst().getEndTime();
            Room overlappedRoom = roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find room user with ID: " + roomID));

            throw new BookingOverlappingDatesException(createBookingRequest.startTime(), createBookingRequest.endTime(), overlappedStartTime, overlappedEndTime, overlappedRoom.getName());
        }

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
        LocalDateTime startTime = booking.startTime();
        LocalDateTime endTime = booking.endTime();
        Room room = null;
        User user = null;

        List<Booking> overlappingBookings = bookingRepository.findAll(BookingSpecifications.roomAndBetweenTimeAt(startTime, endTime, roomID));

        if (!overlappingBookings.isEmpty()) {
            LocalDateTime overlappedStartTime = overlappingBookings.getFirst().getStartTime();
            LocalDateTime overlappedEndTime = overlappingBookings.getFirst().getEndTime();
            Room overlappedRoom = roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find room user with ID: " + roomID));

            throw new BookingOverlappingDatesException(booking.startTime(), booking.endTime(), overlappedStartTime, overlappedEndTime, overlappedRoom.getName());
        }

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

    public List<BookingResponse> findBookingsByUser(int userID) {
        List<Booking> bookings = bookingRepository.findByUserId(userID);

        return bookings.stream().map(booking -> bookingMapper.toResponse(booking)).toList();
    }

    public List<BookingResponse> findBookingsByRoom(int userID) {
        List<Booking> bookings = bookingRepository.findByRoomId(userID);

        return bookings.stream().map(booking -> bookingMapper.toResponse(booking)).toList();
    }
}
