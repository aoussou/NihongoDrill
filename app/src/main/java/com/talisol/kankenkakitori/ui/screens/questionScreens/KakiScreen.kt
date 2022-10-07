package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.quizUtils.makeTargetRed
import com.talisol.kankenkakitori.ui.states.QuizState

@Composable
fun KakiScreen(
    quizState: QuizState,
) {
    val annotatedString = makeTargetRed(quizState.question, quizState.target!!)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(BorderStroke(5.dp, Color.Green)),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {


            Text(
                text = annotatedString,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )


        Text(text = quizState.inputAnswer ?: "", fontSize = 24.sp)


        if (quizState.isAnswerConfirmed) {

            if (quizState.isAnswerCorrect != null) {

                if (!quizState.isAnswerCorrect) {
                    CorrectVsWrongAnswerScreen(quizState)
                } else (
                        Text(
                            text = "CORRECT",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        )
            }

            }


        }



}
