package com.talisol.nihongodrill.actions

sealed class QuizSettingAction {
    data class SelectCategory(val category: String?) : QuizSettingAction()
    data class SelectQuestionLevel(val level: String?) : QuizSettingAction()
    data class SelectQuestionType(val type: String?) : QuizSettingAction()
    data class SelectQuestionSource(val source: String?) : QuizSettingAction()
    data class SelectQuestionReference(val reference: String?) : QuizSettingAction()
    data class SetIsShuffle(val isShuffle: Boolean) : QuizSettingAction()
    data class SetIsOnlyNeverAnswered(val isOnlyNeverAnswered: Boolean) : QuizSettingAction()


    object ApplyAllQuestionSelectors: QuizSettingAction()


    data class ChooseNumberOfQuestions(val number: Int?) : QuizSettingAction()

    object LoadSelectedGroupQuestions : QuizSettingAction()
    object MakeLocalQuizQuestionList : QuizSettingAction()
}