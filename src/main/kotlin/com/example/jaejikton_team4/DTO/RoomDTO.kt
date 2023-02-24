package com.example.jaejikton_team4.DTO

import com.example.jaejikton_team4.Entity.Question

class RoomDTO {

    data class Room(
        var title: String,
        var maximum: Int,
        var openMinute: Int? = null,
        var questions: List<QuestionDTO.Question>
    )

    data class Connect(
        var name: String,
        var description: String?,
        var code: String
    )

    data class Info(
        var title: String,
        var name: String,
        var code: String,
        var questions: List<Question>
    )

    data class Result(
        var code: String,
        var name: String,
        var results: List<QuestionDTO.Result>
    )

    data class TotalResult(
        var code: String,
        var title: String,
        var name: String,
        var results: List<QuestionDTO.TotalResult>
    )

}