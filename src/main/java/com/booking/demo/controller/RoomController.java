package com.booking.demo.controller;

import com.booking.demo.dto.request.UpdateRoomRequest;
import com.booking.demo.entity.Room;
import com.booking.demo.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/{roomID}")
    public Room getRoomById(@PathVariable int roomID) {
        return roomService.getRoomById(roomID);
    }

    @PostMapping
    public Room createRoom(@Valid @RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @PatchMapping("/{roomID}")
    public ResponseEntity<Room> updateRoom(@PathVariable int roomID, @RequestBody UpdateRoomRequest room) {
        Room updatedRoom = roomService.updateRoom(roomID, room);

        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{roomID}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int roomID){
        roomService.removeRoom(roomID);

        return ResponseEntity.noContent().build();
    }
}
