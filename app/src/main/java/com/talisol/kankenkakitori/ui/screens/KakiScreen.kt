package com.talisol.kankenkakitori.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.quizUtils.makeTargetRed
import com.talisol.kankenkakitori.ui.states.QuizState
import java.lang.reflect.Modifier

@Composable
fun KakiScreen(
    quizState: QuizState,
) {
            val annotatedString = makeTargetRed(quizState.question, quizState.target)
        Text(
            text = annotatedString,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
}