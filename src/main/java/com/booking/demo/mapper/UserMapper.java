package com.booking.demo.mapper;


import com.booking.demo.dto.request.UpdateUserRequest;
import com.booking.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User updateUserFromRequest(UpdateUserRequest updateUserRequest, User userToBeUpdated){
        String firstName = updateUserRequest.firstName();
        String lastName = updateUserRequest.lastName();
        String email = updateUserRequest.email();

        if(firstName != null) userToBeUpdated.setFirstName(firstName);
        if(lastName != null) userToBeUpdated.setLastName(lastName);
        if(email != null) userToBeUpdated.setEmail(email);

        return userToBeUpdated;

    }
}
