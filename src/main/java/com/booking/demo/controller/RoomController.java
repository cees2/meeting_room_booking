package com.booking.demo.controller;

import com.booking.demo.entity.Room;
import com.booking.demo.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public Room getRoom(){

    }
}
