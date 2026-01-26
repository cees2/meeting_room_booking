package com.booking.demo.service;

import com.booking.demo.dto.request.CreateUserRequest;
import com.booking.demo.dto.request.UpdateUserRequest;
import com.booking.demo.dto.response.UserResponse;
import com.booking.demo.entity.User;
import com.booking.demo.mapper.UserMapper;
import com.booking.demo.repository.UserRepository;
import com.booking.demo.specification.UserSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Page<UserResponse> getAllUsers(String firstName, String lastName, String email, Pageable page) {
        Specification<User> spec = (root, query, builder) -> builder.conjunction();

        if(firstName != null){
            spec = spec.and(UserSpecifications.firstName(firstName));
        }

        if(lastName != null){
            spec = spec.and(UserSpecifications.lastName(lastName));
        }

        if(email != null){
            spec = spec.and(UserSpecifications.email(email));
        }

        return userRepository.findAll(spec, page).map(userMapper::toResponse);
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
