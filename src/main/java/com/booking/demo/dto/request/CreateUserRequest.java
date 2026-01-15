package com.booking.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateUserRequest(
        @NotEmpty
        String firstName,
        @NotEmpty
        String lastName,
        @NotEmpty
        String email
) {
}
