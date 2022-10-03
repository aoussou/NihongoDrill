package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.ui.states.QuizState
import com.talisol.kankenkakitori.quizUtils.makeTargetRed

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionScreen(
    state: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    if (state.questionType == "goji") {

        val charList = state.question.toList()

        LazyVerticalGrid(
            cells = GridCells.Adaptive(28.dp),
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
                                        state.question[index].toString(), index
                                    )
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (index == state.selectedWrongKanjInd) {
                            Text(
                                state.question[index].toString(),
                                color = Color.Blue,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {

                                Text(state.question[index].toString())


                        }

                    }

                }


            }
        )

    } else {

        val annotatedString = makeTargetRed(state.question, state.target)

        Text(
            modifier = modifier,
            text = annotatedString,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

    }


}
