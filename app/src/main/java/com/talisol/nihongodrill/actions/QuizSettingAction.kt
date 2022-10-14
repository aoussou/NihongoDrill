package com.talisol.nihongodrill.actions

sealed class QuizSettingAction {
    data class SelectQuestionLevel(val questionGroupID: String?) : QuizSettingAction()
    data class ChooseNumberOfQuestions(val number: Int?) : QuizSettingAction()
    data class ChooseQuestionType(val type: String?) : QuizSettingAction()
    object LoadSelectedGroupQuestions : QuizSettingAction()
    object MakeLocalQuizQuestionList : QuizSettingAction()
}