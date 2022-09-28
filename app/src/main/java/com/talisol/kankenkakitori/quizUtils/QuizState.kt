package com.talisol.kankenkakitori.quizUtils


data class QuizState(

    val question: String = "",
    val target: String = "",
    val correctAnswerNbr: Int? = null,
    val selectedAnswer: Int? = null,
    val isAnswerConfirmed: Boolean = false,
    val isAnswerCorrect: Boolean? = null,
    val answerList: List<String> = listOf(""),
    val localQuestionNumber: Int? = null,
    val isQuizStarted: Boolean = false,
    val isQuizOver: Boolean = false,
    val questionGlobalId : Int? = null,
    val questionGroupId: String? = null,
    val inputAnswer: String? = null,
    val correctAnswer: String? = null,
    val quizType: String? = null,
    val isLastQuestion: Boolean = false,
    val isFirstQuestion: Boolean = true,
    val chosenNumberOfQuestions: Int? = null,
    val actualNumberOfQuestions: Int? = null,
    )
