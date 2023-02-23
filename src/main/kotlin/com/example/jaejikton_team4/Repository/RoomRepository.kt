package com.example.jaejikton_team4.Repository

import com.example.jaejikton_team4.Entity.Room
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository: JpaRepository<Room, Int> {

    fun existsByCode(code: String): Boolean

}