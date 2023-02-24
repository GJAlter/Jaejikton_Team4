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

    data class TotalResult(
        var id: Int,
        var view1: String,
        var view2: String,
        var view1Percentage: Double,
        var view2Percentage: Double,
        var view1Users: List<String>,
        var view2Users: List<String>
    )
}