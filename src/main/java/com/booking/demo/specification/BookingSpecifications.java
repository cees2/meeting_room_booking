package com.booking.demo.specification;

import com.booking.demo.entity.Booking;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class BookingSpecifications {
    public static Specification<Booking> startTimeAt(LocalDateTime startTime){
        return (root, query, builder) ->
             builder.greaterThanOrEqualTo(root.get("startTime"), startTime);
    }

    public static Specification<Booking> endTimeAt(LocalDateTime endTime){
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("endTime"), endTime);
    }
}
