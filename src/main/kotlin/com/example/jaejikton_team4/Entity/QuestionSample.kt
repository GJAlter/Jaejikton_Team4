package com.example.jaejikton_team4.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class QuestionSample(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = -1,
    @Column(name = "description")
    var description: String,
    @Column(name = "view1")
    var view1: String,
    @Column(name = "view2")
    var view2: String
) {
}