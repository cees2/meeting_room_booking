package com.booking.demo.mapper;

import com.booking.demo.dto.request.UpdateRoomRequest;
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
}
