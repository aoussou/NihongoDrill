package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.talisol.kankenkakitori.actions.*
import com.talisol.kankenkakitori.drawingUtils.DrawingState
import com.talisol.kankenkakitori.ui.MySpinner
import com.talisol.kankenkakitori.ui.states.PopUpState
import com.talisol.kankenkakitori.ui.states.QuizState
import com.talisol.kankenkakitori.ui.theme.DarkGreen

@Composable
fun KankenQuizScreen(
    state: QuizState,
    popUpState: PopUpState,
    onAction: (QuizAction) -> Unit,
    currentPath: Path,
    drawingState: DrawingState,
    drawingAction: (DrawingAction) -> Unit,
    kanjiRecognizerOnAction: (KanjiRecAction) -> Unit,
    predictedKanji: String?,
    otherGuessesList: List<String>?,
    trackingOnAction: (TrackingAction) -> Unit,
    onPopUpAction: (PopUpAction) -> Unit
) {


    val omitQuestionDialog = PopUpState(
        dialogText = "Are you sure you want the quiz to omit this question?",
        onConfirmAction = {
            trackingOnAction(TrackingAction.StopAsking(state.questionGlobalId!!))
            onPopUpAction(PopUpAction.CloseAlertDialog)
            onAction(QuizAction.NextQuestion)

            if (state.isLastQuestion) onAction(QuizAction.EndQuiz)
        }
    )

    val iWasRightDialog = PopUpState(
        dialogText = "Are you sure you got this question right?",
        onConfirmAction =
        {
            val questionId = state.questionGlobalId!!
            onPopUpAction(PopUpAction.CloseAlertDialog)
            trackingOnAction(TrackingAction.AddOneCorrect(questionId))
            trackingOnAction(TrackingAction.SubtractOneWrong(questionId))
            trackingOnAction(TrackingAction.UpdateLastCorrectTime(questionId))
            trackingOnAction(TrackingAction.UpdateCorrectStreak(questionId))
            onAction(QuizAction.NextQuestion)
        }
    )

    val markForReviewDialog = PopUpState(
        dialogText = "Are you sure you want to mark this question for review?",
        onConfirmAction =
        {
            onPopUpAction(PopUpAction.CloseAlertDialog)
            trackingOnAction(TrackingAction.MarkForReview(state.questionGlobalId!!))
        }
    )

    QuizAlertDialog(popUpState = popUpState, onAction = onPopUpAction)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        QuestionScreen(state = state, onAction = onAction)



        if (!state.isAnswerConfirmed) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Button(
                        modifier = Modifier.fillMaxWidth(.20f),
                        onClick = {
                            if (state.inputAnswer != null) {
                                onAction(QuizAction.ConfirmAnswer(trackingOnAction))
                                kanjiRecognizerOnAction(KanjiRecAction.ResetPredictedKanji)
                                drawingAction(DrawingAction.ClearAllPaths)
                            }

                        }
                    ) {
                        Text("CONF.")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(.20f),
                        onClick = {
                            onPopUpAction(PopUpAction.ShowAlertDialog(omitQuestionDialog))
                        }
                    ) {
                        Text("STOP")
                    }

                }


                Column {


                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.25F)
                            .aspectRatio(1f)
                            .border(BorderStroke(1.dp, Color.Blue))
                            .background(Color.White)
                            .clickable {
                                kanjiRecognizerOnAction(KanjiRecAction.SetOtherGuessesList)
                                onPopUpAction(PopUpAction.ShowOtherGuesses)
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        if (predictedKanji != null) Text(text = predictedKanji)

                        if (otherGuessesList != null) {
                            MySpinner(
                                isExpanded = popUpState.isShowOtherGuesses,
                                onPopUpAction = onPopUpAction,
                                items = otherGuessesList,
                                onKanjiRecAction = kanjiRecognizerOnAction
                            )
                        }

                    }

                    Text(text = state.inputAnswer ?: "")

                }

                Column {


                    Button(
                        modifier = Modifier.fillMaxWidth(.30f),
                        onClick = {
                            if (predictedKanji != null) {
                                val newAnswer =
                                    if (state.inputAnswer == null) {
                                        predictedKanji
                                    } else {
                                        state.inputAnswer + predictedKanji
                                    }
                                onAction(QuizAction.InputAnswer(newAnswer))
                                drawingAction(DrawingAction.ClearAllPaths)
                                kanjiRecognizerOnAction(KanjiRecAction.ResetPredictedKanji)
                            }

                        }
                    ) {
                        Text("OK")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(.30f),
                        onClick = {
                            if (state.inputAnswer != null) {
                                var newAnswer = state.inputAnswer
                                newAnswer = newAnswer.dropLast(1)

                                if(newAnswer.isEmpty()) newAnswer = null

                                onAction(QuizAction.InputAnswer(newAnswer))
                            }
                        }
                    ) {
                        Text("DEL")
                    }
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
                            onPopUpAction(PopUpAction.ShowAlertDialog(iWasRightDialog))
                        }
                    ) {
                        Text("I WAS\nRIGHT!")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(.5f),
                        onClick = {
                            onPopUpAction(PopUpAction.ShowAlertDialog(markForReviewDialog))
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




