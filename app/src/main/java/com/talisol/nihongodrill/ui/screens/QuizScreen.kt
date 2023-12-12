package com.talisol.nihongodrill.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
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
import com.talisol.nihongodrill.actions.*
import com.talisol.nihongodrill.drawingUtils.DrawingState
import com.talisol.nihongodrill.ui.states.PopupState
import com.talisol.nihongodrill.ui.states.QuizState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun QuizScreen(
    quizState: QuizState,
    popupState: PopupState,
    onQuizAction: (QuizAction) -> Unit,
    currentPath: StateFlow<Path>,
    drawingState: StateFlow<DrawingState>,
    onDrawingAction: (DrawingAction) -> Unit,
    onKanjiRecAction: (KanjiRecAction) -> Unit,
    predictedKanji: String?,
    otherGuessesList: List<String>?,
    onTrackingAction: (TrackingAction) -> Unit,
    onPopupAction: (PopupAction) -> Unit,
    getExplanation: (String) -> String
) {

    QuizAlertDialog(popupState = popupState, onAction = onPopupAction)


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            QuestionScreen(
                quizState = quizState,
                onQuizAction = onQuizAction,
                onTrackingAction = onTrackingAction,
                onPopupAction = onPopupAction,
                getExplanation = getExplanation
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {



            if (!quizState.isAnswerConfirmed) {
                if (quizState.isKanjiRecRequired) {
                    val gojiCondition =
                        quizState.questionType == "goji" && quizState.selectedWrongKanji == null
                    val taigiCondition =
                        quizState.questionType == "taigi" && quizState.selectedSubQuestionNbr == null

                    val drawingCondition = !gojiCondition && !taigiCondition
                    val localContext = LocalContext.current

//                    KanjiDrawingWidget(
//                        quizState,
//                        drawingState,
//                        popupState,
//                        currentPath,
//                        predictedKanji,
//                        otherGuessesList,
//                        onDrawingAction,
//                        onKanjiRecAction,
//                        onPopupAction,
//                    )


                    val kanModifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(5.dp, Color.Black))
                        .clickable {
                            if (gojiCondition) {
                                Toast
                                    .makeText(
                                        localContext,
                                        "Choose a kanji first.",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }

                            if (taigiCondition) {
                                Toast
                                    .makeText(
                                        localContext,
                                        "Choose a box first.",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(5.dp, Color.Black))
                            .clickable {
                                if (gojiCondition) {
                                    Toast
                                        .makeText(
                                            localContext,
                                            "Choose a kanji first.",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                                if (taigiCondition) {
                                    Toast
                                        .makeText(
                                            localContext,
                                            "Choose a box first.",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                    ) {

                        KanjiDrawingWidget(
                            drawingState,
                            popupState,
                            currentPath,
                            predictedKanji,
                            otherGuessesList,
                            onDrawingAction,
                            onKanjiRecAction,
                            onPopupAction,
                            kanModifier,
                            drawingCondition
                        )

                    }

                }

                if (
                    quizState.questionFormat == "type"
                ) {
                    val textState = remember { mutableStateOf(TextFieldValue()) }
                    TextField(
                        value = textState.value,
                        onValueChange = {
                            textState.value = it
                            onQuizAction(
                                QuizAction.InputAnswer(
                                    textState.value.text
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            onQuizAction(QuizAction.ConfirmAnswer(onTrackingAction))
                        }
                        )
                    )
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
            } else {

                CheckScreen(
                    quizState,
                    onPopupAction,
                    onTrackingAction,
                    onQuizAction
                )


            }


        }

    }


}




