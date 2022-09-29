package com.talisol.kankenkakitori.actions

sealed class QuizSettingAction {
    data class SelectQuestionLevel(val questionGroupID: String) : QuizSettingAction()
    data class ChooseNumberOfQuestions(val number: Int?) : QuizSettingAction()
    object StartQuiz : QuizSettingAction()
    object LoadSelectedGroupQuestions : QuizSettingAction()
    object MakeLocalQuizQuestionList : QuizSettingAction()
}