package com.example.jaejikton_team4.Repository

import com.example.jaejikton_team4.Entity.Room
import com.example.jaejikton_team4.Entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {

    fun existsByRoomAndName(room: Room, name: String): Boolean
    fun countByRoomAndName(room: Room, name: String): Int
}