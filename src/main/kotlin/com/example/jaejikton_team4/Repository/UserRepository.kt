package com.example.jaejikton_team4.Repository

import com.example.jaejikton_team4.Entity.Room
import com.example.jaejikton_team4.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository: JpaRepository<User, Int> {

    fun countByRoom(room: Room): Int

    fun findByRoomAndName(room: Room, name: String): User?

    @Query(
        nativeQuery = true,
        value = "SELECT COUNT(*) " +
                "FROM user " +
                "WHERE room = :room " +
                "AND user.name REGEXP :nameRegex"
    )
    fun countByRoomIdAndNameRegex(@Param("room") roomId: Int, @Param("nameRegex") nameRegex: String): Int
}