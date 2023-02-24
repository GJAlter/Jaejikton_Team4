package com.example.jaejikton_team4.Enum

import org.springframework.http.HttpStatus

enum class ResponseStatus(
    val statusCode: Int,
    val msg: String,
    val httpStatus: HttpStatus,
) {
    FAILED(100, "기타오류", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_CODE(101, "방 코드 오류", HttpStatus.BAD_REQUEST),
    UNKNOWN_USER(102, "사용자 오류", HttpStatus.BAD_REQUEST),
    NOT_OPEN_RESULT(103, "결과오픈 조건 미달성", HttpStatus.BAD_REQUEST),

    OK(200, "정상 처리", HttpStatus.OK)

}