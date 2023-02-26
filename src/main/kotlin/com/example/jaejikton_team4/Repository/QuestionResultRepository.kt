package com.example.jaejikton_team4.Repository

import com.example.jaejikton_team4.Entity.Question
import com.example.jaejikton_team4.Entity.QuestionResult
import com.example.jaejikton_team4.Entity.Room
import com.example.jaejikton_team4.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface QuestionResultRepository: JpaRepository<QuestionResult, Int> {

    @Query(
        "SELECT COUNT(DISTINCT qr.user) " +
                "FROM QuestionResult qr " +
                "WHERE qr.user.room = :room"
    )
    fun countDistinctUserByUserRoom(@Param("room") room: Room): Int

    fun getAllByUserRoom(room: Room): List<QuestionResult>
    fun getAllByUser(user: User): List<QuestionResult>
    fun getAllByQuestionAndAnswerAndUserNot(question: Question, answer: Int, user: User): List<QuestionResult>
    fun getAllByQuestionRoomAndUserNameNotIn(room: Room, user: List<String>): List<QuestionResult>

}