package com.example.jaejikton_team4.Controller

import com.example.jaejikton_team4.Entity.User
import com.example.jaejikton_team4.Enum.ResponseStatus
import com.example.jaejikton_team4.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController {

    @GetMapping("/signup")
    fun signUp(): Response {
        throw RuntimeException()
    }

    @PostMapping("/login")
    fun login(): Response {
        return Response(ResponseStatus.OK)
    }

}