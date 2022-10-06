package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.ui.states.QuizState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GojiScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit
) {
    val charList = quizState.question.toList()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(28.dp),
        verticalArrangement = Arrangement.Center,
        content = {
            items(charList.size) { index ->
                Box(
                    modifier = Modifier
//                            .border(BorderStroke(1.dp,Color.Black))
                        .aspectRatio(1f)
                        .clickable {
                            onQuizAction(
                                QuizAction.SelectWrongKanji(
                                    quizState.question[index].toString(), index
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (index == quizState.selectedWrongKanjiInd) {
                        Text(
                            quizState.question[index].toString(),
                            color = Color.Blue,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(quizState.question[index].toString())
                    }
                }
            }
        }
    )
}