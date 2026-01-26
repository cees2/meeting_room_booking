package com.booking.demo.specification;

import com.booking.demo.entity.Booking;
import com.booking.demo.enums.BookingStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class BookingSpecifications {
    public static Specification<Booking> startTimeAt(LocalDateTime startTime) {
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("startTime"), startTime);
    }

    public static Specification<Booking> endTimeAt(LocalDateTime endTime) {
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("endTime"), endTime);
    }

    public static Specification<Booking> roomAndBetweenTimeAt(LocalDateTime starTime, LocalDateTime endTime, Integer roomID) {
        Specification<Booking> spec = (root, query, builder) -> builder.conjunction();

        if(starTime != null){
            spec = spec.and((root, query, builder) -> builder.greaterThan(root.get("endTime"), starTime));
        }

        if(endTime != null){
            spec = spec.and((root, query, builder) -> builder.lessThan(root.get("startTime"), endTime));
        }

        if(roomID != null){
            spec = spec.and((root, query, builder) -> builder.equal(root.get("room").get("id"), roomID));
        }

        return spec;
    }

    public static Specification<Booking> purpose(String purpose) {
        return (root, query, builder) -> builder.equal(root.get("purpose"), purpose);
    }

    public static Specification<Booking> status(BookingStatus status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }

    public static Specification<Booking> user(Integer userId) {
        return (root, query, builder) -> builder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Booking> room(Integer roomId) {
        return (root, query, builder) -> builder.equal(root.get("room").get("id"), roomId);
    }
}
