package com.booking.demo.specification;

import com.booking.demo.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> firstName(String firstName){
        return (root, query, builder) -> builder.like(root.get("firstName"), firstName);
    }

    public static Specification<User> lastName(String lastName){
        return (root, query, builder) -> builder.like(root.get("lastName"), lastName);
    }

    public static Specification<User> email(String email){
        return (root, query, builder) -> builder.equal(root.get("email"), email);
    }
}
