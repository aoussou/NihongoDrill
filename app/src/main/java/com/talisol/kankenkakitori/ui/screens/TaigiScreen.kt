package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.quizUtils.extractStringFromJson
import com.talisol.kankenkakitori.ui.states.QuizState

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


    TaigiRow(
        questionLists.slice(0..4),
        correctAnswersList!!.slice(0..4),
        targetsList.slice(0..4)
    )

    TaigiRow(
        questionLists.slice(5..9),
        correctAnswersList!!.slice(5..9),
        targetsList.slice(5..9)
    )


}