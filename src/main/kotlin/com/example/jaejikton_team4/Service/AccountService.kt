package com.example.jaejikton_team4.Service

import com.example.jaejikton_team4.Entity.User
import com.example.jaejikton_team4.Enum.ResponseStatus
import com.example.jaejikton_team4.Response
import org.springframework.stereotype.Service

@Service
class AccountService {

    fun signUp(signUp: User.SingUp): Response {
        return Response(ResponseStatus.OK)
    }
}