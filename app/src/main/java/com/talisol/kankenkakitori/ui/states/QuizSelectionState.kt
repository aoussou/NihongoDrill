package com.talisol.kankenkakitori.ui.states

data class QuizSelectionState(
    val chosenNumberOfQuestions: Int? = null,
    val actualNumberOfQuestions: Int? = null,
    val groupChosen: String? = null,
    val isQuizReady: Boolean = false
)
