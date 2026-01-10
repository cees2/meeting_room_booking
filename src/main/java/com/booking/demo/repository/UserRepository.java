package com.booking.demo.repository;

import com.booking.demo.dto.request.UpdateUserRequest;
import com.booking.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> getAllUsers(){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM user u", User.class);

        return query.getResultList();
    }

    public User getUserById(int userID){
        return entityManager.find(User.class, userID);
    }

    public User createUser(User user){
        entityManager.persist(user);

        return user;
    }

    public User updateUser(int userID, UpdateUserRequest user){
        User userToBeUpdated = entityManager.find(User.class, userID);

        if(userToBeUpdated == null){
            throw new EntityNotFoundException("Could not find the user with ID: " + userID);
        }

        if(user.firstName() != null){
            userToBeUpdated.setFirstName(user.firstName());
        }
        if(user.lastName() != null){
            userToBeUpdated.setLastName(user.lastName());
        }
        if(user.email() != null){
            userToBeUpdated.setEmail(user.email());
        }

        return userToBeUpdated;
    }

    public void deleteUser(int userID){
        User userToBeDeleted = entityManager.find(User.class, userID);

        if(userToBeDeleted == null){
            throw new EntityNotFoundException("Could not find the user with ID: " + userID);
        }

        entityManager.remove(userToBeDeleted);
    }
}
