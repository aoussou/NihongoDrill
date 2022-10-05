package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.ui.states.QuizState

@Composable
fun SelectionBox(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    suggestionStrings: List<String>,
    listKata: List<String>,
    selectedAnswerIndex: Int,
) {

    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(.25F)
            .aspectRatio(1f)
//            .padding(12.dp)
            .border(BorderStroke(1.dp, Color.Black))
            .background(Color.White)
            .clickable { isExpanded = true },
        contentAlignment = Alignment.Center
    ) {

        if (quizState.selectedAnswersList != null) {
            if (quizState.selectedAnswersList[selectedAnswerIndex] != null) {
                Text(text = quizState.selectedAnswersList[selectedAnswerIndex]!!)
            }
        }


        if (isExpanded) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {


                for (i in suggestionStrings.indices){
                    val element = listKata[i]
                    DropdownMenuItem(onClick = {
                        onQuizAction(QuizAction.UpdateAnswersList(element, selectedAnswerIndex))
                        isExpanded = false
                    }) {
                        Text(text = element)
                    }


                }
            }
        }


    }

}
