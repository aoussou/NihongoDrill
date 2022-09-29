package com.talisol.kankenkakitori.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kanjirecognizercompose.drawingUtils.DrawingAction
import com.talisol.kanjirecognizercompose.screens.KanjiRecognitionScreen
import com.talisol.kankenkakitori.drawingUtils.DrawingState
import com.talisol.kankenkakitori.ui.states.DialogState
import com.talisol.kankenkakitori.quizUtils.QuizAction
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
    kanjiRecognizer: (Bitmap) -> String,
) {

    val omitQuestionDialog = DialogState(
        dialogText = "Are you sure you want the quiz to omit this question?",
        onConfirmAction = {
            onAction(QuizAction.StopAsking)
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
            onAction(QuizAction.AddOneCorrect)
            onAction(QuizAction.SubtractOneWrong)
            onAction(QuizAction.NextQuestion)
        }
    )

    val markForReviewDialog = DialogState(
        dialogText = "Are you sure you want to mark this question for review?",
        onConfirmAction =
        {
            onAction(QuizAction.CloseAlertDialog)
            onAction(QuizAction.MarkForReview)
        }
    )

    QuizAlertDialog(dialogState = dialogState, onAction = onAction)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        Button(
            modifier = Modifier.fillMaxWidth(.5f),
            onClick = {
                onAction(QuizAction.ShowAlertDialog(omitQuestionDialog));
            }
        ) {
            Text("STOP\nASKING")
        }

        QuestionScreen(state = state, onAction = onAction)


        if (!state.isAnswerConfirmed) {


            KanjiRecognitionScreen(
                currentPath,
                drawingState,
                drawingAction,
                kanjiRecognizer
            )

//            // this need to be place inside the if statement otherwise the value get carried
//            var textState = remember { mutableStateOf(TextFieldValue()) }
//
//            TextField(
//                value = textState.value,
//                onValueChange = {
//                    textState.value = it;
//                    onAction(
//                        QuizAction.InputAnswer(
//                            textState.value.text
//                        )
//                    )
//                },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(onDone = {
//                    onAction(QuizAction.ConfirmAnswer)
//                }
//                )
//            )


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
                            onAction(QuizAction.ShowAlertDialog(iWasRightDialog));
                        }
                    ) {
                        Text("I WAS\nRIGHT!")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(.5f),
                        onClick = {
                            onAction(QuizAction.ShowAlertDialog(markForReviewDialog));
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
                        onAction(QuizAction.NextQuestion);
                    }
                ) {
                    Text("NEXT")
                }
            }


        }
    }

}

//        if (!(quizState.isLastQuestion && quizState.isAnswerConfirmed)) {
//
//            Row(
//                modifier = Modifier.fillMaxWidth(1.0f),
//                horizontalArrangement = Arrangement.SpaceBetween,
//            ) {
//
//
//                Box(
//                    modifier = Modifier.weight(.25f),
//                    contentAlignment = Alignment.CenterStart
//                ) {
//                    if (!quizState.isFirstQuestion) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_left_24),
//                            contentDescription = "Back",
//                            modifier = Modifier
//                                .clickable {
//                                    onAction(QuizAction.PreviousQuestion)
//                                }
//                                .size(64.dp)
//                        )
//                    }
//
//                }
//
//
//                Box(
//                    modifier = Modifier.weight(.45f),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Button(
//                        onClick = { onAction(QuizAction.ConfirmAnswer) }) {
//                        Text(text = "OK")
//                    }
//                }
//
//
//
//                Box(
//                    modifier = Modifier.weight(.25f),
//                    contentAlignment = Alignment.CenterEnd
//                ) {
//                    if (!quizState.isLastQuestion) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
//                            contentDescription = "Forwadr",
//                            modifier = Modifier
//                                .clickable {
//                                    onAction(QuizAction.NextQuestion)
//                                }
//                                .size(64.dp)
//                        )
//                    }
//                }
//            }
//        }



