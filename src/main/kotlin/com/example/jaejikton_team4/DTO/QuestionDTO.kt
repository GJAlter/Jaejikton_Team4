package com.example.jaejikton_team4.DTO

class QuestionDTO {

    data class Question(
        var id: Int = -1,
        var description: String = "",
        var view1: String = "",
        var view2: String = ""
    )

    data class Result(
        var id: Int,
        var select: Int,
    )
}