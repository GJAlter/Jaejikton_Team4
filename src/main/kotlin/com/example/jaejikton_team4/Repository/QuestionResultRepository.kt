package com.example.jaejikton_team4.Repository

import com.example.jaejikton_team4.Entity.QuestionResult
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionResultRepository: JpaRepository<QuestionResult, Int> {
}