package com.talisol.nihongodrill.quizUtils

import databases.JlptQuestion
import databases.KankenQuestion


fun convert2Question(kankenQuestion: KankenQuestion): Question {

    val question = Question(
        global_id = kankenQuestion.global_id_,
        question_type = kankenQuestion.question_type,
        format = kankenQuestion.format,
        question = kankenQuestion.question,
        answer = kankenQuestion.answer,
        target = kankenQuestion.target,
        mca_list = kankenQuestion.mca_list,
        total_correct = kankenQuestion.total_correct,
        total_wrong = kankenQuestion.total_wrong,
        correct_streak = kankenQuestion.correct_streak,
        last_correct_date = kankenQuestion.last_correct_date,
        available = kankenQuestion.available,
        marked_for_review = kankenQuestion.marked_for_review,
        level = kankenQuestion.kyu,
        reference = kankenQuestion.reference,
        source = kankenQuestion.source
    )

    return question

}

fun convert2Question(jlptQuestion: JlptQuestion): Question {

    val question = Question(
        global_id = jlptQuestion.global_id_,
        question_type = jlptQuestion.question_type,
        format = jlptQuestion.format,
        question = jlptQuestion.question,
        answer = jlptQuestion.answer,
        target = jlptQuestion.target,
        mca_list = jlptQuestion.mca_list,
        total_correct = jlptQuestion.total_correct,
        total_wrong = jlptQuestion.total_wrong,
        correct_streak = jlptQuestion.correct_streak,
        last_correct_date = jlptQuestion.last_correct_date,
        available = jlptQuestion.available,
        marked_for_review = jlptQuestion.marked_for_review,
        level = jlptQuestion.level,
        reference = jlptQuestion.reference,
        source = jlptQuestion.source
    )

    return question

}

inline fun <reified T> convertDBquestionList(qList: List<T>): List<Question> {

    val newList = mutableListOf<Question>()

    when {

        KankenQuestion::class.java.isAssignableFrom(T::class.java) -> {
            for (q in qList) {
                    val newQ = convert2Question(q as KankenQuestion)
                    newList.add(newQ)
            }
        }

        JlptQuestion::class.java.isAssignableFrom(T::class.java) -> {
            for (q in qList) {
                val newQ = convert2Question(q as JlptQuestion)
                newList.add(newQ)
            }
        }


    }


    return newList
}

//fun convertDBquestionList(qList: List<KankenQuestion>): List<Question> {
//
//    val newList = mutableListOf<Question>()
//
//
//            for (q in qList) {
//                val newQ = convert2Question(q)
//                newList.add(newQ)
//            }
//
//
//
//
//    return newList
//}

//fun convertDBquestionList(qList: List<JlptQuestion>): List<Question> {
//
//    val newList = mutableListOf<Question>()
//    for (q in qList) {
//        val newQ = convert2Question(q)
//        newList.add(newQ)
//    }
//
//    return newList
//}

