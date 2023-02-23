package com.example.jaejikton_team4.DTO

class Room {

    data class Room(
        var title: String,
        var maximum: Int,
        var openMinute: Int? = null,
        var questions: List<Question.Question>
    )

}