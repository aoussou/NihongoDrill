package com.talisol.kankenkakitori.quizUtils

sealed class QuizAction{

//    object SetQuestion: QuizAction()
//    object SetAnswerList: QuizAction()

    data class SelectAnswer(val number: Int): QuizAction()
    object NextQuestion: QuizAction()
    object PreviousQuestion: QuizAction()
    object ConfirmAnswer: QuizAction()
    object StartQuiz: QuizAction()
    data class InputAnswer(val answerString: String): QuizAction()
    data class SelectQuestionGroup(val questionGroupID: String) : QuizAction()
    data class SelectQuizType(val quizType: String) : QuizAction()
    object EndQuiz: QuizAction()

    data class ChooseNumberOfQuestions(val number: Int?): QuizAction()

    object AddOneCorrect: QuizAction()
    object AddOneWrong: QuizAction()

    object SubtractOneCorrect: QuizAction()
    object SubtractOneWrong: QuizAction()

    object StopAsking: QuizAction()
    object MarkForReview: QuizAction()

    data class ShowAlertDialog(val dialogState: DialogState): QuizAction()
    object CloseAlertDialog: QuizAction()


}
