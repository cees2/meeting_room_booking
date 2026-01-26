package com.booking.demo.repository;

import com.booking.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room>, PagingAndSortingRepository<Room, Integer> {
}
