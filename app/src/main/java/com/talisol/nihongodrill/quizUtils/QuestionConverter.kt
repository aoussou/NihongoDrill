package com.talisol.nihongodrill.quizUtils

import databases.KankenQuestion


fun convert2Question(kankenQuestion: KankenQuestion): Question {


    val question = Question(
        global_id = kankenQuestion.global_id_,
        question_type = kankenQuestion.question_type,
        question = kankenQuestion.question,
        answer = kankenQuestion.answer,
        target = kankenQuestion.target,
        mca_list = kankenQuestion.mca_list,
        total_correct = kankenQuestion.total_correct,
        total_wrong = kankenQuestion.total_wrong,
        correct_streak = kankenQuestion.correct_streak,
        last_correct_date = kankenQuestion.last_correct_date,
        available = kankenQuestion.available,
        marked_for_review = kankenQuestion.marked_for_review
    )

    return question

}

fun converDBquestionList(qList: List<KankenQuestion>): List<Question> {

    val newList = mutableListOf<Question>()
    for (q in qList) {
        val newQ = convert2Question(q)
        newList.add(newQ)
    }

    return newList
}