package com.booking.demo.controller;

import com.booking.demo.dto.request.CreateUserRequest;
import com.booking.demo.dto.request.UpdateUserRequest;
import com.booking.demo.dto.response.UserResponse;
import com.booking.demo.entity.User;
import com.booking.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userID}")
    public UserResponse getUserById(@PathVariable int userID){
        return userService.getUserById(userID);
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest user){
        return userService.createUser(user);
    }

    @PatchMapping("/{userID}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int userID, @RequestBody UpdateUserRequest user){
        UserResponse updatedUser = userService.updateUser(userID,user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userID){
        userService.deleteUser(userID);

        return ResponseEntity.noContent().build();
    }
}
