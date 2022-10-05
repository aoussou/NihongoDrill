package com.talisol.kankenkakitori.ui.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.quizUtils.extractStringFromJson
import com.talisol.kankenkakitori.ui.states.QuizState
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShikibetsuScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit
) {

    val suggestionStrings = extractStringFromJson(quizState.target)


    val listKata = listOf("ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ")

    Column {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(72.dp),
            verticalArrangement = Arrangement.Center,
            content = {
                items(suggestionStrings.size) { index ->
                    Box(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color.Black))
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(listKata[index], fontSize = 24.sp)
                            Text(
                                suggestionStrings[index].replace(""""""", ""),
                                fontSize = 24.sp,
                            )
                        }
                    }
                }
            }
        )

        val questionsLists = Json.parseToJsonElement(quizState.question).jsonArray



        Column(
            modifier = Modifier
                .fillMaxHeight(.75F)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            for (ql_ind in questionsLists.indices) {
                val ql = questionsLists[ql_ind]
                val questionGroup = Json.parseToJsonElement(ql.toString()).jsonArray

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    for (question in questionGroup) {
                        Text(
                            text = question.toString().replace(""""""", ""),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    SelectionBox(
                        quizState,
                        onQuizAction,
                        suggestionStrings,
                        listKata,
                        ql_ind
                    )

                    if (quizState.correctAnswersList != null) {

                        val correctAnswer = quizState.correctAnswersList[ql_ind]
                        if (
                            quizState.isAnswerConfirmed
                            && (quizState.selectedAnswersList!![ql_ind] != correctAnswer)
                        ) {
                            Text(
                                text = correctAnswer,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                        }

                    }


                }

            }

        }

    }

}