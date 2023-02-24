package com.example.jaejikton_team4.Repository

import com.example.jaejikton_team4.Entity.QuestionSample
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionSampleRepository: JpaRepository<QuestionSample, Int> {
}