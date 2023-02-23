package com.example.jaejikton_team4.Entity

import jakarta.persistence.*

@Entity
class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = -1,
    @OneToOne
    @JoinColumn(name = "room")
    var room: Room,
    @Column(name = "description")
    var description: String,
    @Column(name = "view1")
    var view1: String,
    @Column(name = "view2")
    var view2: String,
) {

}