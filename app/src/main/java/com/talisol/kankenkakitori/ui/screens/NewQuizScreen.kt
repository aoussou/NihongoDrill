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
fun NewQuizScreen(
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
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {


            if (!quizState.isAnswerConfirmed) {
                if (
                    quizState.questionType == "kaki"
                ) {

                    KanjiDrawingWidget(
                        quizState,
                        drawingState,
                        popupState,
                        currentPath,
                        predictedKanji,
                        otherGuessesList,
                        onDrawingAction,
                        onKanjiRecAction,
                        onPopupAction,
                    )
                }

                if (quizState.questionType == "yomi") {
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




