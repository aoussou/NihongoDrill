package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
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
            cells = GridCells.Adaptive(32.dp),
            content = {
                items(charList.size) {
                    index ->

                    val textStyle = if (index == state.selectedWrongKanjInd) {
                        TextStyle(
                            color = Color.Blue,
                            fontSize = 26.sp,
                        )
                    } else {
                        TextStyle()
                    }

                    ClickableText(
                        text = AnnotatedString(state.question[index].toString()),
                        onClick = {
                            onQuizAction(QuizAction.SelectWrongKanji(
                                state.question[index].toString(),index)
                            )
                        },
                        style = textStyle
                    )
                }

            }
        )

        Row(modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically) {
            for (i in state.question.indices) {


            }
        }

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

