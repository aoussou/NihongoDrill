package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction

@Composable
fun TaigiRow(
    questionLists: List<String>,
    questionsRange: List<Int>,
    correctAnswersList: List<String>,
    targetsList: List<String>,
    onQuizAction: (QuizAction) -> Unit
) {

    Row {

        val modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
//            .border(BorderStroke(1.dp, Color.Black))

        val rowModifier = Modifier
            .padding(4.dp)

        for (ql_ind in questionsRange) {

            val question = questionLists[ql_ind]
            val correctAnswer = correctAnswersList[ql_ind]
            val isFirst = targetsList[ql_ind].indexOf(correctAnswer)
            val target = targetsList[ql_ind].replace(correctAnswer, "")

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {


                Row(
                    modifier = rowModifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TaigiBox(
                        modifier = modifier,
                        text = question[0].toString()
                    )
                    TaigiBox(
                        modifier = modifier,
                        text = question[1].toString()
                    )
                }

                Row(
                    modifier = rowModifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isFirst == 1) {
                        TaigiBox(
                            modifier = modifier,
                            text = target
                        )
                    }

                    Box(
                        modifier =
                        modifier
                            .border(BorderStroke(1.dp, Color.Black))
                            .clickable {
                                       onQuizAction(QuizAction.SetSelectedSubQuestion(ql_ind))
                            }
                        ,
                        contentAlignment = Alignment.Center
                    ) {

                    }

                    if (isFirst == 0) {
                        TaigiBox(
                            modifier = modifier,
                            text = target
                        )
                    }

                }
            }
        }
    }

}
