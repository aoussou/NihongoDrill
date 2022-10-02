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
import androidx.compose.ui.zIndex
import com.talisol.kankenkakitori.actions.*
import com.talisol.kankenkakitori.drawingUtils.DrawingState
import com.talisol.kankenkakitori.ui.MySpinner
import com.talisol.kankenkakitori.ui.states.PopupState
import com.talisol.kankenkakitori.ui.states.QuizState
import com.talisol.kankenkakitori.ui.theme.DarkGreen

@Composable
fun KankenQuizScreen(
    quizState: QuizState,
    popUpState: PopupState,
    onQuizAction: (QuizAction) -> Unit,
    currentPath: Path,
    drawingState: DrawingState,
    onDrawingAction: (DrawingAction) -> Unit,
    onKanjiRecAction: (KanjiRecAction) -> Unit,
    predictedKanji: String?,
    otherGuessesList: List<String>?,
    onTrackingAction: (TrackingAction) -> Unit,
    onPopupAction: (PopupAction) -> Unit
) {


    val iWasRightDialog = PopupState(
        dialogText = "Are you sure you got this question right?",
        onConfirmAction =
        {
            val questionId = quizState.questionGlobalId!!
            onPopupAction(PopupAction.CloseAlertDialog)
            onTrackingAction(TrackingAction.AddOneCorrect(questionId))
            onTrackingAction(TrackingAction.SubtractOneWrong(questionId))
            onTrackingAction(TrackingAction.UpdateLastCorrectTime(questionId))
            onTrackingAction(TrackingAction.UpdateCorrectStreak(questionId))
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

    QuizAlertDialog(popUpState = popUpState, onAction = onPopupAction)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        QuestionScreen(state = quizState, onAction = onQuizAction)



        if (!quizState.isAnswerConfirmed) {

            Column {

                Text(text = quizState.inputAnswer ?: "", fontSize = 12.sp)

            }

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .border(BorderStroke(5.dp, Color.Black))
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(.25F)
                        .aspectRatio(1f)
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .border(
                            if (predictedKanji != null) {
                                BorderStroke(1.dp, Color.Black)
                            } else BorderStroke(0.dp, Color.White)
                        )
                        .background(Color.White)
                        .zIndex(if (predictedKanji != null) 1f else 0f)
                        .clickable {
                            onKanjiRecAction(KanjiRecAction.SetOtherGuessesList)
                            onPopupAction(PopupAction.ShowOtherGuesses)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (predictedKanji != null) Text(text = predictedKanji, fontSize = 36.sp)
                    if (otherGuessesList != null) {
                        MySpinner(
                            isExpanded = popUpState.isShowOtherGuesses,
                            onPopUpAction = onPopupAction,
                            items = otherGuessesList,
                            onKanjiRecAction = onKanjiRecAction
                        )
                    }
                }


                DrawingScreen(
                    currentPath,
                    drawingState,
                    onDrawingAction,
                )
            }

            DrawingPropertiesMenu(
                drawingState = drawingState,
                onDrawingAction = onDrawingAction,
                onKanjiRecAction = onKanjiRecAction
            )


        } else {
            if (!quizState.isAnswerCorrect!!) {

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


            } else {
                Text(text = quizState.correctAnswer!!)
            }


            if (quizState.isLastQuestion) {
                Button(
                    modifier = Modifier.fillMaxWidth(.5f),
                    onClick = {
                        onQuizAction(QuizAction.EndQuiz)
                    }
                ) {
                    Text("END")
                }
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth(.5f),
                    onClick = {
                        onQuizAction(QuizAction.NextQuestion)
                    }
                ) {
                    Text("NEXT")
                }
            }


        }



        QuizOperationMenu(
            quizState,
            predictedKanji,
            onQuizAction,
            onPopupAction,
            onTrackingAction,
            onKanjiRecAction,
            onDrawingAction
        )

    }



}




