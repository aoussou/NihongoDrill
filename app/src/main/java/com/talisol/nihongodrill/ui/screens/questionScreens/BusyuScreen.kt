package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.actions.TrackingAction
import com.talisol.nihongodrill.ui.states.QuizState

@Composable
fun BusyuScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    onTrackingAction: (TrackingAction) -> Unit,
) {

    val listKata = listOf("ア", "イ", "ウ", "エ", "オ")
    val listText = quizState.mcaList
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = quizState.question,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
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