package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.quizUtils.extractStringFromJson
import com.talisol.nihongodrill.quizUtils.processTarget
import com.talisol.nihongodrill.ui.states.QuizState


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
           ,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        val selectionBoxModifier = Modifier.weight(1f)

        for (q in questionsStrings.indices) {
            val annotatedString = processTarget(questionsStrings[q], quizState.target)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
                    .weight(1f)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    annotatedString,
                    fontSize = 16.sp,
//                    modifier = Modifier.weight(1f)
                )

                SelectionBox(
                    quizState,
                    onQuizAction,
                    mcaStrings!!,
                    listKata,
                    q,
                    selectionBoxModifier
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "${listKata[s]} :",
                        fontSize = 24.sp,
                    )
                    Text(
                        "${mcaStrings[s]}",
                        fontSize = 24.sp,
                    )

                }


            }
        }

    }


}