package com.talisol.nihongodrill.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.talisol.nihongodrill.ui.states.QuizSelectionState

@Composable
fun LevelSectionScreen(
    quizSelectionState: QuizSelectionState
) {

    if (quizSelectionState.selectedCategory!= null)
    Text(text = quizSelectionState.selectedCategory)


}