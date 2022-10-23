package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.nihongodrill.ui.states.QuizSelectionState

@Composable
fun LevelSectionScreen(
    quizSelectionState: QuizSelectionState
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (quizSelectionState.selectedCategory != null)
            Text(
                text = quizSelectionState.selectedCategory,
                fontSize = 32.sp
            )


        Row(
            modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column() {
                if (quizSelectionState.levelList != null) {
                    for (level in quizSelectionState.levelList) {
                        Text(text = level, fontSize = 32.sp)
                    }
                }
            }

            Column() {
                if (quizSelectionState.questionTypeList != null) {
                    for (type in quizSelectionState.questionTypeList) {
                        Text(text = type, fontSize = 32.sp)
                    }
                }
            }

        }


    }


}