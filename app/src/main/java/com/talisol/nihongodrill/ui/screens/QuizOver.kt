package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.talisol.nihongodrill.actions.QuizAction

@Composable
fun QuizOver(
    onQuizAction: (QuizAction) -> Unit
) {

    Button(
        modifier = Modifier.fillMaxWidth(.5f),
        onClick = {
            onQuizAction(QuizAction.EndQuiz)
        }
    ) {
        Text("END")
    }

}