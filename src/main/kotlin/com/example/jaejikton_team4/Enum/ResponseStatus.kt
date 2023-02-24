package com.example.jaejikton_team4.Enum

import org.springframework.http.HttpStatus

enum class ResponseStatus(
    val statusCode: Int,
    val msg: String,
    val httpStatus: HttpStatus,
) {
    FAILED(100, "기타오류", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_CODE(101, "방 코드 오류", HttpStatus.BAD_REQUEST),

    OK(200, "정상 처리", HttpStatus.OK)

}