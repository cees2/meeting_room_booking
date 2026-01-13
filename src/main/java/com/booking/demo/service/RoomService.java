package com.booking.demo.service;

import com.booking.demo.dto.request.UpdateRoomRequest;
import com.booking.demo.entity.Room;
import com.booking.demo.mapper.RoomMapper;
import com.booking.demo.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(int roomID) {
        return roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find a room with given ID"));
    }

    @Transactional
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Transactional
    public Room updateRoom(int roomID, UpdateRoomRequest room) {
        Room roomToBeUpdated = roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find a room with given ID"));

        return roomMapper.updateRoomFromRequest(roomToBeUpdated, room);
    }

    @Transactional
    public void removeRoom(@PathVariable int roomID) {
        Room roomToBeDeleted = roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find a room with given ID"));

        roomRepository.delete(roomToBeDeleted);
    }
}
