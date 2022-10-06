package com.talisol.kankenkakitori

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.talisol.kankenkakitori.ui.screens.KanjiDrawingWidget
import com.talisol.kankenkakitori.ui.screens.NewQuizScreen
import com.talisol.kankenkakitori.ui.screens.QuizScreen
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

                val quizSettingVM = viewModel<QuizSettingVM>()
                val localQuizState by quizSettingVM.quizSelectionState.collectAsState()

                val popupVM = viewModel<PopupVM>()
                val popupState by popupVM.popupState.collectAsState()

                val quizVM = viewModel<QuizVM>()
                val quizState by quizVM.quizState.collectAsState()

                val trackingVM = viewModel<ProgressTrackingVM>()

                NewQuizScreen(
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


//                Row(
//                    modifier = Modifier
//                        .fillMaxSize()
//                    ,
////                    verticalArrangement = Arrangement.Bottom,
//                verticalAlignment = Alignment.Bottom
//                ) {
//
//                    KanjiDrawingWidget(
//                        quizState,
//                        drawingState,
//                        popupState,
//                        currentPath,
//                        predictedKanji = predictedKanji.value,
//                        otherGuessesList = otherGuessesList.value,
//                        onDrawingAction = drawingVM::onAction,
//                        onKanjiRecAction = recognizerVM::onAction,
//                        onPopupAction = popupVM::onAction
//                    )
//                }


//                if (!quizState.isQuizStarted) {
//
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.SpaceEvenly
//                    ) {
//                        if (localQuizState.chosenNumberOfQuestions == null ||
//                            localQuizState.groupChosen == null ||
//                            localQuizState.typeChosen == null
//                        ) {
//
//
//                            SelectKyuScreen(
//                                groupsList = quizSettingVM.groupsList,
//                                quizType = quizSettingVM.quizTypesList,
//                                onAction = quizSettingVM::onAction
//                            )
//
//                        } else {
//                            Column {
//                                Button(onClick = {
//                                    quizSettingVM.onAction(QuizSettingAction.LoadSelectedGroupQuestions)
//                                    quizSettingVM.onAction(QuizSettingAction.MakeLocalQuizQuestionList)
//                                    quizVM.onAction(
//                                        QuizAction.LoadQuestionList(
//                                            quizSettingVM.localQAlist.value
//                                        )
//                                    )
//                                    quizVM.onAction(
//                                        QuizAction.SetQuizType(
//                                            localQuizState.typeChosen!!
//                                        )
//                                    )
//                                    quizVM.onAction(QuizAction.StartQuiz)
//                                }) {
//                                    Text(text = "Start quiz,  ${localQuizState.chosenNumberOfQuestions} ${localQuizState.typeChosen} questions of ${localQuizState.groupChosen}")
//                                }
//
//                                Button(onClick = {
//                                    quizSettingVM.onAction(
//                                        QuizSettingAction.SelectQuestionLevel(
//                                            null
//                                        )
//                                    )
//                                    quizSettingVM.onAction(
//                                        QuizSettingAction.ChooseNumberOfQuestions(
//                                            null
//                                        )
//                                    )
//                                }) {
//                                    Text(text = "Go back to selection menu")
//                                }
//                            }
//
//                        }
//                    }
//                } else {
////                        NewQuizScreen(
////                            quizState = quizState,
////                            onQuizAction = quizVM::onAction,
////                            popupState = popupState,
////                            currentPath = currentPath,
////                            drawingState = drawingState,
////                            onDrawingAction = drawingVM::onAction,
////                            onKanjiRecAction = recognizerVM::onAction,
////                            predictedKanji = predictedKanji.value,
////                            otherGuessesList = otherGuessesList.value,
////                            onTrackingAction = trackingVM::onAction,
////                            onPopupAction = popupVM::onAction
////                        )
//
//                }


            }
        }

    }


}



