package com.booking.demo.service;

import com.booking.demo.dto.request.UpdateUserRequest;
import com.booking.demo.entity.User;
import com.booking.demo.mapper.UserMapper;
import com.booking.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(int userID){
        return userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Could not find the user with given ID"));
    }

    @Transactional
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(int userID, UpdateUserRequest user){
        User userToBeUpdated = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Could not find the user with given ID"));

        return userMapper.updateUserFromRequest(user,userToBeUpdated);
    }

    @Transactional
    public void deleteUser(int userID){
        User userToBeDeleted = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Could not find the user with given ID"));

        userRepository.delete(userToBeDeleted);
    }
}
