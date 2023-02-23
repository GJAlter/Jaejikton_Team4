package com.example.jaejikton_team4.Service

import com.example.jaejikton_team4.DTO.Room
import org.springframework.stereotype.Service

@Service
class RoomService {

    fun createRoom(room: Room.Room) {
        println(room)
    }
}