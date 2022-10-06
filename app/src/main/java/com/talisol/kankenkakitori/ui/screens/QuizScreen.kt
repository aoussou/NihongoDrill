package com.talisol.kankenkakitori.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.talisol.kankenkakitori.actions.*
import com.talisol.kankenkakitori.drawingUtils.DrawingState
import com.talisol.kankenkakitori.ui.MySpinner
import com.talisol.kankenkakitori.ui.states.PopupState
import com.talisol.kankenkakitori.ui.states.QuizState

@Composable
fun QuizScreen(
    quizState: QuizState,
    popupState: PopupState,
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


    QuizAlertDialog(popupState = popupState, onAction = onPopupAction)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {

        val localContext = LocalContext.current


        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            QuestionScreen(
                quizState = quizState,
                onQuizAction = onQuizAction,
                onTrackingAction = onTrackingAction,
            )
        }



        if (!quizState.isAnswerConfirmed) {

            Text(text = quizState.inputAnswer ?: "", fontSize = 12.sp)

            Column(modifier = Modifier.align(Alignment.Center)) {

                if (
                    quizState.questionType == "kaki"
                    || quizState.questionType == "goji"
                    || quizState.questionType == "taigi"
                ) {

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth()
                            .border(BorderStroke(5.dp, Color.Black))
//                            .clickable {
//                                if (quizState.questionType == "goji" && quizState.selectedWrongKanji == null) {
//                                    Toast
//                                        .makeText(
//                                            localContext,
//                                            "Choose a kanji first.",
//                                            Toast.LENGTH_SHORT
//                                        )
//                                        .show()
//                                }
//                            }
                    ) {

//                        KanjiDrawingWidget(
//                            drawingState,
//                            popupState,
//                            currentPath,
//                            predictedKanji,
//                            otherGuessesList,
//                            onDrawingAction,
//                            onKanjiRecAction,
//                            onPopupAction,
//                        )

                    }

//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth(.25F)
//                                .aspectRatio(1f)
//                                .padding(12.dp)
//                                .border(
//                                    if (predictedKanji != null) {
//                                        BorderStroke(1.dp, Color.Black)
//                                    } else BorderStroke(0.dp, Color.White)
//                                )
//                                .background(Color.White)
//                                .zIndex(if (predictedKanji != null) 1f else 0f)
//                                .clickable {
//                                    onKanjiRecAction(KanjiRecAction.SetOtherGuessesList)
//                                    onPopupAction(PopupAction.ShowOtherGuesses)
//                                },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            if (predictedKanji != null) Text(text = predictedKanji, fontSize = 36.sp)
//                            if (otherGuessesList != null) {
//                                MySpinner(
//                                    isExpanded = popupState.isShowOtherGuesses,
//                                    onPopupAction = onPopupAction,
//                                    items = otherGuessesList,
//                                    onKanjiRecAction = onKanjiRecAction
//                                )
//                            }
//                        }
//
//                        if (
//                            quizState.questionType == "kaki"
//                            || quizState.questionType == "taigi"
//                            || quizState.selectedWrongKanji != null
//                        ) {
//                            DrawingScreen(
//                                currentPath,
//                                drawingState,
//                                onDrawingAction,
//                            )
//                        }
//
//
//                        DrawingPropertiesMenu(
//                            drawingState = drawingState,
//                            onDrawingAction = onDrawingAction,
//                            onKanjiRecAction = onKanjiRecAction
//                        )
//
//                    }

                } else if (quizState.questionType == "yomi") {
                    val textState = remember { mutableStateOf(TextFieldValue()) }
                    TextField(
                        value = textState.value,
                        onValueChange = {
                            textState.value = it;
                            onQuizAction(
                                QuizAction.InputAnswer(
                                    textState.value.text
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            onQuizAction(QuizAction.ConfirmAnswer(onTrackingAction))
                        }
                        )
                    )
                }

            }

        } else {

            if (
                quizState.questionType == "kaki"
                || quizState.questionType == "yomi"
                || quizState.questionType == "kousei"
            ) {

                if (!quizState.isAnswerCorrect!!) {

                    WrongAnswer(
                        quizState,
                        onPopupAction,
                        onTrackingAction,
                        onQuizAction
                    )

                } else {
                    Text(text = quizState.correctAnswer!!)
                }

            }



            if (quizState.isLastQuestion) {
                QuizOver(onQuizAction)
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


//        Box(modifier = Modifier
//            .align(Alignment.BottomCenter)
//            .fillMaxHeight(.2F)
//        ) {
//            QuizOperationMenu(
//                quizState,
//                predictedKanji,
//                onQuizAction,
//                onPopupAction,
//                onTrackingAction,
//                onKanjiRecAction,
//                onDrawingAction
//            )
//        }


    }


}




