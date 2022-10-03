package com.talisol.kankenkakitori.actions

import databases.kanji.SelectKakitoriQuestionByKankenKyu


sealed class QuizAction {

    object NextQuestion : QuizAction()
    object PreviousQuestion : QuizAction()
    data class ConfirmAnswer(val trackingOnAction: (TrackingAction) -> Unit) : QuizAction()
    data class LoadQuestionList(val qaList: List<SelectKakitoriQuestionByKankenKyu>) : QuizAction()
    data class InputAnswer(val answerString: String?) : QuizAction()
    object StartQuiz: QuizAction()
    object EndQuiz : QuizAction()

//    data class SelectAnswer(val number: Int): QuizAction()


}
