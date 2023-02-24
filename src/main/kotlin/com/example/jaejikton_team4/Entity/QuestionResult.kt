package com.example.jaejikton_team4.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
class QuestionResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = -1,
    @OneToOne
    @JoinColumn(name = "question")
    var question: Question,
    @OneToOne
    @JoinColumn(name = "user")
    var user: User,
    @Column(name = "answer")
    var answer: Int
) {
}