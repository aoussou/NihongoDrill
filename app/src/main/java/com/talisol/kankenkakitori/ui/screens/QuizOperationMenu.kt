package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.talisol.kankenkakitori.R
import com.talisol.kankenkakitori.actions.*
import com.talisol.kankenkakitori.ui.states.PopupState
import com.talisol.kankenkakitori.ui.states.QuizState

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

    /* investigate why crashes when removing modifier from input, even though it's not being used
    *  */

    val modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.3F)
        .background(Color.White)
        .border(BorderStroke(3.dp, Color.Blue))

    val omitQuestionDialog = PopupState(
        dialogText = "Do you give up?",
        onConfirmAction = {
            onTrackingAction(TrackingAction.StopAsking(quizState.questionGlobalId!!))
            onPopupAction(PopupAction.CloseAlertDialog)
            onQuizAction(QuizAction.NextQuestion)

            if (quizState.isLastQuestion) onQuizAction(QuizAction.EndQuiz)
        }
    )

    val giveUp = PopupState(
        dialogText = "Are you sure you want the quiz to omit this question?",
        onConfirmAction = {
            onQuizAction(QuizAction.ConfirmAnswer(onTrackingAction))
            onPopupAction(PopupAction.CloseAlertDialog)

//            if (quizState.isLastQuestion) onQuizAction(QuizAction.EndQuiz)
        }
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {

        IconButton(
            onClick = {
                onPopupAction(PopupAction.ShowAlertDialog(giveUp))
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_skip_next_24),
                contentDescription = null,
                tint = Color.Red
            )
        }

        IconButton(
            onClick = {
                onPopupAction(PopupAction.ShowAlertDialog(omitQuestionDialog))
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_stop_circle_24),
                contentDescription = null,
            )
        }


        IconButton(onClick = {
            if (quizState.inputAnswer != null) {
                var newAnswer = quizState.inputAnswer
                newAnswer = newAnswer.dropLast(1)

                if (newAnswer.isEmpty()) newAnswer = null

                onQuizAction(QuizAction.InputAnswer(newAnswer))
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_backspace_24),
                contentDescription = null,
                tint = Color.Black
            )
        }

        IconButton(onClick = {
            if (quizState.inputAnswer != null) {
                onQuizAction(QuizAction.ConfirmAnswer(onTrackingAction))
                onKanjiRecAction(KanjiRecAction.ResetPredictedKanji)
                onDrawingAction(DrawingAction.ClearAllPaths)
            }
        }) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_send_24),
                contentDescription = null,
            )
        }


        IconButton(onClick = {
            if (predictedKanji != null) {
                val newAnswer =
                    if (quizState.inputAnswer == null) {
                        predictedKanji
                    } else {
                        quizState.inputAnswer + predictedKanji
                    }
                onQuizAction(QuizAction.InputAnswer(newAnswer))
                onDrawingAction(DrawingAction.ClearAllPaths)
                onKanjiRecAction(KanjiRecAction.ResetPredictedKanji)
            }
        }) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_input_24),
                contentDescription = null,
                tint = Color.Black
            )
        }


    }



}