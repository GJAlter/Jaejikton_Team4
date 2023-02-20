package com.example.jaejikton_team4.Entity

import java.util.Date

class User {

    data class SingUp(
        var userId: String,
        var userPw: String,
        var userName: String,
        var nickName: String,
        var token: String? = null,
        var createdDatetime: Date = Date(),
    )

}