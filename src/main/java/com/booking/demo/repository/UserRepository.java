package com.booking.demo.repository;

import com.booking.demo.entity.User;
import jakarta.persistence.EntityManager;
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
}
