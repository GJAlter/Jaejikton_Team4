package com.example.jaejikton_team4.Repository

import com.example.jaejikton_team4.Entity.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository: JpaRepository<Question, Int> {
}