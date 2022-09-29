package com.talisol.kankenkakitori.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kanjirecognizercompose.drawingUtils.DrawingAction
import com.talisol.kanjirecognizercompose.screens.KanjiRecognitionScreen
import com.talisol.kankenkakitori.drawingUtils.DrawingState
import com.talisol.kankenkakitori.ui.states.DialogState
import com.talisol.kankenkakitori.quizUtils.QuizAction
import com.talisol.kankenkakitori.quizUtils.TrackingAction
import com.talisol.kankenkakitori.ui.states.QuizState
import com.talisol.kankenkakitori.ui.theme.DarkGreen

@Composable
fun KankenQuizScreen(
    state: QuizState,
    dialogState: DialogState,
    onAction: (QuizAction) -> Unit,
    currentPath: Path,
    drawingState: DrawingState,
    drawingAction: (DrawingAction) -> Unit,
    kanjiRecognizerOnAction: (QuizAction) -> Unit,
    predictedKanji: String?,
    trackingOnAction: (TrackingAction) -> Unit
) {

    val omitQuestionDialog = DialogState(
        dialogText = "Are you sure you want the quiz to omit this question?",
        onConfirmAction = {
            trackingOnAction(TrackingAction.StopAsking(state.questionGlobalId!!))
            onAction(QuizAction.CloseAlertDialog)
            onAction(QuizAction.NextQuestion)

            if (state.isLastQuestion) onAction(QuizAction.EndQuiz)
        }
    )

    val iWasRightDialog = DialogState(
        dialogText = "Are you sure you got this question right?",
        onConfirmAction =
        {
            onAction(QuizAction.CloseAlertDialog)
            trackingOnAction(TrackingAction.AddOneCorrect(state.questionGlobalId!!))
            trackingOnAction(TrackingAction.SubtractOneWrong(state.questionGlobalId))
            onAction(QuizAction.NextQuestion)
        }
    )

    val markForReviewDialog = DialogState(
        dialogText = "Are you sure you want to mark this question for review?",
        onConfirmAction =
        {
            onAction(QuizAction.CloseAlertDialog)
            trackingOnAction(TrackingAction.MarkForReview(state.questionGlobalId!!))
        }
    )

    QuizAlertDialog(dialogState = dialogState, onAction = onAction)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        Button(
            modifier = Modifier.fillMaxWidth(.5f),
            onClick = {
                onAction(QuizAction.ShowAlertDialog(omitQuestionDialog))
            }
        ) {
            Text("STOP\nASKING")
        }

        QuestionScreen(state = state, onAction = onAction)



        if (!state.isAnswerConfirmed) {

            Row {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.25F)
                        .aspectRatio(1f)
                        .border(BorderStroke(1.dp, Color.Blue))
                        .background(Color.White),
                    contentAlignment = Alignment.Center

                ) {
                    if (predictedKanji != null) {
                        Text(
                            predictedKanji,
                            fontSize = 72.sp,
                            color = Color.Black
                        )
                    }
                }

                Button(
                    modifier = Modifier.fillMaxWidth(.5f),
                    onClick = {
                        if (predictedKanji != null) {
                            onAction(QuizAction.InputAnswer(predictedKanji))
                            onAction(QuizAction.ConfirmAnswer)
                            kanjiRecognizerOnAction(QuizAction.ResetPredictedKanji)
                            drawingAction(DrawingAction.ClearAllPaths)

                            if (state.isAnswerCorrect == true) {

                                trackingOnAction(TrackingAction.AddOneCorrect(state.questionGlobalId!!))

                            } else if (state.isAnswerCorrect == false) {
                                trackingOnAction(TrackingAction.AddOneWrong(state.questionGlobalId!!))
                            } else {
                                Log.i("DEBUG",state.isAnswerCorrect.toString())
                            }
                        }

                    }
                ) {
                    Text("CONFIRM")
                }
            }


            KanjiRecognitionScreen(
                currentPath,
                drawingState,
                drawingAction,
                kanjiRecognizerOnAction
            )


        } else {
            if (!state.isAnswerCorrect!!) {

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
                            Text(
                                text = state.inputAnswer!!,
                                color = Color.Red,
                                fontSize = 16.sp
                            )
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
                            Text(
                                text = state.correctAnswer!!,
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
                            onAction(QuizAction.ShowAlertDialog(iWasRightDialog))
                        }
                    ) {
                        Text("I WAS\nRIGHT!")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(.5f),
                        onClick = {
                            onAction(QuizAction.ShowAlertDialog(markForReviewDialog))
                        }
                    ) {
                        Text("MARK")
                    }
                }


            } else {
                Text(text = state.correctAnswer!!)
            }


            if (state.isLastQuestion) {
                Button(
                    modifier = Modifier.fillMaxWidth(.5f),
                    onClick = {
                        onAction(QuizAction.EndQuiz)
                    }
                ) {
                    Text("END")
                }
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth(.5f),
                    onClick = {
                        onAction(QuizAction.NextQuestion)
                    }
                ) {
                    Text("NEXT")
                }
            }


        }
    }



}




