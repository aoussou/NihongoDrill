package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.quizUtils.extractStringFromJson
import com.talisol.nihongodrill.ui.states.QuizState

@Composable
fun TaigiScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit
) {

    val suggestionStrings = quizState.mcaList!!

    val questionLists = extractStringFromJson(quizState.question)
    val correctAnswersList = quizState.correctAnswersList
    val targetsList = extractStringFromJson(quizState.target!!)


    LazyVerticalGrid(
        columns = GridCells.Adaptive(38.dp),
        verticalArrangement = Arrangement.Center,
        content = {
            items(suggestionStrings.size) { index ->
                Box(
                    modifier = Modifier
                        .border(BorderStroke(1.dp, Color.Black))
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            suggestionStrings[index],
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    )


    if (!quizState.isAnswerConfirmed) {
        var isShowingTaigi by remember { mutableStateOf(true) }


        if (isShowingTaigi) {

            TaigiRow(
                quizState,
                questionLists,
                (0..4).toList(),
                correctAnswersList!!,
                targetsList,
                onQuizAction,
            )

            Button(onClick = { isShowingTaigi = false }) {
                Text(text = "Ruigi")
            }

        } else {
            TaigiRow(
                quizState,
                questionLists,
                (5..9).toList(),
                correctAnswersList!!,
                targetsList,
                onQuizAction,
            )

            Button(onClick = { isShowingTaigi = true }) {
                Text(text = "Taigi")
            }
        }
    } else {

        TaigiRow(
            quizState,
            questionLists,
            (0..4).toList(),
            correctAnswersList!!,
            targetsList,
            onQuizAction,
        )

        TaigiRow(
            quizState,
            questionLists,
            (5..9).toList(),
            correctAnswersList!!,
            targetsList,
            onQuizAction,
        )



    }





}