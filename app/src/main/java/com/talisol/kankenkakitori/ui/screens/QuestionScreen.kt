package com.talisol.kankenkakitori.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.quizUtils.QuizAction
import com.talisol.kankenkakitori.ui.states.QuizState
import com.talisol.kankenkakitori.quizUtils.makeTargetRed

@Composable
fun QuestionScreen(
    state: QuizState,
    modifier: Modifier = Modifier,
    onAction: (QuizAction) -> Unit
) {
        val annotatedString = makeTargetRed(state.question,state.target)

        Text(
            modifier = modifier,
            text = annotatedString,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold
        )
}

