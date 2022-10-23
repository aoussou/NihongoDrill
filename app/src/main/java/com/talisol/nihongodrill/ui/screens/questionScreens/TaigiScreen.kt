package com.talisol.nihongodrill.ui.screens.questionScreens

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
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.quizUtils.extractStringFromJson
import com.talisol.nihongodrill.ui.states.QuizState

@Composable
fun TaigiScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit
) {

    val suggestionStrings = quizState.mcaList!!

    val questionLists = extractStringFromJson(quizState.question)
    val correctAnswersList = quizState.correctAnswersList
    val targetsList = extractStringFromJson(quizState.target!!)
    var isShowingTaigi by remember { mutableStateOf(true) }

    if (!quizState.isAnswerConfirmed) {

        Box(modifier =
        Modifier
            .fillMaxWidth()
            .clickable {
                isShowingTaigi = !isShowingTaigi
            }
        ) {
            if (isShowingTaigi) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "対義語",
                    fontWeight = FontWeight.ExtraBold
                )
            }

            if (isShowingTaigi) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = "類義語へ"
                )
            } else {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = "類義語へ"
                )
            }

            if (!isShowingTaigi) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "類義語",
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }



    Row() {
        for (s in suggestionStrings) {

            Box(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color.Black))
                    .aspectRatio(1f)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(s, fontSize = 12.sp)
            }

        }
    }



    if (!quizState.isAnswerConfirmed) {


        if (isShowingTaigi) {

            TaigiRow(
                quizState,
                questionLists,
                (0..4).toList(),
                correctAnswersList!!,
                targetsList,
                onQuizAction,
            )

        } else {
            TaigiRow(
                quizState,
                questionLists,
                (5..9).toList(),
                correctAnswersList!!,
                targetsList,
                onQuizAction,
            )

        }
    } else {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
//            .fillMaxHeight(.75f)
//            .height(IntrinsicSize.Min)
            ,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val taigiRowModifier = Modifier.weight(1f).fillMaxHeight(.25F)

            Text(
                text = "対義語",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
            )

            TaigiRow(
                quizState,
                questionLists,
                (0..4).toList(),
                correctAnswersList!!,
                targetsList,
                onQuizAction,
                taigiRowModifier
            )

            Text(
                text = "類義語",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
            )

            TaigiRow(
                quizState,
                questionLists,
                (5..9).toList(),
                correctAnswersList!!,
                targetsList,
                onQuizAction,
                taigiRowModifier
            )



        }


    }


}