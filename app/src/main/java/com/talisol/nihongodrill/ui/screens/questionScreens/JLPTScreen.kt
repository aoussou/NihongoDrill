package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.actions.TrackingAction
import com.talisol.nihongodrill.quizUtils.processTarget
import com.talisol.nihongodrill.ui.states.QuizState

@Composable
fun JLPTScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    onTrackingAction: (TrackingAction) -> Unit,
) {


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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = annotatedString,
                fontSize = 36.sp,
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