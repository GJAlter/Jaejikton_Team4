package com.example.jaejikton_team4.Controller

import com.example.jaejikton_team4.DTO.RoomDTO
import com.example.jaejikton_team4.Response
import com.example.jaejikton_team4.Service.RoomService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/room")
class RoomController(
    private val roomService: RoomService
) {

    @PostMapping("/create")
    fun createRoom(@RequestBody room: RoomDTO.Room): Response {
        return roomService.createRoom(room)
    }

}