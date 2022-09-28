package com.talisol.kankenkakitori.viewModels

data class QuizSelectionState(
    val chosenNumberOfQuestions: Int? = null,
    val actualNumberOfQuestions: Int? = null,
    val groupChosen: String? = null,
    val isQuizStarted: Boolean = false
)
