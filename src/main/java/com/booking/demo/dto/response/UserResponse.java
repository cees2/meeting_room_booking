package com.booking.demo.dto.response;

public record UserResponse(
        Integer id,
        String firstName,
        String lastName,
        String email
) {
}
