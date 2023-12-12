package com.talisol.nihongodrill.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.talisol.nihongodrill.actions.*
import com.talisol.nihongodrill.ui.states.PopupState
import com.talisol.nihongodrill.ui.states.QuizState

@Composable
fun QuizOperationMenu(
    quizState: QuizState,
    predictedKanji: String?,
    onQuizAction: (QuizAction) -> Unit,
    onPopupAction: (PopupAction) -> Unit,
    onTrackingAction: (TrackingAction) -> Unit,
    onKanjiRecAction: (KanjiRecAction) -> Unit,
    onDrawingAction: (DrawingAction) -> Unit,
) {

//    val modifier = Modifier
//        .fillMaxWidth()
//        .fillMaxHeight(.25F)
//        .background(Color.White)
//        .border(BorderStroke(3.dp, Color.Blue))
//
//    val omitQuestionDialog = PopupState(
//        dialogText = "Are you sure you want the quiz to omit this question?",
//        onConfirmAction = {
//            onTrackingAction(TrackingAction.StopAsking(quizState.questionGlobalId!!))
//            onPopupAction(PopupAction.CloseAlertDialog)
//            onQuizAction(QuizAction.ConfirmAnswer(onTrackingAction))
//
//            if (quizState.isLastQuestion) onQuizAction(QuizAction.EndQuiz)
//        }
//    )
//
//    val giveUp = PopupState(
//        dialogText = "Do you give up?",
//        onConfirmAction = {
//            onQuizAction(QuizAction.ConfirmAnswer(onTrackingAction))
//            onDrawingAction(DrawingAction.ClearAllPaths)
//            onKanjiRecAction(KanjiRecAction.ResetPredictedKanji)
//            onPopupAction(PopupAction.CloseAlertDialog)
//
//        }
//    )
//
//    val confirmSubmission = PopupState(
//        dialogText = "Are you sure you want to submit your answers?",
//        onConfirmAction = {
//            onQuizAction(QuizAction.ConfirmAnswersList(onTrackingAction))
//            onPopupAction(PopupAction.CloseAlertDialog)
//        }
//    )
//
//    val markForReviewDialog = PopupState(
//        dialogText = "Are you sure you want to mark this question for review?",
//        onConfirmAction =
//        {
//            onPopupAction(PopupAction.CloseAlertDialog)
//            onTrackingAction(TrackingAction.MarkForReview(quizState.questionGlobalId!!))
//        }
//    )
//
//    Row(
//        modifier = modifier,
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly,
//    ) {
//
//        IconButton(
//            onClick = {
//                onPopupAction(PopupAction.ShowAlertDialog(omitQuestionDialog))
//            }
//
//
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_outline_stop_circle_24),
//                contentDescription = null,
//                tint = Color.Red
//            )
//        }
//
//
//        IconButton(
//            onClick = {
//                onPopupAction(PopupAction.ShowAlertDialog(markForReviewDialog))
//            }
//
//
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_baseline_bookmark_24),
//                contentDescription = null,
//                tint = Color.Red
//            )
//        }
//
//
//        IconButton(
//            onClick = {
//                onPopupAction(PopupAction.ShowAlertDialog(giveUp))
//            }
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_baseline_skip_next_24),
//                contentDescription = null,
//            )
//        }
//
//
//        IconButton(onClick = {
//            if (quizState.inputAnswer != null) {
//                var newAnswer = quizState.inputAnswer
//                newAnswer = newAnswer.dropLast(1)
//
//                if (newAnswer.isEmpty()) newAnswer = null
//
//                onQuizAction(QuizAction.InputAnswer(newAnswer))
//            }
//        }) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_baseline_backspace_24),
//                contentDescription = null,
//                tint = Color.Black
//            )
//        }
//
//        IconButton(onClick = {
//            if (quizState.inputAnswer != null) {
//                if (quizState.isKanjiRecRequired) {
//                    onQuizAction(QuizAction.ConfirmAnswer(onTrackingAction))
//                    onKanjiRecAction(KanjiRecAction.ResetPredictedKanji)
//                    onDrawingAction(DrawingAction.ClearAllPaths)
//                }
//            } else if (
//                quizState.questionFormat == "shikibetsu"
//                || quizState.questionFormat == "douon"
//                || quizState.questionFormat == "taigi"
//            ) {
//                onPopupAction(PopupAction.ShowAlertDialog(confirmSubmission))
//            }
//        }) {
//            Icon(
//                painterResource(id = R.drawable.ic_baseline_send_24),
//                contentDescription = null,
//            )
//        }
//
//
//        IconButton(onClick = {
//            if (predictedKanji != null) {
//
//                if (quizState.questionFormat == "taigi" && quizState.selectedSubQuestionNbr != null) {
//                    onQuizAction(
//                        QuizAction.UpdateAnswersList(
//                            predictedKanji,
//                            quizState.selectedSubQuestionNbr
//                        )
//                    )
//                    Log.i("DEBUG taigi","update sent")
//                } else {
//                    val newAnswer =
//                        if (quizState.inputAnswer == null) {
//                            predictedKanji
//                        } else {
//                            quizState.inputAnswer + predictedKanji
//                        }
//                    onQuizAction(QuizAction.InputAnswer(newAnswer))
//                }
//
//
//                onDrawingAction(DrawingAction.ClearAllPaths)
//                onKanjiRecAction(KanjiRecAction.ResetPredictedKanji)
//
//            }
//        }) {
//            Icon(
//                painterResource(id = R.drawable.ic_baseline_input_24),
//                contentDescription = null,
//                tint = Color.Black
//            )
//        }
//
//
//    }


}