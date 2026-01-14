package com.booking.demo.service;

import com.booking.demo.dto.request.CreateUserRequest;
import com.booking.demo.dto.request.UpdateUserRequest;
import com.booking.demo.dto.response.UserResponse;
import com.booking.demo.entity.User;
import com.booking.demo.mapper.UserMapper;
import com.booking.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    public UserResponse getUserById(int userID) {
        User user = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Could not find the user with given ID"));

        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest user) {
        User userToBeSaved = userMapper.createUserFromRequest(user);

        userRepository.save(userToBeSaved);

        return userMapper.toResponse(userToBeSaved);
    }

    @Transactional
    public UserResponse updateUser(int userID, UpdateUserRequest user) {
        User userToBeUpdated = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Could not find the user with given ID"));

        User updatedUser = userMapper.updateUserFromRequest(user, userToBeUpdated);

        return userMapper.toResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(int userID) {
        User userToBeDeleted = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Could not find the user with given ID"));

        userRepository.delete(userToBeDeleted);
    }
}
