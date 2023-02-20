package com.example.jaejikton_team4

import com.example.jaejikton_team4.Enum.ResponseStatus
import lombok.Getter
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

//data class Response (
//    val status: ResponseStatus,
//    val data: Any? = ""
//): ResponseEntity<Any>(data, status.httpStatus) {
//}

class Response(status: ResponseStatus, data: Any? = "") : ResponseEntity<Any>(ResponseData(status.statusCode, "${status.name} - ${status.msg}", data), status.httpStatus) {

    data class ResponseData(
        val statusCode: Int,
        val message: String,
        val result: Any?
    )

}