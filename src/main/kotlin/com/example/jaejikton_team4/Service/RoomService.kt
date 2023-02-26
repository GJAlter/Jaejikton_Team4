package com.example.jaejikton_team4.Service

import com.example.jaejikton_team4.DTO.QuestionDTO
import com.example.jaejikton_team4.DTO.RoomDTO
import com.example.jaejikton_team4.Entity.Question
import com.example.jaejikton_team4.Entity.QuestionResult
import com.example.jaejikton_team4.Entity.Room
import com.example.jaejikton_team4.Entity.User
import com.example.jaejikton_team4.Enum.ResponseStatus
import com.example.jaejikton_team4.Exception.NotOpenResultException
import com.example.jaejikton_team4.Exception.UnknownCodeException
import com.example.jaejikton_team4.Exception.UnknownUserException
import com.example.jaejikton_team4.Repository.QuestionRepository
import com.example.jaejikton_team4.Repository.QuestionResultRepository
import com.example.jaejikton_team4.Repository.RoomRepository
import com.example.jaejikton_team4.Repository.UserRepository
import com.example.jaejikton_team4.Response
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.type.TypeFactory
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap
import kotlin.random.Random

@Service
class RoomService(
    private val questionRepository: QuestionRepository,
    private val questionResultRepository: QuestionResultRepository,
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
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

    fun enterRoom(connect: RoomDTO.Connect): Response {
        val room = roomRepository.findByCode(connect.code) ?: throw UnknownCodeException("해당 방을 찾을 수 없습니다.")
        val questions = questionRepository.getAllByRoom(room)
        val duplicateUserCount = userRepository.countByRoomIdAndNameRegex(room.id, "^${connect.name}[0-9]|^${connect.name}$")

        if(duplicateUserCount > 0) {
            connect.name = "${connect.name}${duplicateUserCount + 1}"
        }

        val objMapper = ObjectMapper().apply {
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
        }

        val roomInfo = RoomDTO.Info(
            title = room.title,
            name = connect.name,
            code = connect.code,
            questions = objMapper.convertValue(questions, TypeFactory.defaultInstance().constructCollectionType(List::class.java, QuestionDTO.Question::class.java))
        )

        userRepository.save(User(
            name = connect.name,
            description = connect.description,
            room = room
        ))

        return Response(ResponseStatus.OK, roomInfo)
    }

    fun completed(result: RoomDTO.Result): Response {
        val room = roomRepository.findByCode(result.code) ?: throw UnknownCodeException("해당 방을 찾을 수 없습니다.")
        val user = userRepository.findByRoomAndName(room, result.name)?: throw UnknownUserException("사용자를 찾을 수 없습니다.")

        for(questionResult in result.results) {
            val question = questionRepository.findById(questionResult.id).get()
            questionResultRepository.save(QuestionResult(
                question = question,
                user = user,
                answer = questionResult.select
            ))
        }

        return Response(ResponseStatus.OK)
    }

    fun getResult(name: String, code: String): Response {
        val room = roomRepository.findByCode(code)?: throw UnknownCodeException("해당 방을 찾을 수 없습니다.")
        val user = userRepository.findByRoomAndName(room, name)?: throw UnknownUserException("사용자를 찾을 수 없습니다.")
        val completedUserCount = questionResultRepository.countDistinctUserByUserRoom(room)
        if(room.openMinute != null) {
            val openTime = Calendar.getInstance().apply {
                time = room.createdDatetime
                add(Calendar.MINUTE, room.openMinute!!)
                set(Calendar.SECOND, 0)
            }
            if(openTime.time >= Date()) {
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                throw NotOpenResultException("결과 오픈 조건: ${dateFormatter.format(openTime.time)}")
            }
        } else {
            if(room.maximum > completedUserCount) {
                throw NotOpenResultException("결과 오픈 조건: ${room.maximum}명이 전부 완료(현재: ${completedUserCount}명 완료)")
            }
        }

        val view1UserListMap = LinkedHashMap<Int, ArrayList<String>>()
        val view2UserListMap = LinkedHashMap<Int, ArrayList<String>>()// ID, 사용자 이름 목록
        val questionResults = questionResultRepository.getAllByUserRoom(room)
        for(questionResult in questionResults) {
            if(view1UserListMap[questionResult.question.id] == null) {
                view1UserListMap[questionResult.question.id] = ArrayList<String>()
            }
            if(view2UserListMap[questionResult.question.id] == null) {
                view2UserListMap[questionResult.question.id] = ArrayList<String>()
            }

            if(questionResult.answer == 1) {
                view1UserListMap[questionResult.question.id]!!.add(questionResult.user.name)
            } else {
                view2UserListMap[questionResult.question.id]!!.add(questionResult.user.name)
            }
        }

        val questionTotalResultList = ArrayList<QuestionDTO.TotalResult>()

        for(questionId in view1UserListMap.keys) {
            val question = questionRepository.findById(questionId).get()
            val view1 = view1UserListMap[questionId]
            val view2 = view2UserListMap[questionId]
            val questionTotalResult = QuestionDTO.TotalResult(
                id = questionId,
                view1 = question.view1,
                view2 = question.view2,
                view1Percentage = (100.0 / completedUserCount) * view1!!.size,
                view2Percentage = (100.0 / completedUserCount) * view2!!.size,
                view1Users = view1,
                view2Users = view2
            )

            questionTotalResultList.add(questionTotalResult)
        }

        val similarMap = LinkedHashMap<String, Int>()
        val userAnswers = questionResultRepository.getAllByUser(user)
        for(userAnswer in userAnswers) {
            val sameAnswers = questionResultRepository.getAllByQuestionAndAnswerAndUserNot(userAnswer.question, userAnswer.answer, user)
            for(sameAnswer in sameAnswers) {
                if(similarMap[sameAnswer.user.name] == null) {
                    similarMap[sameAnswer.user.name] = 1
                } else {
                    similarMap[sameAnswer.user.name] = similarMap[sameAnswer.user.name]!!.plus(1)
                }
            }
        }


        val sortedList = similarMap.toList().sortedBy { it.second }.reversed()

        val rankMap = ArrayList<LinkedHashMap<String, Any>>()
        for(sortedItem in sortedList) {
            val sortedMap = LinkedHashMap<String, Any>()
            sortedMap["name"] = sortedItem.first
            sortedMap["same"] = sortedItem.second
            rankMap.add(sortedMap)
        }

        val notSameNameList = ArrayList<String>()
        for(i in similarMap.keys) {
            notSameNameList.add(i)
        }
        notSameNameList.add(user.name)
        val notSameMap = questionResultRepository.getAllByQuestionRoomAndUserNameNotIn(room, notSameNameList)
        val setList = ArrayList<LinkedHashMap<String, Any>>()

        for(i in notSameMap) {
            val notMap = LinkedHashMap<String, Any>()
            notMap["name"] = i.user.name
            notMap["same"] = 0
            setList.add(
                notMap
            )
        }

        val setList2 = setList.toMutableSet()

        for(i in setList2) {
            val notMap = LinkedHashMap<String, Any>()
                notMap["name"] = i["name"]!!
                notMap["same"] = 0
                rankMap.add(notMap)
        }

        val totalResult = RoomDTO.TotalResult(
            code = code,
            title = room.title,
            name = name,
            questionCount = questionRepository.countByRoom(room),
            results = questionTotalResultList
        )

        return Response(ResponseStatus.OK, mapOf("gameResult" to totalResult, "rank" to rankMap))
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