package com.booking.demo.dto.request;

public record CreateUserRequest(
    String firstName,
    String lastName,
    String email
) {
}
