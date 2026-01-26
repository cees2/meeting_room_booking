package com.booking.demo.repository;

import com.booking.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer>, JpaSpecificationExecutor<Booking>, PagingAndSortingRepository<Booking, Integer> {
    List<Booking> findByUserId(Integer userID);
    List<Booking> findByRoomId(Integer roomID);
}
