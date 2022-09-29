package com.talisol.kankenkakitori.quizUtils

import android.graphics.Bitmap
import com.talisol.kankenkakitori.ui.states.DialogState
import databases.kanji.KakitoriQuestion

sealed class QuizAction{

//    object SetQuestion: QuizAction()
//    object SetAnswerList: QuizAction()

    data class SelectAnswer(val number: Int): QuizAction()
    object NextQuestion: QuizAction()
    object PreviousQuestion: QuizAction()
    object ConfirmAnswer: QuizAction()
    object StartQuiz: QuizAction()
    data class InputAnswer(val answerString: String): QuizAction()
    data class SelectQuestionLevel(val questionGroupID: String) : QuizAction()
    object EndQuiz: QuizAction()

    data class ChooseNumberOfQuestions(val number: Int?): QuizAction()



    data class ShowAlertDialog(val dialogState: DialogState): QuizAction()
    object CloseAlertDialog: QuizAction()

    object LoadSelectedGroupQuestions: QuizAction()
    object MakeLocalQuizQuestionList: QuizAction()

    data class LoadQuestionList(val qaList: List<KakitoriQuestion> ) : QuizAction()

    data class RecognizeKanji(val bitmap: Bitmap): QuizAction()

    object ResetPredictedKanji: QuizAction()

}
