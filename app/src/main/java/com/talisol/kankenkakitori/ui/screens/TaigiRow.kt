package com.talisol.kankenkakitori.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaigiRow(
    questionLists: List<String>,
    correctAnswersList: List<String>,
    targetsList: List<String>,
) {


    Row(
        modifier = Modifier
    ) {

        val modifier = Modifier
            .border(BorderStroke(1.dp, Color.Black))
            .aspectRatio(1f)
            .weight(1f)

        for (ql_ind in 0..4) {
            val question = questionLists[ql_ind]
            val correctAnswer = correctAnswersList[ql_ind]
            val isFirst = targetsList[ql_ind].indexOf(correctAnswer)
            val target = targetsList[ql_ind].replace(correctAnswer, "")

            Column(modifier = Modifier
                .weight(1F)
                .padding(2.dp)
            ) {

                Row(modifier = Modifier.weight(1F)) {

                    Box(
                        modifier = modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = question[0].toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = question[1].toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }


                Row(
                    modifier = Modifier.weight(1F)
                ) {
                    if (isFirst == 1) {

                        Box(
                            modifier = modifier,
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = target,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Box(
                        modifier = modifier,
                        contentAlignment = Alignment.Center
                    ) {

                    }

                    if (isFirst == 0) {
                        Box(
                            modifier = modifier,
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = target,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

    }

}