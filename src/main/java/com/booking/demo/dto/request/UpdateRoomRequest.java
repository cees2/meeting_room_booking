package com.booking.demo.dto.request;

public record UpdateRoomRequest(
                                String name,
                                String location,
                                Integer capacity) {
}
