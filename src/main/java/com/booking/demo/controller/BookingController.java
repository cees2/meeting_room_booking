package com.booking.demo.controller;

import com.booking.demo.dto.request.CreateBookingRequest;
import com.booking.demo.entity.Booking;
import com.booking.demo.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@RequestBody CreateBookingRequest createBookingRequest){
        return bookingService.createBooking(createBookingRequest);
    }

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @GetMapping("/{bookingID}")
    public Booking getBooking(@PathVariable int bookingID){
        return bookingService.getBookingByID(bookingID);
    }

    @PatchMapping("/{bookingID}")
    public Booking updateBooking(@PathVariable int bookingID, @RequestBody Booking booking){
        return bookingService.updateBooking(bookingID, booking);
    }

    @DeleteMapping("/{bookingID}")
    public void updateBooking(@PathVariable int bookingID){
        bookingService.deleteBooking(bookingID);
    }
}
