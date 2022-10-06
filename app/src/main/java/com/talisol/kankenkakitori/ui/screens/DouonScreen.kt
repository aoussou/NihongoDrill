package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.quizUtils.extractStringFromJson
import com.talisol.kankenkakitori.quizUtils.makeTargetRed
import com.talisol.kankenkakitori.ui.states.QuizState


@Composable
fun DouonScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit
) {

    val mcaStrings = quizState.mcaList
    val questionsStrings = extractStringFromJson(quizState.question)
    val listKata = listOf("ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ")

Column(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.5F)
    ,
    verticalArrangement = Arrangement.SpaceBetween
) {


    for (q in questionsStrings.indices) {
        val annotatedString = makeTargetRed(questionsStrings[q], quizState.target!!)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                annotatedString,
                fontSize = 16.sp,
            )

            SelectionBox(
                quizState,
                onQuizAction,
                mcaStrings!!,
                listKata,
                q
            )

            if (quizState.correctAnswersList != null) {

                val correctAnswer = quizState.correctAnswersList[q]
                if (
                    quizState.isAnswerConfirmed
                    && (quizState.selectedAnswersList!![q] != correctAnswer)
                ) {
                    Text(
                        text = correctAnswer,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }

            }

        }


    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (s in mcaStrings!!.indices) {
            Text(
                "${listKata[s]} : ${mcaStrings[s]}",
                fontSize = 24.sp,
            )

        }
    }

}






}