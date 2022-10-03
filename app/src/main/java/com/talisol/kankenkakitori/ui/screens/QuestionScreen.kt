package com.talisol.kankenkakitori.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.ui.states.QuizState
import com.talisol.kankenkakitori.quizUtils.makeTargetRed

@Composable
fun QuestionScreen(
    state: QuizState,
    modifier: Modifier = Modifier,
) {
    if (state.quizType == "goji") {

        var selectedCharacterInd by remember {
            mutableStateOf(-1)
        }

        val text = "問題集は漢字検定の模擬問題を出題しているサイトです。"

        Row(verticalAlignment = Alignment.CenterVertically) {
            for (i in text.indices) {

                val textStyle = if (i == selectedCharacterInd) {
                    TextStyle(
                        color = Color.Blue,
                        fontSize = 26.sp,
                    )
                } else {
                    TextStyle()
                }

                ClickableText(
                    text = AnnotatedString(text[i].toString()),
                    onClick = { selectedCharacterInd = i },
                    style = textStyle
                )
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

