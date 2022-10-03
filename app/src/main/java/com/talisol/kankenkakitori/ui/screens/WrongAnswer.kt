package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.PopupAction
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.actions.TrackingAction
import com.talisol.kankenkakitori.ui.states.PopupState
import com.talisol.kankenkakitori.ui.states.QuizState
import com.talisol.kankenkakitori.ui.theme.DarkGreen

@Composable
fun WrongAnswer(
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


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Text(text = "Your answer:")
            }
            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {


                    if (quizState.questionType == "goji") {

                        if (quizState.selectedWrongKanji != null) {
                            Text(
                                text = "${quizState.selectedWrongKanji} ➔",
                                color = Color.Red,
                                fontSize = 16.sp
                            )
                        }
                    }

                    if (quizState.inputAnswer != null) {
                        Text(
                            text = quizState.inputAnswer,
                            color = Color.Red,
                            fontSize = 16.sp
                        )

                }



            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Text(text = "Correct answer:")
            }

            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {

                if (quizState.questionType == "goji") {

                    Text(
                        text = "${quizState.target} ➔",
                        color = DarkGreen,
                        fontSize = 16.sp
                    )
                }

                Text(
                    text = quizState.correctAnswer!!,
                    color = DarkGreen,
                    fontSize = 16.sp
                )
            }
        }

    }

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Button(
            modifier = Modifier.fillMaxWidth(.5f),
            onClick = {
                onPopupAction(PopupAction.ShowAlertDialog(iWasRightDialog))
            }
        ) {
            Text("I WAS\nRIGHT!")
        }

        Button(
            modifier = Modifier.fillMaxWidth(.5f),
            onClick = {
                onPopupAction(PopupAction.ShowAlertDialog(markForReviewDialog))
            }
        ) {
            Text("MARK")
        }
    }


}