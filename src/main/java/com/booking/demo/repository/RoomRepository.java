package com.booking.demo.repository;

import com.booking.demo.entity.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Room> getRooms(){
        TypedQuery<Room> query = entityManager.createQuery("SELECT r FROM Room r", Room.class);

        return query.getResultList();
    }
}
