package com.talisol.nihongodrill.ui.states

data class QuizSelectionState(
    val chosenNumberOfQuestions: Int? = null,
    val actualNumberOfQuestions: Int? = null,
    val groupChosen: String? = null,
    val typeChosen: String? = null,
    val isQuizReady: Boolean = false
)
