package com.booking.demo.service;

import com.booking.demo.dto.request.CreateRoomRequest;
import com.booking.demo.dto.request.UpdateRoomRequest;
import com.booking.demo.dto.response.RoomResponse;
import com.booking.demo.entity.Room;
import com.booking.demo.mapper.RoomMapper;
import com.booking.demo.repository.RoomRepository;
import com.booking.demo.specification.RoomSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public List<RoomResponse> getRooms(String name, Integer capacity, String location) {
        Specification<Room> spec = (root, query, cb) -> cb.conjunction();

        if(name != null){
            spec = spec.and(RoomSpecification.name(name));
        }

        if(capacity != null){
            spec = spec.and(RoomSpecification.capacity(capacity));
        }

        if(location != null){
            spec = spec.and(RoomSpecification.location(location));
        }

        return roomRepository.findAll(spec).stream().map(roomMapper::toResponse).toList();
    }

    public RoomResponse getRoomById(int roomID) {
        Room room = roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find a room with given ID"));

        return roomMapper.toResponse(room);
    }

    @Transactional
    public RoomResponse createRoom(CreateRoomRequest room) {
        Room roomToBeSaved = roomMapper.createRoomFromRequest(room);

        roomRepository.save(roomToBeSaved);

        return roomMapper.toResponse(roomToBeSaved);
    }

    @Transactional
    public RoomResponse updateRoom(int roomID, UpdateRoomRequest room) {
        Room roomToBeUpdated = roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find a room with given ID"));

        roomMapper.updateRoomFromRequest(roomToBeUpdated, room);

        return roomMapper.toResponse(roomToBeUpdated);
    }

    @Transactional
    public void removeRoom(@PathVariable int roomID) {
        Room roomToBeDeleted = roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException("Could not find a room with given ID"));

        roomRepository.delete(roomToBeDeleted);
    }
}
