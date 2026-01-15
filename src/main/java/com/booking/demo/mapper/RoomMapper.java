package com.booking.demo.mapper;

import com.booking.demo.dto.request.CreateRoomRequest;
import com.booking.demo.dto.request.UpdateRoomRequest;
import com.booking.demo.dto.response.RoomResponse;
import com.booking.demo.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public Room updateRoomFromRequest(Room roomToBeUpdated, UpdateRoomRequest updateRoomRequest){
        if (updateRoomRequest.name() != null) {
            roomToBeUpdated.setName(updateRoomRequest.name());
        }
        if (updateRoomRequest.capacity() != null) {
            roomToBeUpdated.setCapacity(updateRoomRequest.capacity());
        }
        if (updateRoomRequest.location() != null) {
            roomToBeUpdated.setLocation(updateRoomRequest.location());
        }

        return roomToBeUpdated;
    }

    public Room createRoomFromRequest(CreateRoomRequest createRoomRequest){
        Room room = new Room();

        room.setName(createRoomRequest.name());
        room.setLocation(createRoomRequest.location());
        room.setCapacity(createRoomRequest.capacity());

        return room;
    }

    public RoomResponse toResponse (Room room) {
        return new RoomResponse(room.getId(), room.getName(), room.getCapacity(), room.getLocation());
    }
}
