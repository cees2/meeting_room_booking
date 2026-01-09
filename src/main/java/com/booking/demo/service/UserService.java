package com.booking.demo.service;

import com.booking.demo.entity.User;
import com.booking.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public User getUserById(int userID){
        return userRepository.getUserById(userID);
    }

    @Transactional
    public User createUser(User user){
        return userRepository.createUser(user);
    }
}
