package com.booking.demo.specification;

import com.booking.demo.entity.Room;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {
    public static Specification<Room> name(String name){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Room> capacity(Integer capacity){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("capacity"), capacity);
    }

    public static Specification<Room> location(String location){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("location"), location);
    }
}
