package com.booking.demo.repository;

import com.booking.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserId(Integer userID);
    List<Booking> findByRoomId(Integer roomID);
    List<Booking> findByStartTimeGreaterThanAndEndTimeLessThan(LocalDateTime startTime, LocalDateTime endTime);
}
