package com.talisol.nihongodrill.ui.screens.questionScreens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.nihongodrill.actions.PopupAction
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.actions.TrackingAction
import com.talisol.nihongodrill.quizUtils.processTarget
import com.talisol.nihongodrill.ui.screens.MCAScreen
import com.talisol.nihongodrill.ui.states.PopupState
import com.talisol.nihongodrill.ui.states.QuizState

@Composable
fun JLPTScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    onTrackingAction: (TrackingAction) -> Unit,
    onPopupAction: (PopupAction) -> Unit
) {

    val explanationPopup = PopupState(
        title = "Explanation:",
        dialogText = quizState.explanation,
        onConfirmAction = {
            onPopupAction(PopupAction.CloseAlertDialog)
        },
        dismissButtonText = "OK",
        confirmButtonText = "OK"
    )

    val listText = quizState.mcaList

    val listKata = if (listText != null) {
        (1..listText.size).toList().map { it.toString() }
    } else {
        listOf()
    }

    val annotatedString = processTarget(quizState.question, quizState.target!!)

    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .fillMaxHeight(.75F)
    ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = annotatedString,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            if (quizState.isAnswerConfirmed) {
                if (quizState.questionTranslation != null) {
                    Box(Modifier.padding(16.dp)) {
                        Text(text = quizState.questionTranslation)
                    }
                }

                if (quizState.explanation != null) {
                    Button(onClick = {onPopupAction(PopupAction.ShowAlertDialog(explanationPopup)) }) {
                     Text(text = "see explanation")
                    }
                }

            }
        }



            MCAScreen(
                quizState,
                listKata,
                listText!!,
                onQuizAction,
                onTrackingAction,
            )


    }

}