package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.quizUtils.makeTargetRed
import com.talisol.kankenkakitori.ui.states.QuizState

@Composable
fun KakiScreen(
    quizState: QuizState,
) {
        val annotatedString = makeTargetRed(quizState.question, quizState.target!!)

//    Text(
//        text = annotatedString,
//        fontSize = 16.sp,
//        fontWeight = FontWeight.Bold
//    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(BorderStroke(5.dp, Color.Green))
        ,
        contentAlignment = Alignment.Center,
    ) {


        Text(
            modifier = Modifier.align(Center),
            text = annotatedString,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

    }

}
