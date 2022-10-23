package com.talisol.nihongodrill.ui.screens

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.talisol.nihongodrill.actions.QuizAction
import com.talisol.nihongodrill.actions.QuizSettingAction
import com.talisol.nihongodrill.navigation.ScreenRoute
import com.talisol.nihongodrill.quizUtils.Question
import com.talisol.nihongodrill.ui.states.QuizSelectionState

@Composable
fun LevelSectionScreen(
    navController: NavController,
    quizSelectionState: QuizSelectionState,
    onQuizSettingAction: (QuizSettingAction) -> Unit,
    localQAlist: List<Question>,
    onQuizAction: (QuizAction) -> Unit
) {

    val refScrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5F),
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
                            }
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
                    if (
                        quizSelectionState.referenceList != null
                        && quizSelectionState.selectedQuestionSource != null
                        && quizSelectionState.selectedLevel != null
                    ) {
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


        Text(text = "${localQAlist.size} questions available")

        val textState = remember { mutableStateOf(TextFieldValue()) }
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            label = { Text("Number of Random Question") },
            value = textState.value,
            onValueChange = {
                textState.value = it;

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onQuizSettingAction(
                        QuizSettingAction.ChooseNumberOfQuestions(
                            textState.value.text.toInt()
                        )
                    )
                    focusManager.clearFocus()

                    Log.i("DEBUG", "number sent")
                })
        )

        Button(onClick = {


            onQuizSettingAction(QuizSettingAction.MakeLocalQuizQuestionList)
            onQuizAction(QuizAction.LoadQuestionList(localQAlist))
            onQuizAction(QuizAction.StartQuiz)



            navController.navigate(ScreenRoute.QuizScreen.route)
        }) {
            Text(text = "START QUIZ")
        }

    }


}