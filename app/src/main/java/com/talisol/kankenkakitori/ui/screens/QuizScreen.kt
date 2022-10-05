package com.talisol.kankenkakitori.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        val localContext = LocalContext.current

        QuestionScreen(quizState = quizState, onQuizAction = onQuizAction)

        if (!quizState.isAnswerConfirmed) {

            Text(text = quizState.inputAnswer ?: "", fontSize = 12.sp)


            if (quizState.questionType == "kaki" || quizState.questionType == "goji"){
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .border(BorderStroke(5.dp, Color.Black))
                    .clickable {
                        if (quizState.questionType == "goji" && quizState.selectedWrongKanji == null) {
                            Toast
                                .makeText(
                                    localContext,
                                    "Choose a kanji first.",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
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
                            isExpanded = popupState.isShowOtherGuesses,
                            onPopupAction = onPopupAction,
                            items = otherGuessesList,
                            onKanjiRecAction = onKanjiRecAction
                        )
                    }
                }

                if (quizState.questionType == "goji" || quizState.selectedWrongKanji != null) {

                    DrawingScreen(
                        currentPath,
                        drawingState,
                        onDrawingAction,
                    )

                }


            }

            DrawingPropertiesMenu(
                drawingState = drawingState,
                onDrawingAction = onDrawingAction,
                onKanjiRecAction = onKanjiRecAction
            )

        }

        } else {

            if (
                quizState.questionType == "kaki"
                || quizState.questionType == "yomi"
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




