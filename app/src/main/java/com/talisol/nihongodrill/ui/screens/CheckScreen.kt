package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.talisol.nihongodrill.actions.PopupAction
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.actions.TrackingAction
import com.talisol.nihongodrill.ui.states.PopupState
import com.talisol.nihongodrill.ui.states.QuizState

@Composable
fun CheckScreen(
    quizState: QuizState,
    onPopupAction: (PopupAction) -> Unit,
    onTrackingAction: (TrackingAction) -> Unit,
    onQuizAction: (QuizAction) -> Unit
) {

    val iWasRightDialog = PopupState(
        dialogText = "Are you sure you got this question right?",
        onConfirmAction =
        {
            val questionId = quizState.questionGlobalId!!
            onPopupAction(PopupAction.CloseAlertDialog)
            onTrackingAction(TrackingAction.AddOneCorrect(questionId))
            onTrackingAction(TrackingAction.SubtractOneWrong(questionId))
            onQuizAction(QuizAction.NextQuestion)
        }
    )

    val markForReviewDialog = PopupState(
        dialogText = "Are you sure you want to mark this question for review?",
        onConfirmAction =
        {
            onPopupAction(PopupAction.CloseAlertDialog)
            onTrackingAction(TrackingAction.MarkForReview(quizState.questionGlobalId!!))
        }
    )




    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clickable {
                    onPopupAction(PopupAction.ShowAlertDialog(iWasRightDialog))
                }
                .border(BorderStroke(2.dp, Color.Black)),
            contentAlignment = Alignment.Center
        ) {
            Text("STOP ASKING")
        }

        Box(
            modifier = Modifier
                .clickable {
                onPopupAction(PopupAction.ShowAlertDialog(iWasRightDialog))
            }
                .border(BorderStroke(2.dp, Color.Black)),
            contentAlignment = Alignment.Center
        ) {
            Text("I WAS\nRIGHT!")
        }

        Box(
            modifier = Modifier.
            clickable {
                onPopupAction(PopupAction.ShowAlertDialog(markForReviewDialog))
            }
                .border(BorderStroke(2.dp, Color.Black)),
            contentAlignment = Alignment.Center
        ) {
            Text("MARK")
        }

        if (quizState.isLastQuestion) {
            Box(
                modifier = Modifier
                    .clickable {
                    onQuizAction(QuizAction.EndQuiz)
                }
                    .border(BorderStroke(2.dp, Color.Black)),
                contentAlignment = Alignment.Center
            ) {
                Text("END")
            }
        } else {
            Box(
                modifier = Modifier
                    .clickable {
                    onQuizAction(QuizAction.NextQuestion)
                }
                    .border(BorderStroke(2.dp, Color.Black)),
                contentAlignment = Alignment.Center
            ) {
                Text("NEXT")
            }
        }
    }


}