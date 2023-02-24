package com.example.jaejikton_team4.DTO

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
        var questions: List<QuestionDTO.Question>
    )

}