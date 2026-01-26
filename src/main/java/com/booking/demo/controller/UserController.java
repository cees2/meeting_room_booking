package com.booking.demo.controller;

import com.booking.demo.dto.request.CreateUserRequest;
import com.booking.demo.dto.request.UpdateUserRequest;
import com.booking.demo.dto.response.BookingResponse;
import com.booking.demo.dto.response.RoomResponse;
import com.booking.demo.dto.response.UserResponse;
import com.booking.demo.service.BookingService;
import com.booking.demo.service.RoomService;
import com.booking.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private BookingService bookingService;

    public UserController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public Page<UserResponse> getAllUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @PageableDefault() Pageable page
    ) {
        return userService.getAllUsers(firstName,lastName,email, page);
    }

    @GetMapping("/{userID}")
    public UserResponse getUserById(@PathVariable int userID) {
        return userService.getUserById(userID);
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest user) {
        return userService.createUser(user);
    }

    @PatchMapping("/{userID}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int userID, @RequestBody UpdateUserRequest user) {
        UserResponse updatedUser = userService.updateUser(userID, user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userID) {
        userService.deleteUser(userID);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userID}/bookings")
    public List<BookingResponse> getUsersBookings(@PathVariable int userID) {
        return bookingService.findBookingsByUser(userID);
    }
}
