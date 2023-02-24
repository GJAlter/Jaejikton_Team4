package com.example.jaejikton_team4.Entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.util.*

@Entity
class Room(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var id: Int = -1,
   @Column(name = "title")
   var title: String,
   @Column(name = "maximum")
   var maximum: Int,
   @Column(name = "open_minute")
   var openMinute: Int? = null,
   @Column(name = "code")
   var code: String,
//   @JsonFormat(timezone = "Asia/Seoul")
   @Column(name = "created_datetime")
   var createdDatetime: Date = Date()
) {

}