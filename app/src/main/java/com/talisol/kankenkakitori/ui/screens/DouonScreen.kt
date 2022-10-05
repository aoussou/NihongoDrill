package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.quizUtils.extractStringFromJson
import com.talisol.kankenkakitori.ui.states.QuizState
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DouonScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit
) {

    val suggestionStrings = extractStringFromJson(quizState.target)
    val questionsStrings = extractStringFromJson(quizState.question)
    val listKata = listOf("ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ")

Column(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.75F)
    ,
    verticalArrangement = Arrangement.SpaceBetween
) {


    for (q in questionsStrings.indices) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                questionsStrings[q],
                fontSize = 16.sp,
            )

            SelectionBox(
                quizState,
                onQuizAction,
                suggestionStrings,
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
        for (s in suggestionStrings.indices) {
            Text(
                "${listKata[s]} : ${suggestionStrings[s]}",
                fontSize = 24.sp,
            )

        }
    }

}






}