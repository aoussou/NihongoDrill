package com.talisol.kankenkakitori.ui.screens

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
import com.talisol.kankenkakitori.ui.states.QuizState
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DouonScreen(
    quizState: QuizState,
    onQuizAction: (QuizAction) -> Unit
) {


    val suggestionsJsonString = Json.parseToJsonElement(quizState.target).jsonArray.toList()
    val listKata = listOf("ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ")

    Column {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(72.dp),
            verticalArrangement = Arrangement.Center,
            content = {
                items(suggestionsJsonString.size) { index ->
                    Box(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color.Black))
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(listKata[index], fontSize = 24.sp)
                            Text(
                                suggestionsJsonString[index].toString().replace(""""""", ""),
                                fontSize = 24.sp,
                            )
                        }
                    }
                }
            }
        )

        val questionsLists = Json.parseToJsonElement(quizState.question).jsonArray
//            val answersArray by remember { mutableStateOf(arrayOfNulls<String>(questionsLists.size))}
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

                    var isExpanded by remember { mutableStateOf(false) }

//                        var selectedAnswer by remember { mutableStateOf("") }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.25F)
                            .aspectRatio(1f)
                            .padding(12.dp)
                            .border(BorderStroke(1.dp, Color.Black))
                            .background(Color.White)
                            .clickable { isExpanded = true },
                        contentAlignment = Alignment.Center
                    ) {

                        if (quizState.selectedAnswersList != null) {
                            if (quizState.selectedAnswersList[ql_ind] != null) {
                                Text(text = quizState.selectedAnswersList[ql_ind]!!)
                            }

                        }


                        if (isExpanded) {
                            DropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false },
                            ) {
                                listKata.forEachIndexed { index, element ->
                                    DropdownMenuItem(onClick = {
                                        onQuizAction(QuizAction.UpdateAnswersList(element, ql_ind))
                                        isExpanded = false
                                    }) {
                                        Text(text = element)
                                    }
                                }
                            }
                        }


                    }

                    val correctAnswer = quizState.correctAnswersList!![ql_ind]
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