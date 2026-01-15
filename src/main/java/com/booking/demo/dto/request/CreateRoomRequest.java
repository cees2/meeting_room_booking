package com.booking.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record CreateRoomRequest(
        @NotEmpty
        String name,
        @Min(1)
        Integer capacity,
        @NotEmpty
        String location
) {
}
