package com.example.jaejikton_team4.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = -1,
    @Column(name = "name")
    var name: String,
    @Column(name = "description")
    var description: String? = null,
    @OneToOne
    @JoinColumn(name = "room")
    var room: Room
) {

}