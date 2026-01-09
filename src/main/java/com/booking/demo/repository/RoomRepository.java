package com.booking.demo.repository;

import com.booking.demo.dto.request.UpdateRoomRequest;
import com.booking.demo.entity.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public class RoomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Room> getRooms(){
        TypedQuery<Room> query = entityManager.createQuery("SELECT r FROM Room r", Room.class);

        return query.getResultList();
    }

    public Room getRoomById(int roomID){
        return entityManager.find(Room.class, roomID);
    }

    public Room createRoom(Room room){
        entityManager.persist(room);

        return room;
    }

    public Room updateRoom(int roomID, UpdateRoomRequest room) {
        Room roomToBeUpdated = entityManager.find(Room.class, roomID);

        if(roomToBeUpdated == null){
            throw new EntityNotFoundException("Could not find a room with given ID");
        }

        if(room.name() != null){
            roomToBeUpdated.setName(room.name());
        }
        if(room.capacity() != null){
            roomToBeUpdated.setCapacity(room.capacity());
        }
        if(room.location() != null){
            roomToBeUpdated.setLocation(room.location());
        }

        return roomToBeUpdated;
    }

    public void removeRoom(@PathVariable int roomID){
        Room roomToBeDeleted = entityManager.find(Room.class, roomID);

        if(roomToBeDeleted == null){
            throw new EntityNotFoundException("Room with ID: " + roomID + " could not be found");
        }

        entityManager.remove(roomToBeDeleted);
    }
}
