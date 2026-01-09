package com.booking.demo.service;

import com.booking.demo.dto.request.UpdateRoomRequest;
import com.booking.demo.entity.Room;
import com.booking.demo.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRooms(){
        return roomRepository.getRooms();
    }

    public Room getRoomById(int roomID){
        return roomRepository.getRoomById(roomID);
    }

    @Transactional
    public Room createRoom(Room room) {return roomRepository.createRoom(room);}

    @Transactional
    public Room updateRoom(int roomID, UpdateRoomRequest room) {
        return roomRepository.updateRoom(roomID, room);
    }
}
