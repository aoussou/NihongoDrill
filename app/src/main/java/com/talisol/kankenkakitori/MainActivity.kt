package com.talisol.kankenkakitori

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.talisol.kankenkakitori.actions.QuizAction
import com.talisol.kankenkakitori.actions.QuizSettingAction
import com.talisol.kankenkakitori.ui.screens.KankenQuizScreen
import com.talisol.kankenkakitori.ui.screens.SelectKyuScreen
import com.talisol.kankenkakitori.ui.theme.KankenKakitoriTheme
import com.talisol.kankenkakitori.viewModels.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KankenKakitoriTheme {
                // A surface container using the 'background' color from the theme

                val drawingVM = viewModel<DrawingVM>()
                val drawingState by drawingVM.drawingState.collectAsState()
                val currentPath by drawingVM.currentPath.collectAsState()

                val recognizerVM = viewModel<KanjiRecognitionVM>()
                val otherGuessesList = recognizerVM.otherGuessesList.collectAsState()
                val predictedKanji = recognizerVM.predictedKanji.collectAsState()

                val questionListSelectionVM = viewModel<QuestionListSelectionVM>()
                val localQuizState by questionListSelectionVM.quizSelectionState.collectAsState()

                val popupVM = viewModel<PopupVM>()
                val popupState by popupVM.popupState.collectAsState()

                val quizVM = viewModel<QuizVM>()
                val quizState by quizVM.quizState.collectAsState()

                val trackingVM = viewModel<ProgressTrackingVM>()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    if (!quizState.isQuizStarted) {

                        if (localQuizState.chosenNumberOfQuestions == null ||
                            localQuizState.groupChosen == null ||
                            localQuizState.typeChosen == null
                        ) {



                            SelectKyuScreen(
                                groupsList = questionListSelectionVM.groupsList,
                                quizType = questionListSelectionVM.quizTypesList,
                                onAction = questionListSelectionVM::onAction
                            )

                        } else {
                            Column {
                                Button(onClick = {
                                    questionListSelectionVM.onAction(QuizSettingAction.LoadSelectedGroupQuestions)
                                    questionListSelectionVM.onAction(QuizSettingAction.MakeLocalQuizQuestionList)
                                    quizVM.onAction(
                                        QuizAction.LoadQuestionList(
                                            questionListSelectionVM.localQAlist.value
                                        )
                                    )
                                    quizVM.onAction(
                                        QuizAction.SetQuizType(
                                            localQuizState.typeChosen!!
                                        )
                                    )
                                    quizVM.onAction(QuizAction.StartQuiz)
                                }) {
                                    Text(text = "Start quiz,  ${localQuizState.chosenNumberOfQuestions} ${localQuizState.typeChosen} questions of ${localQuizState.groupChosen}")
                                }

                                Button(onClick = {
                                    questionListSelectionVM.onAction(QuizSettingAction.SelectQuestionLevel(null))
                                    questionListSelectionVM.onAction(QuizSettingAction.ChooseNumberOfQuestions(null))
                                }) {
                                    Text(text = "Go back to selection menu")
                                }
                            }
                        }


                    } else {
                        KankenQuizScreen(
                            quizState = quizState,
                            onQuizAction = quizVM::onAction,
                            popupState = popupState,
                            currentPath = currentPath,
                            drawingState = drawingState,
                            onDrawingAction = drawingVM::onAction,
                            onKanjiRecAction = recognizerVM::onAction,
                            predictedKanji = predictedKanji.value,
                            otherGuessesList = otherGuessesList.value,
                            onTrackingAction = trackingVM::onAction,
                            onPopupAction = popupVM::onAction
                        )
                    }
                }
            }

        }


    }


}
