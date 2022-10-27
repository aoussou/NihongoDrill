package com.talisol.nihongodrill.ui.screens.questionScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.ui.states.QuizState


@Composable
fun TaigiRow(
    quizState: QuizState,
    questionLists: List<String>,
    questionsRange: List<Int>,
    correctAnswersList: List<String>,
    targetsList: List<String>,
    onQuizAction: (QuizAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(modifier = Modifier
        .padding(bottom = 0.dp)
        .then(modifier)

    ) {

        val modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
//            .border(BorderStroke(1.dp, Color.Black))

        val rowModifier = Modifier
            .padding(0.dp)

        for (ql_ind in questionsRange) {

            val question = questionLists[ql_ind]
            val correctAnswer = correctAnswersList[ql_ind]
            val isFirst = targetsList[ql_ind].indexOf(correctAnswer)
            val target = targetsList[ql_ind].replace(correctAnswer, "")

//            val isAnswerCorrect =
//                if (quizState.selectedAnswersList != null) {
//                    quizState.selectedAnswersList[ql_ind] == quizState.correctAnswersList!![ql_ind]
//                } else {
//                    null
//                }

            val isAnswerCorrect = quizState.selectedAnswersList!![ql_ind] == quizState.correctAnswersList!![ql_ind]



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

                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 1.dp, vertical = 1.dp)
                )

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
                            .border(
                                if (
                                    quizState.selectedSubQuestionNbr == ql_ind
                                    && !quizState.isAnswerConfirmed)
                                    BorderStroke(3.dp,Color.Blue)
                                else BorderStroke(1.dp, Color.Black)
                            )
                            .clickable {
                                onQuizAction(QuizAction.SetSelectedSubQuestion(ql_ind))
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        if (quizState.selectedAnswersList[ql_ind] != null) {
                            Text(
                                text = quizState.selectedAnswersList[ql_ind]!!,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color =
                                if (quizState.isAnswerConfirmed) {
                                    if (isAnswerCorrect) Color.Green else Color.Red
                                } else Color.Black
                            )
                        }
                    }

                    if (isFirst == 0) {
                        TaigiBox(
                            modifier = modifier,
                            text = target
                        )
                    }
                }

                if (quizState.isAnswerConfirmed && !isAnswerCorrect) {
                    Row(
                        modifier = rowModifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (isFirst == 1) {
                            TaigiBox(
                                modifier = modifier,
                                text = ""
                            )
                        }
                        Box(
                            modifier =
                            modifier
                                .border(BorderStroke(1.dp, Color.Black)),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = quizState.correctAnswersList[ql_ind],
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )

                        }

                        if (isFirst == 0) {
                            TaigiBox(
                                modifier = modifier,
                                text = ""
                            )
                        }
                    }
                }
            }


            Divider(
                color = Color.Black,
                modifier = Modifier
//                    .padding(horizontal = 1.dp, vertical = 1.dp)
                    .padding(bottom = if(isAnswerCorrect) 120.dp else 1.dp, start = 1.dp,end=1.dp)
                    .fillMaxHeight()
                    .width(2.dp)
            )

        }
    }

}


