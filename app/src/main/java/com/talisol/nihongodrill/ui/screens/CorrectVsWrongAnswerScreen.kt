package com.talisol.nihongodrill.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.nihongodrill.ui.states.QuizState
import com.talisol.nihongodrill.ui.theme.DarkGreen

@Composable
fun CorrectVsWrongAnswerScreen(
    quizState: QuizState,
) {


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {


        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {




            Box {
                Text(text = "Your answer:")
            }

            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {

                Row {
                    if (quizState.questionType == "goji") {

                        if (quizState.selectedWrongKanji != null) {
                            Text(
                                text = "${quizState.selectedWrongKanji} ➔",
                                color = Color.Red,
                                fontSize = 16.sp
                            )
                        }
                    }

                    if (quizState.inputAnswer != null) {
                        Text(
                            text = quizState.inputAnswer,
                            color = Color.Red,
                            fontSize = 16.sp
                        )

                    }
                }

            }
        }

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Text(text = "Correct answer:")
            }

            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {

                Row {

                    if (quizState.questionType == "goji") {

                        Text(
                            text = "${quizState.target} ➔",
                            color = DarkGreen,
                            fontSize = 16.sp
                        )
                    }

                    Text(
                        text = quizState.correctAnswer!!,
                        color = DarkGreen,
                        fontSize = 16.sp
                    )

                }

            }
        }

    }


}


