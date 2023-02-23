package com.example.jaejikton_team4.Service

import com.example.jaejikton_team4.DTO.RoomDTO
import com.example.jaejikton_team4.Entity.Question
import com.example.jaejikton_team4.Entity.Room
import com.example.jaejikton_team4.Enum.ResponseStatus
import com.example.jaejikton_team4.Repository.QuestionRepository
import com.example.jaejikton_team4.Repository.RoomRepository
import com.example.jaejikton_team4.Response
import org.springframework.stereotype.Service
import java.util.*
import kotlin.random.Random

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val questionRepository: QuestionRepository,
) {

    fun createRoom(room: RoomDTO.Room): Response {
        var code: String
        do {
            code = generateCode()
            if(code == "") {
                throw NullPointerException("단어목록 파일을 불러올 수 없습니다.")
            }
        } while(roomRepository.existsByCode(code))

        val createdRoom = roomRepository.saveAndFlush(Room(
            title = room.title,
            maximum = room.maximum,
            openMinute = room.openMinute,
            code = code
        ))

        for(question in room.questions) {
            questionRepository.save(Question(
                room = createdRoom,
                description = question.description,
                view1 = question.view1,
                view2 = question.view2
            ))
        }

        return Response(ResponseStatus.OK, mapOf("code" to code))
    }

    private fun generateCode(): String {
        val colorsWordInputStream = javaClass.getResourceAsStream("/words/colors.txt")
        val wordsInputStream = javaClass.getResourceAsStream("/words/words.txt")

        if(colorsWordInputStream != null && wordsInputStream != null) {
            val colors = colorsWordInputStream.bufferedReader().readLines()
            val words = wordsInputStream.bufferedReader().readLines()
            val colorIndex = Random(Date().time).nextInt(colors.size)
            val wordIndex = Random(Date().time).nextInt(words.size)

            return "${colors[colorIndex]}${words[wordIndex]}"
        }

        return ""
    }
}