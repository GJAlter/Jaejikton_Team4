package com.example.jaejikton_team4.Enum

import org.springframework.http.HttpStatus

enum class ResponseStatus(
    val statusCode: Int,
    val msg: String,
    val httpStatus: HttpStatus,
) {
    FAILED(100, "기타오류", HttpStatus.INTERNAL_SERVER_ERROR),

    OK(200, "정상 처리", HttpStatus.OK)

}