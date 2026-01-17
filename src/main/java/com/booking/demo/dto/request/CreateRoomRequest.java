package com.booking.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateRoomRequest(
        @NotEmpty
        String name,
        @Min(1)
        @NotNull
        Integer capacity,
        @NotEmpty
        String location
) {
}
