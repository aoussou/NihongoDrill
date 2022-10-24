package com.talisol.nihongodrill.ui.states

data class QuizSelectionState(
    val selectedCategory: String? = null,
    val selectedQuestionType: String? = null,
    val selectedQuestionSource: String? = null,
    val selectedQuestionRef: String? = null,
    val levelList: List<String>? = null,
    val questionTypeList: List<String>? = null,
    val sourceList: List<String>? = null,
    val referenceList: List<String>? = null,
    val selectedLevel: String? = null,
    val groupList: List<String>? = null,
    val chosenNumberOfQuestions: Int? = null,
    val actualNumberOfQuestions: Int? = null,
    val groupChosen: String? = null,
//    val typeChosen: String? = null,
    val isQuizReady: Boolean = false
)
