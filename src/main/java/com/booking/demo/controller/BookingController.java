package com.booking.demo.controller;

import com.booking.demo.dto.request.CreateBookingRequest;
import com.booking.demo.dto.request.UpdateBookingRequest;
import com.booking.demo.dto.response.BookingResponse;
import com.booking.demo.entity.Booking;
import com.booking.demo.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponse createBooking(@Valid @RequestBody CreateBookingRequest createBookingRequest){
        return bookingService.createBooking(createBookingRequest);
    }

    @GetMapping
    public List<BookingResponse> getAllBookings(@RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate){
        return bookingService.getAllBookings(startDate, endDate);
    }

    @GetMapping("/{bookingID}")
    public BookingResponse getBooking(@PathVariable int bookingID){
        return bookingService.getBookingByID(bookingID);
    }

    @PatchMapping("/{bookingID}")
    public BookingResponse updateBooking(@PathVariable int bookingID, @RequestBody UpdateBookingRequest booking){
        return bookingService.updateBooking(bookingID, booking);
    }

    @DeleteMapping("/{bookingID}")
    public ResponseEntity<Void> updateBooking(@PathVariable int bookingID){
        bookingService.deleteBooking(bookingID);

        return ResponseEntity.noContent().build();
    }
}
