package com.example.jaejikton_team4.DTO

class RoomDTO {

    data class Room(
        var title: String,
        var maximum: Int,
        var openMinute: Int? = null,
        var questions: List<QuestionDTO.Question>
    )

}