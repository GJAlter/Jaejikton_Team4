package com.example.jaejikton_team4.Repository

import com.example.jaejikton_team4.Entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
}