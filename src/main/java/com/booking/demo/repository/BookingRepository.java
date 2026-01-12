package com.booking.demo.repository;

import com.booking.demo.entity.Booking;
import com.booking.demo.entity.Room;
import com.booking.demo.entity.User;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BookingRepository {
    private EntityManager entityManager;

    public BookingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Booking createBooking(Booking booking) {
        entityManager.persist(booking);

        return booking;
    }

    public List<Booking> getAllBookings() {
        TypedQuery<Booking> query = entityManager.createQuery("SELECT b FROM Booking b", Booking.class);

        return query.getResultList();
    }

    public Booking getBookingByID(int bookingID) {
        return entityManager.find(Booking.class, bookingID);
    }

    public Booking updateBooking(int bookingID, Booking booking) {
        Booking bookingToBeUpdated = entityManager.find(Booking.class, bookingID);

        if (bookingToBeUpdated == null) {
            throw new EntityNotFoundException("Could not find the booking with ID: " + bookingID);
        }

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = booking.getEndTime();
        String bookingPurpose = booking.getPurpose();
        String bookingStatus = booking.getStatus();
        User bookingUser = booking.getUser();
        Room bookingRoom = booking.getRoom();

        if (bookingStartTime != null) bookingToBeUpdated.setStartTime(bookingStartTime);
        if (bookingEndTime != null) bookingToBeUpdated.setEndTime(bookingEndTime);
        if (bookingPurpose != null) bookingToBeUpdated.setPurpose(bookingPurpose);
        if (bookingStatus != null) bookingToBeUpdated.setStatus(bookingStatus);
        if (bookingUser != null) bookingToBeUpdated.setUser(bookingUser);
        if (bookingRoom != null) bookingToBeUpdated.setRoom(bookingRoom);

        return bookingToBeUpdated;
    }

    public void deleteBooking(int bookingID) {
        Booking bookingToBeUpdated = entityManager.find(Booking.class, bookingID);

        if (bookingToBeUpdated == null) {
            throw new EntityNotFoundException("Could not find the booking with ID: " + bookingID);
        }

        entityManager.remove(bookingToBeUpdated);
    }
}
