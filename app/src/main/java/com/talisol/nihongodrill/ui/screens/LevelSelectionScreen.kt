package com.talisol.nihongodrill.ui.screens

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.talisol.nihongodrill.actions.QuizSettingAction
import com.talisol.nihongodrill.ui.states.QuizSelectionState

@Composable
fun LevelSectionScreen(
    navController: NavController,
    quizSelectionState: QuizSelectionState,
    onQuizSettingAction: (QuizSettingAction) -> Unit
) {

    val refScrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (quizSelectionState.selectedCategory != null)
            Text(
                text = quizSelectionState.selectedCategory,
                fontSize = 32.sp
            )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column() {

                Text(text = "LEVEL")
                Button(onClick = {
                    onQuizSettingAction(QuizSettingAction.SelectQuestionLevel(null))
                }) {
                    Text(text = "ALL")
                }
                if (quizSelectionState.levelList != null) {
                    for (level in quizSelectionState.levelList) {
                        Button(onClick = {
                            onQuizSettingAction(QuizSettingAction.SelectQuestionLevel(level))
                        }) {
                            Text(text = level, fontSize = 12.sp)
                        }
                    }
                }
            }

            Column() {
                Text(text = "TYPE")
                Button(onClick = {
                    onQuizSettingAction(QuizSettingAction.SelectQuestionType(null))
                }) {
                    Text(text = "ALL")
                }
                if (quizSelectionState.questionTypeList != null) {
                    for (type in quizSelectionState.questionTypeList) {
                        Button(onClick = {
                            onQuizSettingAction(QuizSettingAction.SelectQuestionType(type))
                        /*TODO*/ }
                        ) {
                            Text(text = type, fontSize = 12.sp)
                        }

                    }
                }
            }


            Column() {
                Text(text = "GROUP")
                if (quizSelectionState.sourceList != null) {
                    for (source in quizSelectionState.sourceList) {

                        Button(onClick = {
                            onQuizSettingAction(QuizSettingAction.SelectQuestionSource(source))
                        }) {
                            Text(text = source, fontSize = 12.sp)
                        }

                    }
                }
            }


            Column(
                modifier = Modifier.verticalScroll(refScrollState)
            ) {
                Text(text = "SUBGROUP")
                if (quizSelectionState.referenceList != null) {
                    for (ref in quizSelectionState.referenceList) {
                        Button(onClick = {
                            onQuizSettingAction(QuizSettingAction.SelectQuestionReference(ref))
                        }) {
                            Text(text = ref, fontSize = 12.sp)
                        }

                    }
                }
            }

        }


    }


}