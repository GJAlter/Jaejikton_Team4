package com.example.jaejikton_team4.Controller

import com.example.jaejikton_team4.Enum.ResponseStatus
import com.example.jaejikton_team4.Exception.UnknownCodeException
import com.example.jaejikton_team4.Response
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorController {

    @ExceptionHandler(Exception::class)
    fun exceptionResponse(exception: Exception): Response {
        val responseStatus = when(exception) {
            is UnknownCodeException -> ResponseStatus.UNKNOWN_CODE
            else -> ResponseStatus.FAILED
        }
        exception.printStackTrace()
        return Response(responseStatus, exception.message)
    }
}