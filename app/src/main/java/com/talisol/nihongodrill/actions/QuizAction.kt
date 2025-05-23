package com.talisol.nihongodrill.actions

import databases.kanji.SelectKakitoriQuestions


sealed class QuizAction {

    object NextQuestion : QuizAction()
    object PreviousQuestion : QuizAction()
    data class ConfirmAnswer(val trackingOnAction: (TrackingAction) -> Unit) : QuizAction()
    data class ConfirmAnswersList(val trackingOnAction: (TrackingAction) -> Unit) : QuizAction()
    data class LoadQuestionList(val qaList: List<SelectKakitoriQuestions>) : QuizAction()
    data class InputAnswer(val answerString: String?) : QuizAction()
    data class SelectWrongKanji(val kanji: String?,val kanjiInd: Int?): QuizAction()
    object StartQuiz: QuizAction()
    object EndQuiz : QuizAction()

    data class SetSelectedSubQuestion(val number: Int): QuizAction()

    data class SetQuizType(val questionType: String) : QuizAction()
    data class UpdateAnswersList(val answer: String, val index: Int) : QuizAction()

//    data class SelectAnswer(val number: Int): QuizActio


}
