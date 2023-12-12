package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.ui.states.QuizState

@Composable
fun SelectionBox(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit,
    suggestionStrings: List<String>,
    listKata: List<String>,
    selectedAnswerIndex: Int,
    modifier: Modifier = Modifier
) {

    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth(.00005F)
            .aspectRatio(1f)
            .padding(1.dp)
            .border(BorderStroke(1.dp, Color.Black))
            .background(Color.White)
            .clickable { isExpanded = true }
//            .then(modifier)
        ,
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
//                    DropdownMenuItem(onClick = {
//                        onQuizAction(QuizAction.UpdateAnswersList(element, selectedAnswerIndex))
//                        isExpanded = false
//                    }) {
//                        Text(text = element)
//                    }


                }
            }
        }


    }

}
