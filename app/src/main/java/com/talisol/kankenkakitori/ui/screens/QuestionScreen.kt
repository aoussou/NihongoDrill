package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.ui.states.QuizState
import com.talisol.kankenkakitori.quizUtils.makeTargetRed


@Composable
fun QuestionScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    when (quizState.questionType){
        "yomi" -> KakiScreen(quizState)
        "kaki" -> KakiScreen(quizState)
        "goji" -> GojiScreen(quizState,onQuizAction)
        "shikibetsu" -> ShikibetsuScreen(quizState,onQuizAction)
        "douon" -> DouonScreen(quizState,onQuizAction)
    }



}
